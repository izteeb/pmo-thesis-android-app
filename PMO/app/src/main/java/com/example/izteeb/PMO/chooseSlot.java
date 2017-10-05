package com.example.izteeb.PMO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;

public class chooseSlot extends AppCompatActivity {

    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";
    Button slot_1,slot_2, slot_3,slot_4,slot_5,slot_6,slot_7,slot_8,slot_9,back;
    TextView user1;

    private String user;
    public String TAG_USER = "user";
    public String TAG_SLOT = "slot_number";
    public int slot;
    Handler mHandler = new Handler();
    Boolean isRunning = true;


    ArrayList<Integer> slotList = new ArrayList();

    public void findView(){
        slot_1 = (Button) findViewById (R.id.slot1);
        slot_2 = (Button) findViewById (R.id.slot2);
        slot_3 = (Button) findViewById (R.id.slot3);
        slot_4 = (Button) findViewById (R.id.slot4);
        slot_5 = (Button) findViewById (R.id.slot5);
        slot_6 = (Button) findViewById (R.id.slot6);
        slot_7 = (Button) findViewById(R.id.slot7);
        slot_8 = (Button) findViewById(R.id.slot8);
        slot_9 = (Button) findViewById(R.id.slot9);
        back = (Button) findViewById(R.id.bckBttn);
    }


    public void internetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            slot_1.setEnabled(true);
            slot_2.setEnabled(true);
            slot_3.setEnabled(true);
            slot_4.setEnabled(true);
            slot_5.setEnabled(true);
            slot_6.setEnabled(true);
            slot_7.setEnabled(true);
            slot_8.setEnabled(true);
            slot_9.setEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue.",Toast.LENGTH_SHORT).show();
            slot_1.setEnabled(false);
            slot_2.setEnabled(false);
            slot_3.setEnabled(false);
            slot_4.setEnabled(false);
            slot_5.setEnabled(false);
            slot_6.setEnabled(false);
            slot_7.setEnabled(false);
            slot_8.setEnabled(false);
            slot_9.setEnabled(false);


        }
    }

    private class checkSlotAvailability extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_user_medication);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jArray = jsonObj.getJSONArray("medication");

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject c = jArray.getJSONObject(i);

                        String username = c.getString(TAG_USER);
                        int slot = c.getInt(TAG_SLOT);

                        if(username.equals(user)) {
                           slotList.add(slot);
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
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            for(int i=0; i<slotList.size(); i++){
                if(slotList.contains(1)){
                    slot_1.setEnabled(false);
                }
                if(slotList.contains(2)){
                    slot_2.setEnabled(false);
                }
               if(slotList.contains(3)){
                    slot_3.setEnabled(false);
                }
                if(slotList.contains(4)){
                    slot_4.setEnabled(false);
                }
                if(slotList.contains(5)){
                    slot_5.setEnabled(false);
                }
                if(slotList.contains(6)){
                    slot_6.setEnabled(false);
                }
                if(slotList.contains(7)){
                    slot_7.setEnabled(false);
                }
                if(slotList.contains(8)){
                    slot_8.setEnabled(false);
                }
               if(slotList.contains(9)){
                    slot_9.setEnabled(false);
                }

            }
        }
    }



    public void slot_clicked(){
        slot_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(chooseSlot.this, slot1.class));
            }
        });
        slot_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chooseSlot.this, slot2.class));
            }
        });
        slot_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chooseSlot.this, slot3.class));
            }
        });
        slot_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chooseSlot.this, slot4.class));
            }
        });
        slot_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chooseSlot.this,slot5.class));
            }
        });
        slot_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chooseSlot.this,slot6.class));
            }
        });
        slot_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseSlot.this,slot7.class));
            }
        });
        slot_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseSlot.this,slot8.class));
            }
        });
        slot_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseSlot.this,slot9.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseSlot.this,user_activity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_slot);
        findView();

        user1  = (TextView) findViewById(R.id.tvname1);
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        user = prefs.getString("username","");
        user1.setText(user+"'s"+" available slots:");
        new checkSlotAvailability().execute();
        slot_clicked();


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
