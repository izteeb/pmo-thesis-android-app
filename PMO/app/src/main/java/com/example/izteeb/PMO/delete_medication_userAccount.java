package com.example.izteeb.PMO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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


public class delete_medication_userAccount extends AppCompatActivity {


    private String TAG = delete_medication_userAccount.class.getSimpleName();
    private ProgressDialog pDialog, progressDialog1;
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> medicationList;
    private ListView listView;

    private static String url_list_med = "http://dmtthesiscpepmo.comli.com/json_getmedication.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MEDICATION = "server_response";
    private static final String TAG_USER = "user";
    private static final String TAG_MEDICINE = "medicine_name";
    private static final String TAG_SLOT = "slot_number";
    private static final String TAG_DOSAGE = "dosage";
    private static final String TAG_TIME = "time_start";

    EditText username,slot;
    String error,username1;

    @Override
    public void onBackPressed() {

    }


    private void back_delete_med(){
        Button back = (Button) findViewById(R.id.back_bttn5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(delete_medication_userAccount.this, user_activity.class));
            }
        });
    }

    private void search_medication(){
        Button search = (Button) findViewById(R.id.search_on_delete);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String slot_num = slot.getText().toString();
                new search_med_delete_med_userAccount().execute(slot_num);
            }
        });

    }

    private void delete_medication(){
        Button delete = (Button) findViewById(R.id.delete_user_slot4);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String slot_num1 = slot.getText().toString();
                new delete_slot_medication_userAccount().execute(slot_num1);
            }
        });
    }

    class search_med_delete_med_userAccount extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(delete_medication_userAccount.this);
            pDialog.setMessage("Loading user medications. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String slot1;
            slot1 = (String) args[0];

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_list_med);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jArray = jsonObj.getJSONArray("server_response");

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject c = jArray.getJSONObject(i);
                        String user = c.getString(TAG_USER);
                        String med = c.getString(TAG_MEDICINE);
                        String dosage = c.getString(TAG_DOSAGE);
                        String slot = c.getString(TAG_SLOT);
                        String time = c.getString(TAG_TIME);

                        if(user.equals(username1) && slot.equals(slot1)) {

                            HashMap<String, String> map = new HashMap<>();
                            map.put(TAG_MEDICINE, med);
                            map.put(TAG_SLOT, slot);
                            map.put(TAG_DOSAGE, dosage);
                            map.put(TAG_TIME, time);
                            medicationList.add(map);
                        }
                        else if(user.equals(username1) && slot == null){
                            HashMap<String, String> map = new HashMap<>();
                            map.put(TAG_MEDICINE, med);
                            map.put(TAG_SLOT, slot);
                            map.put(TAG_DOSAGE, dosage);
                            map.put(TAG_TIME, time);
                            medicationList.add(map);
                        }
                        else if(user == null){
                            Toast.makeText(getApplicationContext(), "Please enter username.", Toast.LENGTH_SHORT).show();
                        }
                        else if(user.equals(username1) && !slot.equals(slot1)){
                            Toast.makeText(getApplicationContext(), "There is no slot "+slot1+" to user "+username1+". Please try again.", Toast.LENGTH_SHORT).show();
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
            if(pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(delete_medication_userAccount.this, medicationList, R.layout.row_user_medication, new String[]{
                    TAG_MEDICINE, TAG_SLOT, TAG_DOSAGE, TAG_TIME
            }, new int[]{R.id.tv_search_med1, R.id.tv_search_time1, R.id.tv_search_slot1, R.id.tv_search_desc1});
            listView.setAdapter(adapter);

        }
    }

    class delete_slot_medication_userAccount extends AsyncTask<String, Void, String>{

        String add_user_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_user_url = "http://dmtthesiscpepmo.comli.com/add_info.php";

            final AlertDialog.Builder adb = new AlertDialog.Builder(delete_medication_userAccount.this);
            adb.setTitle("Deleting user medication.");
            adb.setMessage("Are you sure you want to delete slot"+slot.getText().toString()+" ?");
            adb.setCancelable(false);
            String yesButton= "YES";
            String noButton = "NO";
            adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progressDialog1 = new ProgressDialog(delete_medication_userAccount.this);
                    progressDialog1.setMessage("Deleting user...");
                    progressDialog1.setIndeterminate(false);
                    progressDialog1.setCancelable(true);
                    progressDialog1.show();
                }
            });
            adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    progressDialog1.cancel();
                    delete_medication.delete_user_slot mytask = null;
                    mytask.cancel(true);
                }
            });
            adb.show();

        }



        @Override
        protected String doInBackground(String... args) {

            String slot1;
            slot1 = (String) args[0];

            try {
                URL url = new URL(add_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username1,"UTF-8")+"&"+
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
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_medication_user_account);
        slot = (EditText) findViewById(R.id.delete_slot_text4);
        medicationList = new ArrayList<HashMap<String, String>>();
        listView = (ListView) findViewById(R.id.lv_delete_user);
        back_delete_med();
        search_medication();
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        username1 = prefs.getString("username","");
    }
}
