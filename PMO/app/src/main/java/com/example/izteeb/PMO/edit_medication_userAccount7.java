package com.example.izteeb.PMO;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Calendar.AM;
import static java.util.Calendar.AM_PM;
import static java.util.Calendar.PM;


public class edit_medication_userAccount7 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String userCurrent, slotSP, amOrpm;
    int minTime, hourTime, minInt, hourInt, slotSP_int = 7;
    int minQuantity=0;
    int minDosage=0;

    // pass value
    String ampm2 ,med2,intervalItem, desc2,interval2;
    int min2, hour2, quantity2, dosage2, day2, month2,year2;


    Button done1,time12, date12;
    Spinner intervalSpinner;
    EditText medName, dosageEdit, quantityEdit, descriptionEdit;
    TextView dosage,timeText,minText,ampmText, dayText, monthText, yearText, slot, user1;
    TimePickerDialog mTimePicker;
    ProgressDialog pDialog;

    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";
    public String TAG_USER_VALUE = "user";
    public String TAG_MIN_VALUE = "min_start";
    public String TAG_HOUR_VALUE = "hour_start";
    public String TAG_AM_PM = "am_pm";
    public String TAG_QUANTITY= "quantity";
    public String TAG_MEDICINE = "medicine_name";
    public String TAG_DOSAGE = "dosage";
    public String TAG_SLOT = "slot_number";
    public String TAG_DAY = "day_start";
    public String TAG_MONTH = "month_start";
    public String TAG_YEAR = "year_start";
    public String TAG_DESC = "description";
    public String TAG_INTERVAL = "interval_med";


    Handler mHandler = new Handler();
    Boolean isRunning = true;

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            done1.setEnabled(true);
            medName.setEnabled(true);
            time12.setEnabled(true);
            date12.setEnabled(true);
            dosageEdit.setEnabled(true);
            quantityEdit.setEnabled(true);
            descriptionEdit.setEnabled(true);

        }
        else{
            done1.setEnabled(false);
            medName.setEnabled(false);
            time12.setEnabled(false);
            date12.setEnabled(false);
            dosageEdit.setEnabled(false);
            quantityEdit.setEnabled(false);
            descriptionEdit.setEnabled(false);
        }
    }

    public void findViewInit(){
        slot = (TextView) findViewById(R.id.tv19);
        medName= (EditText) findViewById(R.id.et11);
        time12 = (Button) findViewById(R.id.b11);
        timeText = (TextView) findViewById(R.id.tv17);
        dayText = (TextView) findViewById(R.id.tv18dd);
        monthText = (TextView) findViewById(R.id.tv18mm);
        yearText = (TextView) findViewById(R.id.tv18yyyy);
        dosageEdit = (EditText) findViewById(R.id.et12);
        quantityEdit = (EditText) findViewById(R.id.et13);
        descriptionEdit = (EditText) findViewById(R.id.description);

    }

    public void timeBttnClicked(){

        time12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.setTitle("Slot "+slotSP_int+": Set Alarm Time");
                mTimePicker.show();
            }
        });

    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();
        mTimePicker = new TimePickerDialog(edit_medication_userAccount7.this,
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

            hourInt= Integer.parseInt(hour_string);
            minInt = Integer.parseInt(min_string);
            amOrpm = am_pm;

            timeText.setText(hour_string);
            minText.setText(min_string);
            ampmText.setText(am_pm);

        }
    };

    public void spinnerSet(){
        intervalSpinner = (Spinner) findViewById(R.id.intervalSpin);
        intervalSpinner.setOnItemSelectedListener(edit_medication_userAccount7.this);
        List<String> intervalCategories = new ArrayList<String>();
        intervalCategories.add("Every day");
        intervalCategories.add("Every 12 hours");
        intervalCategories.add("Every 8 hours");
        intervalCategories.add("Every 6 hours");
        intervalCategories.add("Every 4 hours");
        intervalCategories.add("Every 2 hours");
        intervalCategories.add("Every hour");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(edit_medication_userAccount7.this, android.R.layout.simple_spinner_item,intervalCategories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(dataAdapter);
    }

    public void initDatePicker() {
        date12 = (Button) findViewById(R.id.b13);
        date12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker;
                Calendar dateTime = Calendar.getInstance();
                int mYear = dateTime.get(Calendar.YEAR);
                int mMonth = dateTime.get(Calendar.MONTH);
                int mDay = dateTime.get(Calendar.DAY_OF_MONTH);

                mDatePicker = new DatePickerDialog(edit_medication_userAccount7.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth = selectedMonth + 1;

                        dayText.setText(Integer.toString(selectedDay));
                        monthText.setText(Integer.toString(selectedMonth));
                        yearText.setText(Integer.toString(selectedYear));
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date:");
                mDatePicker.show();
            }
        });


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
        Toast.makeText(getApplicationContext(),"Proceed to medicine organizer to edit quantity.",Toast.LENGTH_SHORT).show();
    }
    public void minusQuantity(View view){
        Toast.makeText(getApplicationContext(),"Proceed to medicine organizer to edit quantity.",Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(edit_medication_userAccount7.this, chooseSlotForEdit.class));

    }
    public void cancel(View view){
        startActivity(new Intent(edit_medication_userAccount7.this, user_activity.class));

    }

    public void saveButtonClicked(){

        done1 = (Button) findViewById(R.id.b12);
        done1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = new GregorianCalendar();
                int dayNow = cal.get(Calendar.DAY_OF_MONTH);
                int monthNow = cal.get(Calendar.MONTH);
                int yearNow = cal.get(Calendar.YEAR);

                String username = userCurrent;
                String slot = Integer.toString(slotSP_int);
                String med = medName.getText().toString();
                String dosage = dosageEdit.getText().toString();
                String quantity = quantityEdit.getText().toString();
                String min = minText.getText().toString();
                String hour = timeText.getText().toString();
                String ampm = ampmText.getText().toString();
                String day = dayText.getText().toString();
                String month = monthText.getText().toString();
                String year = yearText.getText().toString();
                String description = descriptionEdit.getText().toString();

                if(medName.getText().toString().isEmpty() || dosageEdit.getText().toString().isEmpty()
                        || quantityEdit.getText().toString().isEmpty() || timeText.getText().toString().isEmpty()
                        || dayText.getText().toString().isEmpty() || monthText.getText().toString().isEmpty() || yearText.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter all data.",Toast.LENGTH_SHORT).show();
                }
                else{

                    new saveMedication().execute(username,slot,med,dosage,description,min,hour,ampm,day,month,year,intervalItem);

                }



            }
        });

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


    public class loadMedicationInformation extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(edit_medication_userAccount7.this);
            pDialog.setMessage("Loading user's information...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            HttpHandler sh = new HttpHandler();
            String jsonString = sh.makeServiceCall(url_user_medication);
            if (jsonString != null) {
                try {
                    JSONObject jobj = new JSONObject(jsonString);
                    JSONArray jarray = jobj.getJSONArray("medication");

                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject c = jarray.getJSONObject(i);
                        String user = c.getString(TAG_USER_VALUE);
                        int slot = c.getInt(TAG_SLOT);
                        int min = c.getInt(TAG_MIN_VALUE);
                        int hour = c.getInt(TAG_HOUR_VALUE);
                        String am_pm = c.getString(TAG_AM_PM);
                        String med = c.getString(TAG_MEDICINE);
                        int dosage = c.getInt(TAG_DOSAGE);
                        int quantity = c.getInt(TAG_QUANTITY);
                        int day = c.getInt(TAG_DAY);
                        int month = c.getInt(TAG_MONTH);
                        int year = c.getInt(TAG_YEAR);
                        String description = c.getString(TAG_DESC);
                        String interval = c.getString(TAG_INTERVAL);

                        if (user.equals(userCurrent) && slot == slotSP_int) {
                            min2 = min;
                            hour2 = hour;
                            ampm2 = am_pm;
                            quantity2 = quantity;
                            dosage2 = dosage;
                            med2 = med;
                            day2 = day;
                            month2 = month;
                            year2 = year;
                            desc2 = description;
                            interval2 = interval;


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

                minInt = min2;
                hourInt = hour2;
                amOrpm = ampm2;

                medName.setText(med2);
                timeText.setText(Integer.toString(hour2));
                minText.setText(Integer.toString(min2));
                ampmText.setText(ampm2);
                dayText.setText(Integer.toString(day2));
                monthText.setText(Integer.toString(month2));
                yearText.setText(Integer.toString(year2));
                dosageEdit.setText(Integer.toString(dosage2));
                quantityEdit.setText(Integer.toString(quantity2));
                descriptionEdit.setText(desc2);


                if(interval2.equals("Every day")){
                    intervalSpinner.setSelection(0);
                    intervalItem = interval2;
                }
                else if(interval2.equals("Every 12 hours")){
                    intervalSpinner.setSelection(1);
                    intervalItem = interval2;
                }
                else if(interval2.equals("Every 8 hours")){
                    intervalSpinner.setSelection(2);
                    intervalItem = interval2;
                }
                else if(interval2.equals("Every 6 hours")){
                    intervalItem = interval2;
                    intervalSpinner.setSelection(3);
                }
                else if(interval2.equals("Every 4 hours")){
                    intervalItem = interval2;
                    intervalSpinner.setSelection(4);
                }
                else if(interval2.equals("Every 2 hours")){
                    intervalItem = interval2;
                    intervalSpinner.setSelection(5);
                }
                else if(interval2.equals("Every hour")){
                    intervalItem = interval2;
                    intervalSpinner.setSelection(6);
                }

            }
        }
    }

    class saveMedication extends AsyncTask<String, Void, String>{

        String update_medication_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            update_medication_url = "http://dmtthesiscpepmo.comli.com/update_medication_for_user.php";
            pDialog = new ProgressDialog(edit_medication_userAccount7.this);
            pDialog.setMessage("Updating slot"+ slotSP_int+" information...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            String username2, slot2,med2,dosage2,quantity2,min2,hour2,ampm2,day2,month2,year2, interval2, desc2;

            username2 = (String) args[0];
            slot2 =  (String) args[1];
            med2 = (String) args[2];
            dosage2 = (String) args[3];
            desc2 = (String) args[4];
            min2 = (String) args[5];
            hour2 = (String) args[6];
            ampm2 = (String) args[7];
            day2 = (String) args[8];
            month2 = (String) args[9];
            year2 = (String) args[10];
            interval2 = (String) args[11];


            try {
                URL url = new URL(update_medication_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string =
                        URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(username2,"UTF-8")+"&"+
                                URLEncoder.encode("slot_number","UTF-8")+"="+URLEncoder.encode(slot2,"UTF-8")+"&"+
                                URLEncoder.encode("medicine_name","UTF-8")+"="+URLEncoder.encode(med2,"UTF-8")+"&"+
                                URLEncoder.encode("dosage","UTF-8")+"="+URLEncoder.encode(dosage2,"UTF-8")+"&"+
                                URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(desc2,"UTF-8")+"&"+
                                URLEncoder.encode("min_start","UTF-8")+"="+URLEncoder.encode(min2,"UTF-8")+"&"+
                                URLEncoder.encode("hour_start","UTF-8")+"="+URLEncoder.encode(hour2,"UTF-8")+"&"+
                                URLEncoder.encode("am_pm","UTF-8")+"="+URLEncoder.encode(ampm2,"UTF-8")+"&"+
                                URLEncoder.encode("day_start","UTF-8")+"="+URLEncoder.encode(day2,"UTF-8")+"&"+
                                URLEncoder.encode("month_start","UTF-8")+"="+URLEncoder.encode(month2,"UTF-8")+"&"+
                                URLEncoder.encode("year_start","UTF-8")+"="+URLEncoder.encode(year2,"UTF-8")+"&"+
                                URLEncoder.encode("interval_med","UTF-8")+"="+URLEncoder.encode(interval2,"UTF-8");

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
            if(pDialog.isShowing()) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Medication updated.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(edit_medication_userAccount7.this, user_activity.class));
            }
        }
    }



    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot1);
        saveButtonClicked();
        initDatePicker();
        findViewInit();
        timeBttnClicked();
        spinnerSet();
        openTimePickerDialog(false);
        slot.setText(Integer.toString(slotSP_int));
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        userCurrent = prefs.getString("username","");
        new loadMedicationInformation().execute();

        timeText = (TextView) findViewById(R.id.tv17);
        minText = (TextView) findViewById(R.id.mm);
        ampmText = (TextView) findViewById(R.id.a);
        quantityEdit.setEnabled(false);

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
