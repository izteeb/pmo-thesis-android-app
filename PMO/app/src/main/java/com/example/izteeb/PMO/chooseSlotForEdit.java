package com.example.izteeb.PMO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Stevens Tabangay on 9/6/2017.
 */

public class chooseSlotForEdit extends AppCompatActivity{

    Button slot_1,slot_2, slot_3,slot_4,slot_5,slot_6,slot_7,slot_8,slot_9,back;
    String user;
    int slotSP;

    ArrayList<Integer> slotList = new ArrayList();
    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";
    public String TAG_USER = "user";
    public String TAG_SLOT = "slot_number";

    public void findViewInit(){
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
                    slot_1.setEnabled(true);
                }
                if(slotList.contains(2)){
                    slot_2.setEnabled(true);
                }
                if(slotList.contains(3)){
                    slot_3.setEnabled(true);
                }
               if(slotList.contains(4)){
                    slot_4.setEnabled(true);
                }
                if(slotList.contains(5)){
                    slot_5.setEnabled(true);
                }
                if(slotList.contains(6)){
                    slot_6.setEnabled(true);
                }
                if(slotList.contains(7)){
                    slot_7.setEnabled(true);
                }
                if(slotList.contains(8)){
                    slot_8.setEnabled(true);
                }
                if(slotList.contains(9)){
                    slot_9.setEnabled(true);
                }

            }
        }
    }

    public void slotClickedEdit(){
        slot_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotSP = 1;
                startActivity(new Intent(chooseSlotForEdit.this, edit_medication_userAccount.class));
            }
        });
        slot_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotSP = 2;
                startActivity(new Intent(chooseSlotForEdit.this, edit_medication_userAccount2.class));
            }
        });
        slot_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotSP = 3;
                startActivity(new Intent(chooseSlotForEdit.this, edit_medication_userAccount3.class));
            }
        });
        slot_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotSP = 4;
                startActivity(new Intent(chooseSlotForEdit.this, edit_medication_userAccount4.class));
            }
        });
        slot_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotSP = 5;
                startActivity(new Intent(chooseSlotForEdit.this,edit_medication_userAccount5.class));
            }
        });
        slot_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotSP = 6;
                startActivity(new Intent(chooseSlotForEdit.this,edit_medication_userAccount6.class));
            }
        });
        slot_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slotSP = 7;
                startActivity(new Intent(chooseSlotForEdit.this,edit_medication_userAccount7.class));
            }
        });
        slot_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slotSP = 8;
                startActivity(new Intent(chooseSlotForEdit.this,edit_medication_userAccount8.class));
            }
        });
        slot_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slotSP = 9;
                startActivity(new Intent(chooseSlotForEdit.this,edit_medication_userAccount9.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseSlotForEdit.this,edit_or_delete.class));
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

        TextView textDelete = (TextView) findViewById(R.id.tvname1);
        textDelete.setText("Choose slot to edit:");
        findViewInit();
        slotClickedEdit();
        new checkSlotAvailability().execute();
        slot_1.setEnabled(false);
        slot_2.setEnabled(false);
        slot_3.setEnabled(false);
        slot_4.setEnabled(false);
        slot_5.setEnabled(false);
        slot_6.setEnabled(false);
        slot_7.setEnabled(false);
        slot_8.setEnabled(false);
        slot_9.setEnabled(false);
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        user = prefs.getString("username","");
        prefs.edit().putString("slot",String.valueOf(slotSP)).apply();

    }
}
