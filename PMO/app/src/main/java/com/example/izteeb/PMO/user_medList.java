package com.example.izteeb.PMO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Izteeb on 9/24/2017.
 */

public class user_medList extends AppCompatActivity {


    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> medicationList;
    private ListView listView;

    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";
    public String TAG_MED_OBJECT = "";
    public String TAG_USER_VALUE = "user";
    public String TAG_MIN_VALUE = "min_start";
    public String TAG_HOUR_VALUE = "hour_start";
    public String TAG_AM_PM = "am_pm";
    public String TAG_QUANTITY= "quantity";
    public String TAG_MEDICINE = "medicine_name";
    public String TAG_DOSAGE = "dosage";

    public String TAG_TIME  = "time_start";
    public String TAG_SLOT = "slot_number";
    public String TAG_DESCRIPTION = "description";
    String username;


    @Override
    public void onBackPressed() {

    }

    public void backView(){
        Button backBttn = (Button) findViewById(R.id.b61);
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_medList.this, user_activity.class));
            }
        });

    }

    JSONArray server_response = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_listview);
        medicationList = new ArrayList<HashMap<String, String>>();
        backView();
        listView = (ListView) findViewById(R.id.list5);
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        username = prefs.getString("username","");
        new LoadMedicineList().execute();

    }

    private class LoadMedicineList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(user_medList.this);
            pDialog.setMessage("Loading medications. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_user_medication);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jArray = jsonObj.getJSONArray("medication");

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject c = jArray.getJSONObject(i);

                        String user = c.getString(TAG_USER_VALUE);
                        int slot = c.getInt(TAG_SLOT);
                        int min = c.getInt(TAG_MIN_VALUE);
                        int hour = c.getInt(TAG_HOUR_VALUE);
                        String am_pm = c.getString(TAG_AM_PM);
                        String med = c.getString(TAG_MEDICINE);
                        String dosage = c.getString(TAG_DOSAGE);
                        int quantity = c.getInt(TAG_QUANTITY);

                        if(user.equals(username)) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put(TAG_MEDICINE, med);
                            map.put(TAG_SLOT, Integer.toString(slot));
                            map.put(TAG_DOSAGE, dosage);
                            map.put(TAG_QUANTITY, Integer.toString(quantity));
                            map.put(TAG_MIN_VALUE, Integer.toString(min));
                            map.put(TAG_HOUR_VALUE, Integer.toString(hour));
                            map.put(TAG_AM_PM, am_pm);

                            medicationList.add(map);
                        }

                    }

                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if(pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(user_medList.this, medicationList, R.layout.row_layout_medicine, new String[]{
                    TAG_MEDICINE, TAG_SLOT, TAG_DOSAGE, TAG_QUANTITY, TAG_MIN_VALUE, TAG_HOUR_VALUE,TAG_AM_PM
            }, new int[]{R.id.row_medicinename, R.id.row_slotnumber, R.id.row_dosage,R.id.row_quantity, R.id.row_min,R.id.row_hour,R.id.row_ampm});
            listView.setAdapter(adapter);


        }
    }

}
