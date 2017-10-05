package com.example.izteeb.PMO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class search_med extends AppCompatActivity {

    private String TAG = search_med.class.getSimpleName();
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    EditText user_med;
    Button search_user_med;
    ArrayList<Map<String, String>> medicationList;
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

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    @Override
    public void onBackPressed() {

    }

    public void back_search_med(){
        Button back = (Button) findViewById(R.id.back_bttn1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(search_med.this, Admin_activity.class));
            }
        });
    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            user_med.setEnabled(true);
            search_user_med.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue!",Toast.LENGTH_SHORT).show();
            user_med.setEnabled(false);
            search_user_med.setEnabled(false);
        }
    }

    public void search_med(){
        user_med = (EditText) findViewById(R.id.search_username_text);
        search_user_med = (Button) findViewById(R.id.search_username_med);

        search_user_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_medication = user_med.getText().toString();
                new search_med_per_user().execute(user_medication);
            }
        });
    }
        class search_med_per_user extends AsyncTask<String, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(search_med.this);
                pDialog.setMessage("Loading medications. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String user1;
                user1 = (String) args[0];
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(url_user_medication);
                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONArray jArray = jsonObj.getJSONArray("medication");
                        int qty = 1;

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

                            if(user.equals(user1)) {

                                Map<String, String> map = new TreeMap<>();
                                map.put(TAG_MEDICINE, med);
                                map.put("Quantity",Integer.toString(qty++));
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
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
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
                        Log.e(TAG, "Couldn't get json from server.");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Please connect to internet to continue!",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }

                    return null;
                }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(pDialog.isShowing()) {
                    pDialog.dismiss();

                    if(medicationList.size() == 0){
                        Toast.makeText(getApplicationContext(), "There is no medication. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ListAdapter adapter = new SimpleAdapter(search_med.this, medicationList, R.layout.row_medication2, new String[]{
                                "Quantity", TAG_MEDICINE, TAG_SLOT, TAG_DOSAGE, TAG_QUANTITY, TAG_MIN_VALUE, TAG_HOUR_VALUE, TAG_AM_PM
                        }, new int[]{R.id.tv701, R.id.tv721, R.id.tv791, R.id.tv703, R.id.tv705, R.id.tv761, R.id.tv741, R.id.tv771});
                        listView.setAdapter(adapter);
                    }
                }
            }
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_med);
            back_search_med();
            search_med();
            medicationList = new ArrayList<Map<String, String>>();
            listView = (ListView) findViewById(R.id.lv_search_med);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    try{
                        Thread.sleep(2000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                internetConnection();
                            }
                        });
                    }
                    catch(Exception e){

                    }
                }
            }
        }).start();
    }
}
