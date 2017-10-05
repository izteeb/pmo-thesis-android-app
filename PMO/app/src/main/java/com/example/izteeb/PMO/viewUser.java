package com.example.izteeb.PMO;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Izteeb on 4/17/2017.
 */

public class viewUser extends AppCompatActivity{

    private String TAG = viewUser.class.getSimpleName();
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    ArrayList<Map<String, String>> userList;
    private ListView listView;
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

    @Override
    public void onBackPressed() {

    }

    public void backView(){
        Button backBttn = (Button) findViewById(R.id.b62);
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewUser.this, Admin_activity.class));
            }
        });

    }

    JSONArray server_response = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_listview);
        userList = new ArrayList<Map<String, String>>();
        backView();
        listView = (ListView) findViewById(R.id.list6);
        new LoadUserList().execute();

    }

    private class LoadUserList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(viewUser.this);
            pDialog.setMessage("Loading user information. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

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
                                "Internet connection must be open to view users. Please try again to continue.",
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
            ListAdapter adapter = new SimpleAdapter(viewUser.this, userList, R.layout.row_user, new String[]{
                    TAG_FIRST,TAG_LAST,TAG_USER,TAG_EMAIL,TAG_PW,TAG_ADDRESS,TAG_GENDER,TAG_BIRTH
            }, new int[]{R.id.ru5, R.id.ru6, R.id.ru7, R.id.ru8,R.id.ru9, R.id.ru10, R.id.ru12,R.id.ru11});
            listView.setAdapter(adapter);


        }

    }

}