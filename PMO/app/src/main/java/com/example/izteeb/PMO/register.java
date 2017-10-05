package com.example.izteeb.PMO;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


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
import java.util.HashMap;
import java.util.regex.Pattern;


public class register extends AppCompatActivity {

    RadioGroup sexGroupRadio;
    RadioButton sexButtonRadio;
    Button backBttn, proceedBttn, dateBttn1;
    EditText firstText, lastText, userText, emailText, passwordText, confirmText, addressText, dayText, monthText,yearText;
    TextView g2,g3;
    private ProgressDialog progressDialog1;
    String error, gender_value, gender_passValue;
    int day_int, month_int, year_int;

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    public void findview_init(){


        backBttn = (Button) findViewById(R.id.backButton);
        proceedBttn = (Button) findViewById(R.id.proceed_bttn);
        firstText = (EditText) findViewById(R.id.et51);
        lastText = (EditText) findViewById(R.id.et52);
        userText = (EditText) findViewById(R.id.et53);
        emailText= (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.et56);
        confirmText = (EditText) findViewById(R.id.et57);
        addressText = (EditText) findViewById(R.id.et54);
        dayText = (EditText) findViewById(R.id.et55);
        monthText = (EditText) findViewById(R.id.et58);
        yearText= (EditText) findViewById(R.id.et59);

    }

    public void gender_init(){
        sexGroupRadio = (RadioGroup) findViewById(R.id.radioSex);
        sexGroupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
             int childCount = group.getChildCount();

                for(int x =0 ; x<childCount; x++){
                    sexButtonRadio = (RadioButton) group.getChildAt(x);
                    if(sexButtonRadio.getId() == R.id.radioFemale){
                       sexButtonRadio.setText("Female");
                    }
                    else{
                        sexButtonRadio.setText("Male");
                    }
                    if(sexButtonRadio.getId() == checkedId){
                        gender_value = sexButtonRadio.getText().toString();
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void init_date() {


        dateBttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar dateTime = Calendar.getInstance();
                int mYear = dateTime.get(Calendar.YEAR);
                int mMonth = dateTime.get(Calendar.MONTH);
                int mDay = dateTime.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(register.this, new DatePickerDialog.OnDateSetListener() {
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
                            dayText.setText(Integer.toString(selectedDay));
                            monthText.setText(Integer.toString(selectedMonth));
                            yearText.setText(Integer.toString(selectedYear));
                        }
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date:");
                mDatePicker.show();

            }
        });
    }





    private void backPressed(){
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,title1.class ));
            }
        });
    }

    public void registerPressed(){


        final emailValidator emailValidate = new emailValidator();
        proceedBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(firstText.getText().toString().isEmpty() ||
                            lastText.getText().toString().isEmpty() ||
                            userText.getText().toString().isEmpty() ||
                            emailText.getText().toString().isEmpty() ||
                            passwordText.getText().toString().isEmpty() ||
                            confirmText.getText().toString().isEmpty()){

                        Toast.makeText(getApplicationContext(),"Please enter all information to continue.",Toast.LENGTH_SHORT).show();
                    }
                    else if (!passwordText.getText().toString().equals(confirmText.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Password doesn't match. Please try again.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!emailValidate.validate(emailText.getText().toString().trim())){
                            Toast.makeText(getApplicationContext(),"Invalid email address. Please try again to continue.",Toast.LENGTH_SHORT).show();
                        }

                        else {
                            if (passwordText.getText().toString().length() < 5) {
                                Toast.makeText(getApplicationContext(), "Password length must be 6 or more. Please try again to continue.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (day_int >31 || month_int > 12) {
                                        Toast.makeText(getApplicationContext(), "Date invalid. Please try again to continue.",Toast.LENGTH_SHORT).show();
                                } else {

                                        String first = firstText.getText().toString();
                                        String last = lastText.getText().toString();
                                        String user = userText.getText().toString();
                                        String email = emailText.getText().toString();
                                        String password = passwordText.getText().toString();
                                        String address = addressText.getText().toString();
                                        String birthday = Integer.toString(day_int);
                                        String birthmonth = Integer.toString(month_int);
                                        String birthyear = Integer.toString(year_int);
                                        String gender = gender_value;
                                        String role = "user";
                                        new register_user().execute(first, last, user, email, password, address, birthday, birthmonth, birthyear, gender);

                                }
                            }
                        }

                    }
            }
        });
    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            proceedBttn.setEnabled(true);
            firstText.setEnabled(true);
            lastText.setEnabled(true);
            userText.setEnabled(true);
            emailText.setEnabled(true);
            passwordText.setEnabled(true);
            confirmText.setEnabled(true);
            addressText.setEnabled(true);

        }
        else {
            Toast.makeText(getApplicationContext(),"Please connect to internet  to register.",Toast.LENGTH_SHORT).show();
            proceedBttn.setEnabled(false);
            firstText.setEnabled(false);
            lastText.setEnabled(false);
            userText.setEnabled(false);
            emailText.setEnabled(false);
            passwordText.setEnabled(false);
            confirmText.setEnabled(false);
            addressText.setEnabled(false);

        }
    }


    class register_user extends AsyncTask<String, Void, String>{

        String add_user_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_user_url = "http://dmtthesiscpepmo.comli.com/add_info.php";


        }
        @Override
        protected String doInBackground(String... args) {
//     backgroundTask.execute(first1,last1,gender,day1,address1,emailaddress1,user,password,role);

            String username12,password12, emailaddress1, first1, last1, address1, day1,month1, year1, gender1,role1;
            first1= (String) args[0];
            last1= (String)args[1];
            username12 = (String) args[2];
            emailaddress1= (String) args[3];
            password12 = (String) args[4];
            address1= (String)args[5];
            day1 =(String)args[6];
            month1 = (String) args[7];
            year1 = (String) args[8];
            gender1 = (String) args[9];


            try {
                URL url = new URL(add_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username12,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password12,"UTF-8")+"&"+
                        URLEncoder.encode("email_ad","UTF-8")+"="+URLEncoder.encode(emailaddress1,"UTF-8")+"&"+
                        URLEncoder.encode("first_name","UTF-8")+"="+URLEncoder.encode(first1,"UTF-8")+"&"+
                        URLEncoder.encode("last_name","UTF-8")+"="+URLEncoder.encode(last1,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address1,"UTF-8")+"&"+
                        URLEncoder.encode("birthday","UTF-8")+"="+URLEncoder.encode(day1,"UTF-8")+"&"+
                        URLEncoder.encode("birthmonth","UTF-8")+"="+URLEncoder.encode(month1,"UTF-8")+"&"+
                        URLEncoder.encode("birthyear","UTF-8")+"="+URLEncoder.encode(year1,"UTF-8")+"&"+
                        URLEncoder.encode("role","UTF-8")+"="+URLEncoder.encode("user","UTF-8")+"&"+
                        URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender1,"UTF-8");
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

            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
           error = result;
           // g3.setText(result);
            if(error.equals("Error.")){
                Toast.makeText(getApplicationContext(),"User already exists.",Toast.LENGTH_SHORT).show();
            }
            else{
                startActivity(new Intent(register.this, login_page.class));
                Toast.makeText(getApplicationContext(),"User registered. Please login to continue.",Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dateBttn1 = (Button) findViewById(R.id.b52);
        g2 = (TextView) findViewById(R.id.g1);
        g3= (TextView) findViewById(R.id.g2);

        init_date();
        findview_init();
        backPressed();

        registerPressed();
        gender_init();
        g2.setVisibility(View.INVISIBLE);

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

