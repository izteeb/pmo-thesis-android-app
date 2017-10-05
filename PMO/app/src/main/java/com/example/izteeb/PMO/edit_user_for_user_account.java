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
import java.util.Calendar;


public class edit_user_for_user_account extends AppCompatActivity {

    public String url_list_user = "http://dmtthesiscpepmo.comli.com/json_getuser.php";
    public String TAG_USER_VALUE = "username",TAG_FIRST_NAME = "first_name", TAG_LAST_NAME= "last_name", TAG_EMAIL= "email_ad",
            TAG_PASSWORD = "password",TAG_ADDRESS = "address",TAG_GENDER = "gender",TAG_DAY="birthday", TAG_MONTH= "birthmonth", TAG_YEAR= "birthyear";
    public String user2="",first2="",last2="",email2="",password2="",address2="",gender2="";
    public int day2,month2,year2,day_int,month_int,year_int;
    String username,username123, value_gender, value_genderValue,gender123;
    EditText first,last,email,password,confirm,address,day,month,year;
    Button date_button, save_button;
    RadioGroup group_gender;
    RadioButton gender_button;
    ProgressDialog pDialog;
    String first123,last123,email123,password123,address123;
    int day123, month123,year123;
    Handler mHandler = new Handler();
    Boolean isRunning = true;

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            first.setEnabled(true);
            last.setEnabled(true);
            email.setEnabled(true);
            password.setEnabled(true);
            confirm.setEnabled(true);
            address.setEnabled(true);
            day.setEnabled(true);
            month.setEnabled(true);
            year.setEnabled(true);
            group_gender.setEnabled(true);
            date_button.setEnabled(true);
            save_button.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to try again.",Toast.LENGTH_SHORT).show();
            first.setEnabled(false);
            last.setEnabled(false);
            email.setEnabled(false);
            password.setEnabled(false);
            confirm.setEnabled(false);
            address.setEnabled(false);
            day.setEnabled(false);
            month.setEnabled(false);
            year.setEnabled(false);
            group_gender.setEnabled(false);
            date_button.setEnabled(false);
            save_button.setEnabled(false);
        }
    }

    public void findViews() {

        first = (EditText) findViewById(R.id.et511);
        last = (EditText) findViewById(R.id.et521);
        email = (EditText) findViewById(R.id.email12);
        password = (EditText) findViewById(R.id.et561);
        confirm = (EditText) findViewById(R.id.et571);
        address = (EditText) findViewById(R.id.et541);
        day = (EditText) findViewById(R.id.et551);
        month = (EditText) findViewById(R.id.et581);
        year = (EditText) findViewById(R.id.et591);
        group_gender = (RadioGroup) findViewById(R.id.radioSex1);
        date_button = (Button) findViewById(R.id.b521);
        save_button = (Button) findViewById(R.id.proceed_bttn1);

    }


    public void gender_init(){

        group_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int childCount = group.getChildCount();
                for(int x =0 ; x<childCount; x++){
                    gender_button = (RadioButton) group.getChildAt(x);
                    if(gender_button.getId() == R.id.radioFemale1){
                        gender_button.setText("Female");
                    }
                    else{
                        gender_button.setText("Male");
                    }

                    if(gender_button.getId() == checkedId){

                        value_gender = gender_button.getText().toString();

                    }
                }

            }
        });
    }

    public void dateButtonClicked() {


        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar dateTime = Calendar.getInstance();
                int mYear = dateTime.get(Calendar.YEAR);
                int mMonth = dateTime.get(Calendar.MONTH);
                int mDay = dateTime.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(edit_user_for_user_account.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth = selectedMonth + 1;
                        if(selectedYear>=2010){
                            Toast.makeText(getApplicationContext(), "You are not eligible to use this application.",Toast.LENGTH_LONG).show();
                        }
                        else {
                            day_int = selectedDay;
                            month_int = selectedMonth;
                            year_int = selectedYear;
                            day.setText(Integer.toString(selectedDay));
                            month.setText(Integer.toString(selectedMonth));
                            year.setText(Integer.toString(selectedYear));
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
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first.getText().toString().isEmpty() ||
                        last.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty() ||
                        confirm.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter all information to continue.",Toast.LENGTH_SHORT).show();
                }
                else if (!password.getText().toString().equals(confirm.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password doesn't match. Please try again.",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!emailValidate.validate(email.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(),"Invalid email address. Please try again to continue.",Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if (day_int >31 || month_int >12) {
                            Toast.makeText(getApplicationContext(), "Date invalid. Please try again to continue.",Toast.LENGTH_SHORT).show();
                        } else {
                            if (password.getText().toString().length() < 5) {
                                Toast.makeText(getApplicationContext(), "Password length must be 6 or more. Please try again to continue.", Toast.LENGTH_SHORT).show();
                            } else {

                                    String first1 = first.getText().toString();
                                    String last1 = last.getText().toString();
                                    String email1 = email.getText().toString();
                                    String password1 = password.getText().toString();
                                    String address1 = address.getText().toString();
                                    String birthday1 = Integer.toString(day_int);
                                    String birthmonth1 = Integer.toString(month_int);
                                    String birthyear1 = Integer.toString(year_int);
                                    String gender1 = value_gender;
                                    String user1 = username;

                                    new save_user().execute(first1, last1, email1, password1, address1, birthday1, birthmonth1, birthyear1, gender1, user1);

                            }
                        }
                    }

                }
            }
        });
    }

    public class userInformation extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {


            SharedPreferences prefs = getSharedPreferences("user_a", MODE_PRIVATE);
            username123 = prefs.getString("username", "");

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


                        if (user2.equals(username123)) {
                            first123 = first2;
                            last123 = last2;
                            email123 = email2;
                            password123 = password2;
                            address123 = address2;
                            day123 = day2;
                            month123 = month2;
                            year123 = year2;
                            gender123 = gender2;


                        }

                    }

                } catch (JSONException ex) {

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


                first.setText(first123);
                last.setText(last123);
                email.setText(email123);
                password.setText(password123);
                confirm.setText(password123);
                address.setText(address123);
                day.setText(Integer.toString(day123));
                month.setText(Integer.toString(month123));
                year.setText(Integer.toString(year123));
/*
            if(gender123.equalsIgnoreCase("Male")) {
                checkedId = R.id.radioMale1;
            }
            else if(gender123.equalsIgnoreCase("Female")){
                checkedId = R.id.radioFemale1;
            }
*/
            RadioButton rb1 = (RadioButton) findViewById(R.id.radioMale1);
            RadioButton rb2 = (RadioButton) findViewById(R.id.radioFemale1);
            if(gender123.equalsIgnoreCase("Male")) {
              rb1.setChecked(true);
            }
            else if(gender123.equalsIgnoreCase("Female")){
                rb2.setChecked(true);
            }
                }
    }

    class save_user extends AsyncTask<String, Void, String>{

        String update_user_url;
        @Override
        protected void onPreExecute() {
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
                startActivity(new Intent(edit_user_for_user_account.this, user_activity.class));

        }
    }







    public void backButtonPressed(){
        Button back = (Button) findViewById(R.id.backButton1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(edit_user_for_user_account.this,user_activity.class));
            }
        });

    }

    public void initUsernameDisplay(){

        TextView username_display = (TextView) findViewById(R.id.edit_username_text);
        SharedPreferences prefs = getSharedPreferences("user_a", MODE_PRIVATE);
        username = prefs.getString("username","");
        username_display.setText(username+"'s"+" information.");
    }



    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_for_user_account);
        backButtonPressed();
        initUsernameDisplay();
        findViews();
        gender_init();
        dateButtonClicked();
        saveButtonClicked();
        new userInformation().execute();

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
