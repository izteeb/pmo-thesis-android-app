package com.example.izteeb.PMO;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


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
import java.util.List;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;

import static java.util.Calendar.AM;
import static java.util.Calendar.AM_PM;
import static java.util.Calendar.PM;
import static java.util.Calendar.getInstance;


public class slot7 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int minDuration =0;
    int minQuantity=0;
    int minDosage=0;
    int h,m;
    SharedPreferences.Editor editor;
    Button done,time12, date12;
    EditText medName, dosageEdit, quantityEdit,descriptionEdit;
    TextView dosage,timeText, minText, ampmText,dayText, monthText, yearText, slot, user1;
    Spinner intervalSpinner;
    final static int RQS_1 = 1;
    TimePickerDialog mTimePicker;
    int minTime, hourTime, minInt, hourInt, minCInt,hourCInt;
    String amOrpm, ampmC;
    String userString;
    private String userCurrent;
    public static final String MyPreferences = "MyPrefs";
    public static final String medicine= "medKey";
    public static final String dosageP = "dosageKey";
    SharedPreferences sharedPreferences;

    String[] durations = {"days","weeks","months","years"};
    String[] intervals = {"Every 12 hours", "Every 10 hours","Every 8 hours","Every 6 hours","Every 4 hours", "Every 2 hours", "Every hour" };
    String[] dosage_unit = {"grams","mg","pcs","pills","tablets"};
    private ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_medication= "http://pmothesis.comli.com/create_medication.php";
    private static final String TAG_SUCCESS= "success";
    private DatabaseReference mDatabase;
    String intervalItem;

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    public void findView() {


        slot = (TextView) findViewById(R.id.tv19);
        done = (Button) findViewById(R.id.b12);
        medName= (EditText) findViewById(R.id.et11);
        time12 = (Button) findViewById(R.id.b11);
        date12 = (Button) findViewById(R.id.b13);
        timeText = (TextView) findViewById(R.id.tv17);
        dayText = (TextView) findViewById(R.id.tv18dd);
        monthText = (TextView) findViewById(R.id.tv18mm);
        yearText = (TextView) findViewById(R.id.tv18yyyy);
        dosageEdit = (EditText) findViewById(R.id.et12);
        quantityEdit = (EditText) findViewById(R.id.et13);
        descriptionEdit = (EditText) findViewById(R.id.description);

        time12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.setTitle("Slot 7: Set Alarm Time");
                mTimePicker.show();
            }
        });

    }

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            done.setEnabled(true);
            medName.setEnabled(true);
            time12.setEnabled(true);
            date12.setEnabled(true);
            dosageEdit.setEnabled(true);
            quantityEdit.setEnabled(true);
            descriptionEdit.setEnabled(true);

        }
        else{
            done.setEnabled(false);
            medName.setEnabled(false);
            time12.setEnabled(false);
            date12.setEnabled(false);
            dosageEdit.setEnabled(false);
            quantityEdit.setEnabled(false);
            descriptionEdit.setEnabled(false);
        }
    }

    public void spinnerSet(){
        intervalSpinner = (Spinner) findViewById(R.id.intervalSpin);
        intervalSpinner.setOnItemSelectedListener(slot7.this);
        List<String> intervalCategories = new ArrayList<String>();
        intervalCategories.add("Every day");
        intervalCategories.add("Every 12 hours");
        intervalCategories.add("Every 8 hours");
        intervalCategories.add("Every 6 hours");
        intervalCategories.add("Every 4 hours");
        intervalCategories.add("Every 2 hours");
        intervalCategories.add("Every hour");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(slot7.this, android.R.layout.simple_spinner_item,intervalCategories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(dataAdapter);
    }


    public void doneBttn_clicked(){

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medName1 = medName.getText().toString();
                String dosageEdit1 = dosageEdit.getText().toString();
                String quantityEdit1 = quantityEdit.getText().toString();
                String min = minText.getText().toString();
                String hour = timeText.getText().toString();
                String ampm = ampmText.getText().toString();
                //String timeText1 = timeText.getText().toString();
                String dayText1 = dayText.getText().toString();
                String monthText1 = monthText.getText().toString();
                String yearText1 = yearText.getText().toString();
                String user2 = userCurrent;
                String description = descriptionEdit.getText().toString();

                if(medName.getText().toString().isEmpty() || dosageEdit.getText().toString().isEmpty() ||
                        quantityEdit.getText().toString().isEmpty() || timeText.getText().toString().isEmpty() ||
                        dayText.getText().toString().isEmpty() || monthText.getText().toString().isEmpty() || yearText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter all information to continue.",Toast.LENGTH_SHORT).show();
                }
                else {

                    new addMedication_slot2().execute(medName1, dosageEdit1, quantityEdit1, hour, min, ampm, dayText1, monthText1, yearText1, user2,description,intervalItem);
                }
            }
        });

    }

    public void internetCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            Toast.makeText(getApplicationContext(),"Connected to internet. Please enter all information.",Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getApplicationContext(),"Please connect to internet  to register.",Toast.LENGTH_SHORT).show();

        }
    }


    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();
        mTimePicker = new TimePickerDialog(slot7.this,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);

    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            String hour_string = String.valueOf(hourOfDay);
            String min_string = String.valueOf(minute);
            String am_pm = "";
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }
            if (calNow.get(AM_PM) == AM) {
                am_pm = "am";
            } else if (calNow.get(AM_PM) == PM) {
                am_pm = "pm";
            }
            if (hourOfDay == 0) {
                hour_string = String.valueOf(hourOfDay + 12);
            }
            if (hourOfDay > 12) {
                hour_string = String.valueOf(hourOfDay - 12);

            }
            if (minute < 10) {
                min_string = "0" + String.valueOf(minute);
            }

            hourCInt= Integer.parseInt(hour_string);
            minCInt = Integer.parseInt(min_string);
            ampmC = am_pm;

            timeText.setText(hour_string);
            minText.setText(min_string);
            ampmText.setText(am_pm);
        }
    };

    public void init_date() {


        date12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar dateTime = Calendar.getInstance();
                int mYear = dateTime.get(Calendar.YEAR);
                int mMonth = dateTime.get(Calendar.MONTH);
                int mDay = dateTime.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(slot7.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth = selectedMonth + 1;
                        dayText.setText(Integer.toString(selectedDay));
                        monthText.setText(Integer.toString(selectedMonth));
                        yearText.setText(Integer.toString(selectedYear));

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date:");
                mDatePicker.show();

            }
        });
    }
    private void setAlarm(Calendar targetCal){
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("slot","1");
        intent.putExtra("message",medName.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), 1*60*60*1000,
                pendingIntent);

    }

    public void addDosage(View view){

        minDosage = minDosage+1;
        displayDosage(minDosage);

    }
    public void minusDosage(View view){


        if(minDosage<=0){
            Toast.makeText(getApplicationContext(),"Please enter valid number.",Toast.LENGTH_SHORT).show();
        }
        else{
            minDosage= minDosage-1;
            displayDosage(minDosage);
        }
    }
    public void addQuantity(View view){
        minQuantity = minQuantity+1;
        if(minQuantity>=12){
            Toast.makeText(getApplicationContext(),"Medicine container can only hold 12 medicine at max.",Toast.LENGTH_SHORT).show();
        }
        else{
            displayQuantity(minQuantity);
        }
    }
    public void minusQuantity(View view){
        if(minQuantity<=0){
            Toast.makeText(getApplicationContext(),"Please enter valid number.",Toast.LENGTH_SHORT).show();
        }
        else{
            minQuantity= minQuantity-1;
            displayQuantity(minQuantity);
        }
    }
    private void displayQuantity(int number){
        EditText quantityText = (EditText) findViewById(R.id.et13);
        quantityText.setText(""+number);
    }

    private void displayDosage(int number){
        EditText dosageText= (EditText) findViewById(R.id.et12);
        dosageText.setText(""+number);
    }
    public void back(View view){
        startActivity(new Intent(slot7.this, chooseSlot.class));

    }
    public void cancel(View view){
        startActivity(new Intent(slot7.this, user_activity.class));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.DKGRAY);
        intervalItem = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),"Item: "+intervalItem,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class addMedication_slot2 extends AsyncTask<String, Void, String> {

        String add_info_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_info_url = "http://dmtthesiscpepmo.comli.com/add_medication.php";


        }
        @Override
        protected String doInBackground(String... args) {
//     backgroundTask.execute(first1,last1,gender,day1,address1,emailaddress1,user,password,role);


            String medName2, dosageEdit2, quantityEdit2, hourText2,minText2,ampmText2, dayText2,monthText2, yearText2,user, desc, interval;
            medName2= (String) args[0];
            dosageEdit2= (String)args[1];
            quantityEdit2= (String) args[2];
            hourText2 = (String) args[3];
            minText2 =(String) args[4];
            ampmText2 = (String) args[5];
            dayText2 = (String) args[6];
            monthText2 = (String) args[7];
            yearText2 = (String) args[8];
            user = (String) args[9];
            desc = (String) args[10];
            interval = (String) args[11];


            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("medicine_name","UTF-8")+"="+URLEncoder.encode(medName2,"UTF-8")+"&"+
                        URLEncoder.encode("dosage","UTF-8")+"="+URLEncoder.encode(dosageEdit2,"UTF-8")+"&"+
                        URLEncoder.encode("quantity","UTF-8")+"="+URLEncoder.encode(quantityEdit2,"UTF-8")+"&"+
                        URLEncoder.encode("hour_start","UTF-8")+"="+URLEncoder.encode(hourText2,"UTF-8")+"&"+
                        URLEncoder.encode("min_start","UTF-8")+"="+URLEncoder.encode(minText2,"UTF-8")+"&"+
                        URLEncoder.encode("am_pm","UTF-8")+"="+URLEncoder.encode(ampmText2,"UTF-8")+"&"+
                        URLEncoder.encode("slot_number","UTF-8")+"="+URLEncoder.encode("7","UTF-8")+"&"+
                        URLEncoder.encode("day_start","UTF-8")+"="+URLEncoder.encode(dayText2,"UTF-8")+"&"+
                        URLEncoder.encode("month_start","UTF-8")+"="+URLEncoder.encode(monthText2,"UTF-8")+"&"+
                        URLEncoder.encode("year_start","UTF-8")+"="+URLEncoder.encode(yearText2,"UTF-8")+"&"+
                        URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"+
                        URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(desc,"UTF-8")+"&"+
                        URLEncoder.encode("interval_med","UTF-8")+"="+URLEncoder.encode(interval,"UTF-8");
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
            startActivity(new Intent(slot7.this, chooseSlot.class));
            Toast.makeText(getApplicationContext(),"Medication successfully registered.",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot1);
        findView();
        slot.setText("7");
        openTimePickerDialog(false);
        init_date();
        internetCheck();
        doneBttn_clicked();
        spinnerSet();
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        userCurrent = prefs.getString("username","");
        timeText = (TextView) findViewById(R.id.tv17);
        minText = (TextView) findViewById(R.id.mm);
        ampmText = (TextView) findViewById(R.id.a);


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