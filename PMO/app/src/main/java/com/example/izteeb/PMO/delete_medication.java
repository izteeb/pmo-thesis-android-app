package com.example.izteeb.PMO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class delete_medication extends AppCompatActivity {


    private String TAG = delete_medication.class.getSimpleName();
    private ProgressDialog pDialog, progressDialog1;
    JSONParser jParser = new JSONParser();

    ArrayList<Map<String, String>> medicationList;
    private ListView listView;
    RelativeLayout layout;
/*
    private static String url_list_med = "http://dmtthesiscpepmo.comli.com/json_getmedication.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MEDICATION = "server_response";
    private static final String TAG_USER = "user";
    private static final String TAG_MEDICINE = "medicine_name";
    private static final String TAG_SLOT = "slot_number";
    private static final String TAG_DOSAGE = "dosage";
    private static final String TAG_TIME = "time_start";
*/

    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";
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

    EditText username,slot;
    String error;


    @Override
    public void onBackPressed() {

    }


    private void back_delete_med(){
        Button back = (Button) findViewById(R.id.back_bttn2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(delete_medication.this, Admin_activity.class));
            }
        });
    }

    private void search_medication(){

        Button search = (Button) findViewById(R.id.search_on_delete);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user  = username.getText().toString();
                String slot_num = slot.getText().toString();
                new search_med_on_delete_med().execute(user);
            }
        });

    }

    private void delete_medication(){
        Button delete = (Button) findViewById(R.id.delete_user_slot);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder adb = new AlertDialog.Builder(delete_medication.this);
                adb.setTitle("Deleting user medication.");
                adb.setMessage("Are you sure you want to delete slot"+slot.getText().toString()+" ?");
                adb.setCancelable(false);
                String yesButton= "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user  = username.getText().toString();
                        String slot_num = slot.getText().toString();
                        new delete_user_slot().execute(user,slot_num);
                    }
                });

        adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                progressDialog1.cancel();

            }
        });
        adb.show();


            }
        });
    }

    class search_med_on_delete_med extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {
            String user1, slot1;
            user1 = (String) args[0];
            slot1 = (String) args[1];

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_user_medication);
            Log.e(TAG, "Response from url: " + jsonStr);

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

                        if(user.equals(user1)) {

                            Map<String, String> map = new TreeMap<>();
                            map.put(TAG_MEDICINE, med);
                            map.put(TAG_SLOT, Integer.toString(slot));
                            map.put(TAG_DOSAGE, dosage);
                            map.put(TAG_QUANTITY, Integer.toString(quantity));
                            map.put(TAG_MIN_VALUE, Integer.toString(min));
                            map.put(TAG_HOUR_VALUE, Integer.toString(hour));
                            map.put(TAG_AM_PM, am_pm);

                            medicationList.add(map);
                        }
                        /*
                        else if(user.equals(user1) && slot1 == null){
                            HashMap<String, String> map = new HashMap<>();
                            map.put(TAG_MEDICINE, med);
                            map.put(TAG_SLOT, slot);
                            map.put(TAG_DOSAGE, dosage);
                            map.put(TAG_TIME, time);
                            medicationList.add(map);
                        }
                        else if(user1 == null){
                            Toast.makeText(getApplicationContext(), "Please enter username.", Toast.LENGTH_SHORT).show();
                        }
                        else if(user.equals(user1) && !slot.equals(slot1)){
                            Toast.makeText(getApplicationContext(), "There is no slot "+slot1+" to user "+user1+". Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!user.equals(user1)){
                            Toast.makeText(getApplicationContext(), "Username "+user1+" not available. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Username not available. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        */
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
                                "Couldn't get json from server. Check LogCat for possible errors!",
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

            ListAdapter adapter = new SimpleAdapter(delete_medication.this, medicationList, R.layout.row_layout_medicine, new String[]{
                    TAG_MEDICINE, TAG_SLOT, TAG_DOSAGE, TAG_QUANTITY, TAG_MIN_VALUE, TAG_HOUR_VALUE,TAG_AM_PM
            }, new int[]{R.id.row_medicinename, R.id.row_slotnumber, R.id.row_dosage,R.id.row_quantity, R.id.row_min,R.id.row_hour,R.id.row_ampm});
            listView.setAdapter(adapter);

        }
    }

    class delete_user_slot extends AsyncTask<String, Void, String>{

        String delete_med_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete_med_url = "http://dmtthesiscpepmo.comli.com/delete_medication.php";

                    progressDialog1 = new ProgressDialog(delete_medication.this);
                    progressDialog1.setMessage("Deleting medication...");
                    progressDialog1.setIndeterminate(false);
                    progressDialog1.setCancelable(true);
                    progressDialog1.show();


        }



        @Override
        protected String doInBackground(String... args) {
//     backgroundTask.execute(first1,last1,gender,day1,address1,emailaddress1,user,password,role);

            String user1,slot1;
            user1 = (String) args[0];
            slot1 = (String) args[1];

            try {
                URL url = new URL(delete_med_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user1,"UTF-8")+"&"+
                        URLEncoder.encode("slot_number","UTF-8")+"="+URLEncoder.encode(slot1,"UTF-8");
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
            } catch (IOException e) {e.printStackTrace();

            }

            return null;


        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            progressDialog1.dismiss();
            error = result;
            // g3.setText(result);
            if(error.equals("Error.")){
                Toast.makeText(getApplicationContext(),"Error deleting user medication.",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Successfully deleted user medication.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(delete_medication.this, delete_medication.class));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_medication);
        username = (EditText) findViewById(R.id.delete_username_text);
        slot = (EditText) findViewById(R.id.delete_slot_text);
        medicationList = new ArrayList<Map<String, String>>();
        listView = (ListView) findViewById(R.id.lv_delete_slot_med);



        back_delete_med();
        search_medication();
        delete_medication();

    }
}
