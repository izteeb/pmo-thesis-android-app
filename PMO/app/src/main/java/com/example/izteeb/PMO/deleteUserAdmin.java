package com.example.izteeb.PMO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class deleteUserAdmin extends AppCompatActivity {


    private ProgressDialog pDialog, progressDialog1;
    JSONParser jParser = new JSONParser();
    useraccountDeleteAdapter adapter;
    ArrayList<String> userAccountList = new ArrayList();
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
    private static final String TAG_ACCOUNT = "role";

    EditText user_text, account;
    Handler mHandler = new Handler();
    Boolean isRunning = true;

    @Override
    public void onBackPressed() {

    }

    public void backPressed(){
        Button backBttn = (Button) findViewById(R.id.back_bttn9);
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(deleteUserAdmin.this, Admin_activity.class));
            }
        });
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            listView.setEnabled(true);
            listView.setVisibility(View.VISIBLE);

        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue!",Toast.LENGTH_SHORT).show();
            listView.setVisibility(View.INVISIBLE);
        }
    }

    class listUserAccount extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_list_user);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jArray = jsonObj.getJSONArray("user_info");

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject c = jArray.getJSONObject(i);


                        String user = c.getString(TAG_USER);
                        String account = c.getString(TAG_ACCOUNT);


                            Map<String, String> map = new TreeMap<>();

                            map.put(TAG_USER, user);
                            map.put(TAG_ACCOUNT, account);
                            userList.add(map);


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
        protected void onPostExecute(String s) {
                adapter = new useraccountDeleteAdapter(userList, deleteUserAdmin.this);
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

            if(result.equals("Error.")){
                Toast.makeText(getApplicationContext(),"Error deleting user.",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Deleted user successfully.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(deleteUserAdmin.this, deleteUserAdmin.class));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_admin);
        backPressed();
        userList = new ArrayList<Map<String, String>>();
        listView = (ListView) findViewById(R.id.listView4);
        new listUserAccount().execute();

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
                                isNetworkAvailable();
                            }
                        });
                    }
                    catch(Exception e){

                    }
                }
            }
        }).start();
    }

    public class useraccountDeleteAdapter extends BaseAdapter implements ListAdapter {

        private ArrayList<Map<String,String>> list = new ArrayList<>();
        Activity context;

        public useraccountDeleteAdapter(ArrayList<Map<String,String>> list, Activity context){
            super();
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
                view = inflater.inflate(R.layout.row_delete_user, null);
            }

            final TextView accounttext = (TextView) view.findViewById(R.id.row_deleteAccount);
            Button deleteBttn = (Button) view.findViewById(R.id.buttonDeleteUser);
            final TextView userText = (TextView) view.findViewById(R.id.row_deleteUser);

            userText.setText(list.get(i).get("username"));
            accounttext.setText(list.get(i).get("role"));

            deleteBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user1 = userText.getText().toString();
                    String role1 = accounttext.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(role1+" "+user1);
                    builder.setMessage("Are you sure you want to delete "+role1+" "+user1+"?");
                    builder.setIcon(R.drawable.delete_user);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String user = userText.getText().toString();
                            String role = accounttext.getText().toString();
                            new delete_username().execute(user);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            return view;
        }


    }
}
