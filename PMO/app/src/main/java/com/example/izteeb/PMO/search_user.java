package com.example.izteeb.PMO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by Stevens Tabangay on 7/13/2017.
 */

public class search_user extends AppCompatActivity {

    private String TAG = search_user.class.getSimpleName();
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    ArrayList<Map<String, String>> userList;
    private ListView listView;
    Button backBttn, search_user;
    EditText user_text;
    private static String url_list_user = "http://dmtthesiscpepmo.comli.com/json_getuser.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MEDICATION = "server_response";
    private static final String TAG_FIRST = "first_name";
    private static final String TAG_LAST = "last_name";
    private static final String TAG_USER = "username";
    private static final String TAG_EMAIL = "email_ad";
    private static final String TAG_PW = "password";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_BIRTH = "birthday";

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    @Override
    public void onBackPressed() {

    }

    public void back_search_user(){
       backBttn = (Button) findViewById(R.id.back_bttn31);
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(search_user.this, Admin_activity.class));
            }
        });
    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            search_user.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue.",Toast.LENGTH_SHORT).show();
            search_user.setEnabled(false);
        }
    }

    public void search_user_info(){
        search_user = (Button) findViewById(R.id.search_user_bttn);
        user_text = (EditText) findViewById(R.id.search_username_text3);
        search_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_string = user_text.getText().toString();
                new Load_Search_User().execute(user_string);
            }
        });
    }

    class Load_Search_User extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String user1;
            user1 = (String) args[0];
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_list_user);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jArray = jsonObj.getJSONArray("user_info");

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject c = jArray.getJSONObject(i);

                        String first = c.getString(TAG_FIRST);
                        String last = c.getString(TAG_LAST);
                        String user = c.getString(TAG_USER);
                        String email = c.getString(TAG_EMAIL);
                        String pass = c.getString(TAG_PW);
                        String address = c.getString(TAG_ADDRESS);
                        String gender = c.getString(TAG_GENDER);
                        String birth = c.getString(TAG_BIRTH);

                        if (user.equals(user1)) {
                            Map<String, String> map = new TreeMap<>();
                            map.put(TAG_FIRST, first);
                            map.put(TAG_LAST, last);
                            map.put(TAG_USER, user);
                            map.put(TAG_EMAIL, email);
                            map.put(TAG_PW, pass);
                            map.put(TAG_ADDRESS, address);
                            map.put(TAG_GENDER, gender);
                            map.put(TAG_BIRTH, birth);
                            userList.add(map);
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
                                "Internet connection needed. Please try again to continue.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (userList.size() == 0) {
                Toast.makeText(getApplicationContext(), "There is no user" + user_text.getText().toString() + ". Please try again.", Toast.LENGTH_SHORT).show();
            }
            ListAdapter adapter = new SimpleAdapter(search_user.this, userList, R.layout.row_user, new String[]{
                    TAG_FIRST, TAG_LAST, TAG_USER, TAG_EMAIL, TAG_PW, TAG_ADDRESS, TAG_GENDER, TAG_BIRTH
            }, new int[]{R.id.ru5, R.id.ru6, R.id.ru7, R.id.ru8, R.id.ru9, R.id.ru10, R.id.ru12, R.id.ru11});
            listView.setAdapter(adapter);

        }
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_user);

            userList = new ArrayList<Map<String, String>>();
            back_search_user();
            listView = (ListView) findViewById(R.id.lv_search_user);
            search_user_info();

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
