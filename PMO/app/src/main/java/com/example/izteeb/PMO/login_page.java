package com.example.izteeb.PMO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class login_page extends AppCompatActivity {

    public String url_list_user = "http://dmtthesiscpepmo.comli.com/json_getuser.php";
    public TextView uaf;
    public Button cancel;
    public Button login;
    public EditText username;
    public EditText password;
    int counter= 3;
    private ProgressDialog progressDialog1;
    private static final String USER = "user";
    private static final String ADMIN = "admin";
    String newResult;
    ArrayList<String> userList = new ArrayList();
    Boolean userChecker = false;

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    public void init_login() {

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {

                    SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
                    prefs.edit().putString("username",username.getText().toString()).commit();
                    Intent intent = new Intent(login_page.this, Admin_activity.class);
                    startActivity(intent);


                } else if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please input username and password.", Toast.LENGTH_LONG).show();

                } else {

                    String user = username.getText().toString();
                    String pass = password.getText().toString();

                    new login_user().execute(user,pass);
                    new insert_current_logged().execute(user);
                    new checkUserOnDatabase().execute();


                }
            }

        });
    }


                /*else {
                    counter--;

                    Toast.makeText(getApplicationContext(), "Incorrect Username and Password. Attempt available: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter == 0) {
                        System.exit(1);
                    }
                }
                */
                /*
                else {
                    mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(login_page.this, "Login successfully.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(login_page.this, user_activity.class));
                            } else {
                                Log.e("ERROR", task.getException().toString());

                                Toast.makeText(login_page.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        */

    @Override
    public void onBackPressed() {

    }


    class checkUserOnDatabase extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpHandler sh = new HttpHandler();
            String jsonString = sh.makeServiceCall(url_list_user);
            if (jsonString != null) {
                try {
                    JSONObject jobj = new JSONObject(jsonString);
                    JSONArray jarray = jobj.getJSONArray("user_info");

                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject c = jarray.getJSONObject(i);
                        String user12 = c.getString("username");

                        userList.add(user12);

                    }

                }catch (final JSONException e) {

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


        }
    }

    class insert_current_logged extends AsyncTask<String, Void, String> {

        String add_user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_user_url = "http://dmtthesiscpepmo.comli.com/current_logged.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String username12;
            username12 = (String) args[0];

            try {
                URL url = new URL(add_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(username12, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
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

        }
    }

    public void init_cancel(){
        cancel = (Button) findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(login_page.this, title1.class));
                    }
                });
    }

    public void internetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            login.setEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue.",Toast.LENGTH_SHORT).show();
            login.setEnabled(false);

        }
    }

    class login_user extends AsyncTask<String, Void, String> {

        String add_user_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_user_url = "http://dmtthesiscpepmo.comli.com/login_user.php";
            progressDialog1 = new ProgressDialog(login_page.this);
            progressDialog1.setMessage("Logging in...");
            progressDialog1.setIndeterminate(false);
            progressDialog1.setCancelable(true);
            progressDialog1.show();
        }

        @Override
        protected String doInBackground(String... args) {
//     backgroundTask.execute(first1,last1,gender,day1,address1,emailaddress1,user,password,role);

            String username12, password12;

            username12 = (String) args[0];
            password12 = (String) args[1];


            try {
                URL url = new URL(add_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username12, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password12, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
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
            progressDialog1.dismiss();
            newResult = result;


            if(newResult.trim().equals("admin")){
                String user = username.getText().toString();
                new insert_current_logged().execute(user);
                SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
                prefs.edit().putString("role",newResult).commit();
                prefs.edit().putString("username",username.getText().toString()).commit();
                startActivity(new Intent(login_page.this, Admin_activity.class));

            }
            else if(newResult.trim().equals("user")){
                String user = username.getText().toString();
                new insert_current_logged().execute(user);
                SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
                prefs.edit().putString("role",newResult).commit();
                prefs.edit().putString("username",username.getText().toString()).commit();
                startActivity(new Intent(login_page.this, user_activity.class));


            }
            else if(newResult.trim().equals("failed")){

                for(int i=0; i<userList.size(); i++){
                    if(!userList.contains(username.getText().toString())){
                         Toast.makeText(getApplicationContext(),"User "+username.getText()+" is not registered. Please register first!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Incorrect username or password. Please try again to continue.",Toast.LENGTH_SHORT).show();
                        break;
                    }

                }

            }


        }
    }



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        init_cancel();
        init_login();

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
