package com.example.izteeb.PMO;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class edit_userslotMedication extends AppCompatActivity {


    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    ArrayList<Map<String,String>> medicationList;
    userslotEditAdapter adapter;
    private ListView listView;
    List<userslotDelete> userList;
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

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    @Override
    public void onBackPressed() {

    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            listView.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue!",Toast.LENGTH_SHORT).show();
            listView.setVisibility(View.INVISIBLE);
        }
    }

    public void backBttnPressed(){
        Button backBttn = (Button) findViewById(R.id.back_bttn8);

        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_userslotMedication.this, Admin_activity.class));
            }
        });
    }

    private class LoadMedicineList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(edit_userslotMedication.this);
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
                        userList = new ArrayList<>();

                        String user = c.getString(TAG_USER_VALUE);
                        int slot = c.getInt(TAG_SLOT);
                        int min = c.getInt(TAG_MIN_VALUE);
                        int hour = c.getInt(TAG_HOUR_VALUE);
                        String am_pm = c.getString(TAG_AM_PM);
                        String med = c.getString(TAG_MEDICINE);
                        String dosage = c.getString(TAG_DOSAGE);
                        int quantity = c.getInt(TAG_QUANTITY);

                        Map<String, String> map = new TreeMap<>();
                        map.put(TAG_USER_VALUE,user);
                        map.put(TAG_SLOT, Integer.toString(slot));

                        // userList.add(new userslotDelete(slot,user));


                        medicationList.add(map);
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
                                "Please connect to internet to continue!",
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

            adapter = new userslotEditAdapter(medicationList, edit_userslotMedication.this);
            listView.setAdapter(adapter);

        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_userslot_medication);

        medicationList = new ArrayList<Map<String, String>>();
        listView = (ListView) findViewById(R.id.listView3);

        new LoadMedicineList().execute();
        backBttnPressed();

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


    public class userslotEditAdapter extends BaseAdapter implements ListAdapter {

        private ArrayList<Map<String,String>> list = new ArrayList<>();
        Activity context;

        public userslotEditAdapter(ArrayList<Map<String,String>> list, Activity context){
            this.list = list;
            this.context = context;
        }

        @Override
        public boolean isEnabled(int position) {
            return isNetworkAvailable();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {

            View view = convertView;
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_editmedication, null);
            }

            final TextView slottext = (TextView) view.findViewById(R.id.row_editSlot);
            final Button editBttn = (Button) view.findViewById(R.id.buttonEdit);
            final TextView userText = (TextView) view.findViewById(R.id.row_editUsername);

            userText.setText(list.get(i).get("user"));
            slottext.setText(list.get(i).get("slot_number"));

           editBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user = userText.getText().toString();
                    String slot = slottext.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), edit_medication.class);
                    intent.putExtra("medUserEdit", user);
                    intent.putExtra("medSlotEdit", slot);
                    startActivity(intent);

                }
            });

            return view;
        }



    }
}
