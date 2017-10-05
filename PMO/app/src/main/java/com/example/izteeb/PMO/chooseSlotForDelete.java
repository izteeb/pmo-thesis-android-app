package com.example.izteeb.PMO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Stevens Tabangay on 9/6/2017.
 */

public class chooseSlotForDelete extends AppCompatActivity {

    Button slot_1,slot_2, slot_3,slot_4,slot_5,slot_6,slot_7,slot_8,slot_9,back;
    String user;

    ArrayList<Integer> slotList = new ArrayList();
    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";
    public String TAG_USER = "user";
    public String TAG_SLOT = "slot_number";
    Handler mHandler = new Handler();
    Boolean isRunning = true;


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

    public void deleteMedication(){
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

        slot_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 1 Medication");
                adb.setMessage("Are you sure you want to delete slot 1?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String user1 = user;
                        String slot1 = "1";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 2 Medication");
                adb.setMessage("Are you sure you want to delete slot 2?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "2";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 3 Medication");
                adb.setMessage("Are you sure you want to delete slot 3?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "3";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 5 Medication");
                adb.setMessage("Are you sure you want to delete slot 5?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "4";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 5 Medication");
                adb.setMessage("Are you sure you want to delete slot 5?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "5";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 6 Medication");
                adb.setMessage("Are you sure you want to delete slot 6?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "6";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 7 Medication");
                adb.setMessage("Are you sure you want to delete slot 7?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "7";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 8 Medication");
                adb.setMessage("Are you sure you want to delete slot 8?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "8";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });

        slot_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(chooseSlotForDelete.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete Slot 9 Medication");
                adb.setMessage("Are you sure you want to delete slot 9?");
                adb.setCancelable(false);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user1 = user;
                        String slot1 = "9";
                        new deleteMedicationBackgroundTask().execute(user1,slot1);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                adb.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chooseSlotForDelete.this,edit_or_delete.class));
            }
        });

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


    class deleteMedicationBackgroundTask extends AsyncTask<String, Void, String> {

        String add_info_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_info_url = "http://dmtthesiscpepmo.comli.com/delete_medication.php";


        }
        @Override
        protected String doInBackground(String... args) {
//     backgroundTask.execute(first1,last1,gender,day1,address1,emailaddress1,user,password,role);


            String user2,slot2;
            user2= (String) args[0];
            slot2= (String)args[1];



            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user2,"UTF-8")+"&"+
                        URLEncoder.encode("slot_number","UTF-8")+"="+URLEncoder.encode(slot2,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line= "";
                while((line = bufferedReader.readLine()) !=null){
                    result +=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;


        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),"Medication deleted successfully.",Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_slot);

        deleteMedication();

        TextView textDelete = (TextView) findViewById(R.id.tvname1);
        textDelete.setText("Choose slot to delete:");

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
