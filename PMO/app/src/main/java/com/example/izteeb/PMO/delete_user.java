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


public class delete_user extends AppCompatActivity {

    private String TAG = search_user.class.getSimpleName();
    private ProgressDialog pDialog, progressDialog1;
    JSONParser jParser = new JSONParser();

    ArrayList<String> userAccountList = new ArrayList();
    ArrayList<HashMap<String, String>> userList;
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
    private static final String TAG_ACCOUNT = "account";

    EditText user_text, account;
    String error;

    @Override
    public void onBackPressed() {

    }

    public void back_delete_user(){
        Button back = (Button) findViewById(R.id.back_bttn4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(delete_user.this, Admin_activity.class));
            }
        });
    }

    private void search_on_delete_username(){
        Button search = (Button) findViewById(R.id.search_on_delete);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_string = user_text.getText().toString();
                String account_string = account.getText().toString();
                new search_on_delete_user().execute(user_string);
            }
        });
    }

    public void search_on_delete_accounts(){
        Button search_accountBttn = (Button) findViewById(R.id.search_user_account);
        search_accountBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account_string = account.getText().toString().toLowerCase();
                new search_on_delete_account().execute(account_string);
            }
        });
    }

    private void delete_user_info_username(){
        Button delete = (Button) findViewById(R.id.delete_user);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(delete_user.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete User");
                adb.setMessage("Are you sure you want to delete user"+user_text.getText().toString()+"?");
                adb.setCancelable(true);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user2  = user_text.getText().toString();
                        new delete_username().execute(user2);
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
    }

    public void delete_user_account(){
        Button delete_accountBttn = (Button) findViewById(R.id.delete_user_account);
        delete_accountBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(delete_user.this);
                adb.setIcon(R.mipmap.pmo_icon);
                adb.setTitle("Delete User");
                adb.setMessage("Are you sure you want to delete user"+user_text.getText().toString()+"?");
                adb.setCancelable(true);
                String yesButton = "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String account_string = account.getText().toString().toLowerCase();
                        new delete_accounts().execute(account_string);
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
    }

    class delete_accounts extends AsyncTask<String, Void, String> {

        String delete_info_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete_info_url = "http://dmtthesiscpepmo.comli.com/delete_role.php";


        }
        @Override
        protected String doInBackground(String... args) {

            String account;
            account= (String)args[0];


            try {
                URL url = new URL(delete_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("role","UTF-8")+"="+URLEncoder.encode(account,"UTF-8");
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


    class search_on_delete_user extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(delete_user.this);
            pDialog.setMessage("Loading user information. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String user1,account1;
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
                        String account = c.getString(TAG_ACCOUNT);

                        if(user.equals(user1)){
                            userAccountList.add(user);
                            HashMap<String, String> map = new HashMap<>();
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
            if(pDialog.isShowing()) {
                pDialog.dismiss();
                if (userAccountList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "User not available. Please try again to continue.", Toast.LENGTH_SHORT).show();
                    listView.setVisibility(View.INVISIBLE);
                } else {
                    ListAdapter adapter = new SimpleAdapter(delete_user.this, userList, R.layout.row_user, new String[]{
                            TAG_FIRST, TAG_LAST, TAG_USER, TAG_EMAIL, TAG_PW, TAG_ADDRESS, TAG_GENDER, TAG_BIRTH
                    }, new int[]{R.id.ru5, R.id.ru6, R.id.ru7, R.id.ru8, R.id.ru9, R.id.ru10, R.id.ru12, R.id.ru11});
                    listView.setAdapter(adapter);
                }
            }
        }
    }

    class search_on_delete_account extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(delete_user.this);
            pDialog.setMessage("Loading accounts. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String user1,account1;
            account1 = (String) args[0];
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
                        String account = c.getString(TAG_ACCOUNT);

                        if(account.equals(account1)){
                            HashMap<String, String> map = new HashMap<>();
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
            if(pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(delete_user.this, userList, R.layout.row_user, new String[]{
                    TAG_FIRST,TAG_LAST,TAG_USER,TAG_EMAIL,TAG_PW,TAG_ADDRESS,TAG_GENDER,TAG_BIRTH
            }, new int[]{R.id.ru5, R.id.ru6, R.id.ru7, R.id.ru8,R.id.ru9, R.id.ru10, R.id.ru12,R.id.ru11});
            listView.setAdapter(adapter);

        }
    }


    class delete_username extends AsyncTask<String, Void, String>{

        String delete_user_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete_user_url = "http://dmtthesiscpepmo.comli.com/delete_username.php";

        }



        @Override
        protected String doInBackground(String... args) {


            String user_json;
            user_json = (String) args[0];


            try {
                URL url = new URL(delete_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_json,"UTF-8");
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
        setContentView(R.layout.activity_delete_user);
        user_text = (EditText) findViewById(R.id.delete_username_text3);
        account = (EditText) findViewById(R.id.delete_account_text);
        userList = new ArrayList<HashMap<String, String>>();
        listView = (ListView) findViewById(R.id.lv_delete_slot_med);

        back_delete_user();
        search_on_delete_accounts();
        search_on_delete_username();
        delete_user_info_username();
        delete_user_account();
    }
}
