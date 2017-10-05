package com.example.izteeb.PMO;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import java.util.Calendar;


public class edit_user_for_admin_account extends AppCompatActivity {


    Button back_bttn, up_bttn, down_bttn, save_bttn, calendar_bttn;
    RelativeLayout hid_layout;
    EditText user_text, first_text, last_text, email_text, password_text,confirm_text,address_text,day_text,month_text,year_text;
    ProgressDialog pDialog;
    RadioGroup group_gender1;
    RadioButton gender_button1;
    Handler mHandler = new Handler();
    Boolean isRunning = true;

    public String url_list_user = "http://dmtthesiscpepmo.comli.com/json_getuser.php";

    public String TAG_USER_VALUE = "username",TAG_FIRST_NAME = "first_name", TAG_LAST_NAME= "last_name", TAG_EMAIL= "email_ad",
            TAG_PASSWORD = "password",TAG_ADDRESS = "address",TAG_GENDER = "gender",TAG_DAY="birthday", TAG_MONTH= "birthmonth", TAG_YEAR= "birthyear";
    public String user2="",first2="",last2="",email2="",password2="",address2="",gender2="";
    String first123,last123,email123,password123,address123,day123,month123,year123;
    String user_temp,value_gender;
    int day_temp, month_temp, year_temp,day2,month2,year2;

    public void findViews(){

        user_text = (EditText) findViewById(R.id.user2_tie);
        first_text = (EditText) findViewById(R.id.et511);
        last_text = (EditText) findViewById(R.id.et521);
        email_text = (EditText) findViewById(R.id.email12);
        password_text = (EditText) findViewById(R.id.et561);
        confirm_text = (EditText) findViewById(R.id.et571);
        address_text = (EditText) findViewById(R.id.et541);
        day_text = (EditText) findViewById(R.id.et551);
        month_text = (EditText) findViewById(R.id.et581);
        year_text = (EditText) findViewById(R.id.et591);
        back_bttn = (Button) findViewById(R.id.backButton1);
        up_bttn = (Button) findViewById(R.id.arrow_up);
        save_bttn = (Button) findViewById(R.id.proceed_bttn1);
        calendar_bttn = (Button) findViewById(R.id.b521);
        hid_layout = (RelativeLayout) findViewById(R.id.rl42);

    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            user_text.setEnabled(true);
            first_text.setEnabled(true);
            last_text.setEnabled(true);
            email_text.setEnabled(true);
            password_text.setEnabled(true);
            confirm_text.setEnabled(true);
            address_text.setEnabled(true);
            day_text.setEnabled(true);
            month_text.setEnabled(true);
            year_text.setEnabled(true);
            save_bttn.setEnabled(true);
            up_bttn.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue.",Toast.LENGTH_SHORT).show();
            user_text.setEnabled(false);
            first_text.setEnabled(false);
            last_text.setEnabled(false);
            email_text.setEnabled(false);
            password_text.setEnabled(false);
            confirm_text.setEnabled(false);
            address_text.setEnabled(false);
            day_text.setEnabled(false);
            month_text.setEnabled(false);
            year_text.setEnabled(false);
            save_bttn.setEnabled(false);
            up_bttn.setEnabled(false);
        }
    }

    public void showLayout(){

        hid_layout.setVisibility(View.INVISIBLE);
        up_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_temp = user_text.getText().toString();
                up_bttn.setVisibility(View.VISIBLE);
                hid_layout.setVisibility(View.VISIBLE);
                new user_information().execute();
                }
        });
    }

    public void backButtonPressed(){
        back_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(edit_user_for_admin_account.this, Admin_activity.class));
            }
        });
    }

    public void gender_init(){
        group_gender1 = (RadioGroup) findViewById(R.id.radioSex1);
        group_gender1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int childCount = group.getChildCount();

                for(int x =0 ; x<childCount; x++){
                    gender_button1 = (RadioButton) group.getChildAt(x);
                    if(gender_button1.getId() == R.id.radioFemale1){
                      gender_button1.setText("Female");
                    }
                    else{
                        gender_button1.setText("Male");
                    }
                    if(gender_button1.getId() == checkedId){
                        value_gender = gender_button1.getText().toString();
                    }
                }

            }
        });
    }

    public void dateButtonClicked() {


        calendar_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar dateTime = Calendar.getInstance();
                int mYear = dateTime.get(Calendar.YEAR);
                int mMonth = dateTime.get(Calendar.MONTH);
                int mDay = dateTime.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(edit_user_for_admin_account.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth = selectedMonth + 1;
                        if(selectedYear>=2010){
                            Toast.makeText(getApplicationContext(), "You are not eligible to use this application.",Toast.LENGTH_LONG).show();
                        }
                        else {
                            day_temp = selectedDay;
                            month_temp = selectedMonth;
                            year_temp = selectedYear;
                            day_text.setText(Integer.toString(selectedDay));
                            month_text.setText(Integer.toString(selectedMonth));
                            year_text.setText(Integer.toString(selectedYear));
                        }
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date:");
                mDatePicker.show();

            }
        });
    }

    public void saveButtonClicked(){
        final emailValidator emailValidate = new emailValidator();
        save_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first_text.getText().toString().isEmpty() ||
                        last_text.getText().toString().isEmpty() ||
                        email_text.getText().toString().isEmpty() ||
                        password_text.getText().toString().isEmpty() ||
                        confirm_text.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter all information to continue.",Toast.LENGTH_SHORT).show();
                }
                else if (!password_text.getText().toString().equals(confirm_text.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password doesn't match. Please try again.",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (day_temp > 31 || month_temp > 12) {
                        Toast.makeText(getApplicationContext(), "Date invalid. Please try again to continue.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                    if (!emailValidate.validate(email_text.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "Invalid email address. Please try again to continue.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password_text.getText().toString().length() < 5) {
                            Toast.makeText(getApplicationContext(), "Password length must be 6 or more. Please try again to continue.", Toast.LENGTH_SHORT).show();
                        } else {


                                String first1 = first_text.getText().toString();
                                String last1 = last_text.getText().toString();
                                String email1 = email_text.getText().toString();
                                String password1 = password_text.getText().toString();
                                String address1 = address_text.getText().toString();
                                String birthday1 = Integer.toString(day_temp);
                                String birthmonth1 = Integer.toString(month_temp);
                                String birthyear1 = Integer.toString(year_temp);
                                String gender1 = value_gender;
                                String username1 = user_text.getText().toString();

                                new save_user().execute(first1, last1, email1, password1, address1, birthday1, birthmonth1, birthyear1, gender1,username1);

                    }
                    }
                }
                }

                }
        });
    }
    class save_user extends AsyncTask<String, Void, String>{

        String update_user_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            update_user_url = "http://dmtthesiscpepmo.comli.com/update_userinfo.php";

        }

        @Override
        protected String doInBackground(String... args) {

            String password12, emailaddress12, first12, last12, address12, day12,month12, year12, gender12,user12;
            first12= (String) args[0];
            last12= (String)args[1];
            emailaddress12= (String) args[2];
            password12 = (String) args[3];
            address12= (String)args[4];
            day12 =(String)args[5];
            month12 = (String) args[6];
            year12 = (String) args[7];
            gender12 = (String) args[8];
            user12 = (String) args[9];


            try {
                URL url = new URL(update_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string =
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password12,"UTF-8")+"&"+
                                URLEncoder.encode("email_ad","UTF-8")+"="+URLEncoder.encode(emailaddress12,"UTF-8")+"&"+
                                URLEncoder.encode("first_name","UTF-8")+"="+URLEncoder.encode(first12,"UTF-8")+"&"+
                                URLEncoder.encode("last_name","UTF-8")+"="+URLEncoder.encode(last12,"UTF-8")+"&"+
                                URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address12,"UTF-8")+"&"+
                                URLEncoder.encode("birthday","UTF-8")+"="+URLEncoder.encode(day12,"UTF-8")+"&"+
                                URLEncoder.encode("birthmonth","UTF-8")+"="+URLEncoder.encode(month12,"UTF-8")+"&"+
                                URLEncoder.encode("birthyear","UTF-8")+"="+URLEncoder.encode(year12,"UTF-8")+"&"+
                                URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender12,"UTF-8")+"&"+
                                URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user12,"UTF-8");

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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

                Toast.makeText(getApplicationContext(),"User information updated.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(edit_user_for_admin_account.this, Admin_activity.class));
            }
        }



    @Override
    public void onBackPressed() {

    }

    class user_information extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(edit_user_for_admin_account.this);
            pDialog.setMessage("Loading user's information...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            HttpHandler sh = new HttpHandler();
            String jsonString = sh.makeServiceCall(url_list_user);
            if (jsonString != null) {
                try {
                    JSONObject jobj = new JSONObject(jsonString);
                    JSONArray jarray = jobj.getJSONArray("user_info");

                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject c = jarray.getJSONObject(i);
                        user2 = c.getString(TAG_USER_VALUE);
                        first2 = c.getString(TAG_FIRST_NAME);
                        last2 = c.getString(TAG_LAST_NAME);
                        email2 = c.getString(TAG_EMAIL);
                        password2 = c.getString(TAG_PASSWORD);
                        address2 = c.getString(TAG_ADDRESS);
                        gender2 = c.getString(TAG_GENDER);
                        day2 = c.getInt(TAG_DAY);
                        month2 = c.getInt(TAG_MONTH);
                        year2 = c.getInt(TAG_YEAR);

                        if (user2.equals(user_temp)) {
                            first123 = first2;
                            last123 = last2;
                            email123 = email2;
                            password123 = password2;
                            address123 = address2;
                            day123 = Integer.toString(day2);
                            month123 = Integer.toString(month2);
                            year123 = Integer.toString(year2);

                        }

                    }

                } catch (JSONException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No information found.", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog.isShowing()) {
                pDialog.dismiss();

                if(first123 == null){
                    Toast.makeText(getApplicationContext(),"User not found. Please try again to continue.",Toast.LENGTH_SHORT).show();
                }
                else {
                    first_text.setText(first123);
                    last_text.setText(last123);
                    email_text.setText(email123);
                    password_text.setText(password123);
                    confirm_text.setText(password123);
                    address_text.setText(address123);
                    day_text.setText(day123);
                    month_text.setText(month123);
                    year_text.setText(year123);
                }

            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_for_admin_account);

        findViews();
        showLayout();
        backButtonPressed();
        gender_init();
        dateButtonClicked();
        saveButtonClicked();

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
