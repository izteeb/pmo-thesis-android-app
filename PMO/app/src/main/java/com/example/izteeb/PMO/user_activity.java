package com.example.izteeb.PMO;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextClock;
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
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class user_activity extends AppCompatActivity {

    public String url_list_user = "http://dmtthesiscpepmo.comli.com/json_getuser.php";
    private static String url_user = "http://dmtthesiscpepmo.comli.com/selectname.php";
    public String url_user_medication = "http://dmtthesiscpepmo.comli.com/json_medic.php";

    public String TAG_MED_OBJECT = "";
    public String TAG_USER_VALUE = "user";
    public String TAG_MIN_VALUE = "min_start";
    public String TAG_HOUR_VALUE = "hour_start";
    public String TAG_AM_PM = "am_pm";
    public String TAG_QUANTITY= "quantity";
    public String TAG_MEDICINE = "medicine_name";
    public String TAG_DOSAGE = "dosage";
    public String TAG_DAY= "day_start";
    public String TAG_MONTH= "month_start";
    public String TAG_YEAR= "year_start";
    public String TAG_TIME  = "time_start";
    public String TAG_SLOT = "slot_number";
    public String TAG_DESCRIPTION = "description";
    public String TAG_INTERVAL = "interval_med";

    public String user1 = "",am_pm1="",med1="",dosage1="",description1="";
    public String time1 = "";
    public int min1,hour1,quantity1,slot1,day1,month1,year1;
    Boolean checkMed;

    ArrayList<String> medList = new ArrayList();
    ArrayList<String> userList = new ArrayList();
    ArrayList<Integer> minList = new ArrayList();
    ArrayList<Integer> hourList = new ArrayList();
    ArrayList<String> ampmList = new ArrayList();
    ArrayList<Integer> dayList = new ArrayList<>();
    ArrayList<Integer> monthList = new ArrayList<>();
    ArrayList<Integer> yearList = new ArrayList<>();
    ArrayList<Integer> slotList = new ArrayList<>();
    ArrayList<Integer> slotList1 = new ArrayList<>();
    ArrayList<String> intervalList = new ArrayList<>();

    ArrayList<HashMap<String, String>> userList1;

    private HashMap<String, String> contentsMap = new HashMap<String, String>();
    private ProgressDialog progressDialog1;
    private String username, role1, first,last;
    ImageView add_medication, view_medication,edit_med,edit_user;
    public Button add_newAdmin, viewMed;
    private ListView listView;
    TextView prompt_textview;
    ProgressDialog pDialog;

    TextView userTextView, time_list;
    private static String url_list_med = "http://dmtthesiscpepmo.comli.com/json_getmedication.php";
    public static List<String> LIST = new ArrayList<String>();
    public static List<String> LIST1 = new ArrayList<String>();

    long dayInterval = 1000*60*60*24; //ms*1 min*1hour*24 hours
    long halfdayInterval = 1000*60*60*12;
    long eightHoursInterval = 1000*60*60*8;
    long sixHoursInterval = 1000*60*60*6;
    long fourHoursInterval = 1000*60*60*4;
    long twoHoursInterval = 1000*60*60*2;
    long oneHourInterval = 1000*60*60;

    Handler mHandler = new Handler();
    Boolean isRunning = true;




    public void displayDate(){

        Calendar cal = new GregorianCalendar();
        int day_now = cal.get(Calendar.DAY_OF_MONTH);
        int month_now = cal.get(Calendar.MONTH);
        int year_now = cal.get(Calendar.YEAR);
        int day_of_week_now = cal.get(Calendar.DAY_OF_WEEK);
        String month_now_string="", day_of_week_now_string="";

        TextView day_week_text = (TextView) findViewById(R.id.day_week_txt);
        TextView year_text = (TextView) findViewById(R.id.year_txt);
        TextView month_text = (TextView) findViewById(R.id.month_txt);
        TextView day_text = (TextView) findViewById(R.id.day_txt);

        if(month_now == 0){
            month_now_string = "January";
        }
        else if(month_now == 1) {
            month_now_string = "February";
        }
        else if(month_now == 2){
            month_now_string = "March";
        }
        else if(month_now == 3){
            month_now_string = "April";
        }
        else if (month_now == 4){
            month_now_string = "May";
        }
        else if (month_now == 5){
            month_now_string = "June";
        }
        else if (month_now == 6){
            month_now_string = "July";
        }
        else if (month_now == 7){
            month_now_string = "August";
        }
        else if (month_now == 8){
            month_now_string = "September";
        }
        else if (month_now == 9){
            month_now_string = "October";
        }
        else if (month_now == 10){
            month_now_string = "November";
        }
        else if (month_now == 11){
            month_now_string = "December";
        }

        if(day_of_week_now == 1){
            day_of_week_now_string = "Sunday";
        }
        if(day_of_week_now == 2){
            day_of_week_now_string = "Monday";
        }
        if(day_of_week_now == 3){
            day_of_week_now_string = "Tuesday";
        }
        if(day_of_week_now == 4){
            day_of_week_now_string = "Wednesday";
        }
        if(day_of_week_now == 5){
            day_of_week_now_string = "Thursday";
        }
        if(day_of_week_now == 6){
            day_of_week_now_string = "Friday";
        }
        if(day_of_week_now == 7){
            day_of_week_now_string = "Saturday";
        }

        day_text.setText(Integer.toString(day_now));
        month_text.setText(month_now_string);
        year_text.setText(Integer.toString(year_now));
        day_week_text.setText(day_of_week_now_string);


        new addMedList().execute();
    }

    public void internetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            add_medication.setEnabled(true);
            view_medication.setEnabled(true);
            edit_med.setEnabled(true);
            edit_user.setEnabled(true);

        } else {
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue.",Toast.LENGTH_SHORT).show();
            add_medication.setEnabled(false);
            view_medication.setEnabled(false);
            edit_med.setEnabled(false);
            edit_user.setEnabled(false);
        }
    }

    public void init_addMedication(){
        add_medication = (ImageView)findViewById(R.id.add_medication);
        add_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(user_activity.this, chooseSlot.class));

            }
        });
    }

    public void init_viewMedication(){
        view_medication = (ImageView) findViewById(R.id.viewMedicine);
        view_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_activity.this, user_medList.class));
            }
        });
    }

    public void editMedicationButtonPressed(){
        edit_med = (ImageView) findViewById(R.id.edit_med12);
        edit_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_activity.this,edit_or_delete.class ));
            }
        });

    }


    public void editUserButtonPressed(){
        edit_user = (ImageView) findViewById(R.id.edit_user);
        edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_activity.this, edit_user_for_user_account.class));
            }
        });
    }



    @Override
    public void onBackPressed() {

    }

    public void init(){
        Intent intent = getIntent();
        String str = intent.getStringExtra("medicine_name");
        String str1 = intent.getStringExtra("time");
    }

    public void back4(View view){
        final AlertDialog.Builder adb = new AlertDialog.Builder(user_activity.this);
        adb.setTitle("Signing out");
        adb.setMessage("Are you sure you want to sign out?");
        adb.setCancelable(false);
        String yesButton= "YES";
        String noButton = "NO";
        adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(user_activity.this, login_page.class));
            }
        });
        adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
            }
        });
        adb.show();
    }



    public void findView1(){
        userTextView = (TextView) findViewById(R.id.uaFN);
        prompt_textview = (TextView) findViewById(R.id.prompt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity);
        editMedicationButtonPressed();
        init_addMedication();
        init_viewMedication();
        init();
        findView1();
        displayDate();
        editUserButtonPressed();
        listView = (ListView) findViewById(R.id.listView1);
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        username = prefs.getString("username","");
        role1 = prefs.getString("role","");
        userTextView.setText(username);
        new loadSlotForAlarm().execute();
        userList1 = new ArrayList<HashMap<String, String>>();

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



    public class loadSlotForAlarm extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                        int day = c.getInt(TAG_DAY);
                        int month = c.getInt(TAG_MONTH);
                        int year = c.getInt(TAG_YEAR);
                        String interval = c.getString(TAG_INTERVAL);

                        if (user.equals(username)) {
                            slotList1.add(slot);
                            minList.add(min);
                            hourList.add(hour);
                            ampmList.add(am_pm);
                            dayList.add(day);
                            monthList.add(month);
                            yearList.add(year);
                            medList.add(med);
                            intervalList.add(interval);

                        }

                    }

                }catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Internet connection is closed. Please connect to internet then reload to continue. ",
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

            //  for(int i=0; i<slotList1.size(); i++){

            int position = -1;
            position = slotList1.indexOf(1);
            if (position == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "1");
                intent.putExtra("medicine", medList.get(position));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position));
                long _alarm = 0;

                if (ampmList.get(position).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position));
                } else if (ampmList.get(position).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }


            int position2 = -1;
            position2 = slotList1.indexOf(2);
            if (position2 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "2");
                intent.putExtra("medicine", medList.get(position2));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position2));
                long _alarm = 0;

                if (ampmList.get(position2).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position2));
                } else if (ampmList.get(position2).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position2) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position2).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position2).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position2).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
             }


            int position3 = -1;
            position3 = slotList1.indexOf(3);
            if (position3 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "3");
                intent.putExtra("medicine", medList.get(position3));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position3));
                long _alarm = 0;

                if (ampmList.get(position3).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position3));
                } else if (ampmList.get(position3).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position3) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position3).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position3).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position3).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }


            int position4 = -1;
            position4 = slotList1.indexOf(4);
            if (position4 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "4");
                intent.putExtra("medicine", medList.get(position4));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position4));
                long _alarm = 0;

                if (ampmList.get(position4).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position4));
                } else if (ampmList.get(position4).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position4) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position4).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position4).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position4).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }


            int position5 = -1;
            position5 = slotList1.indexOf(5);
            if (position5 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "5");
                intent.putExtra("medicine", medList.get(position5));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position5));
                long _alarm = 0;

                if (ampmList.get(position5).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position5));
                } else if (ampmList.get(position5).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position5) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position5).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position5).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position5).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }


            int position6 = -1;
            position6 = slotList1.indexOf(6);
            if (position6 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 6, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "6");
                intent.putExtra("medicine", medList.get(position6));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position6));
                long _alarm = 0;

                if (ampmList.get(position6).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position6));
                } else if (ampmList.get(position6).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position6) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 6, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position6).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position6).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position6).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }


            int position7 = -1;
            position7 = slotList1.indexOf(7);
            if (position7 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 7, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "7");
                intent.putExtra("medicine", medList.get(position7));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position7));
                long _alarm = 0;

                if (ampmList.get(position7).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position7));
                } else if (ampmList.get(position7).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position7) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 7, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position7).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position7).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position7).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }


            int position8 = -1;
            position8 = slotList1.indexOf(8);
            if (position8 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 8, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "8");
                intent.putExtra("medicine", medList.get(position8));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position8));
                long _alarm = 0;

                if (ampmList.get(position8).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position8));
                } else if (ampmList.get(position8).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position8) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 8, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position8).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position8).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position8).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }


            int position9 = -1;
            position9 = slotList1.indexOf(9);
            if (position9 == -1) {
                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 9, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            } else {

                Intent intent = new Intent(user_activity.this, AlarmReceiver.class);
                intent.putExtra("slot", "9");
                intent.putExtra("medicine", medList.get(position9));

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.MINUTE, minList.get(position9));
                long _alarm = 0;

                if (ampmList.get(position9).equals("am")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position9));
                } else if (ampmList.get(position9).equals("pm")) {
                    calSet.set(Calendar.HOUR_OF_DAY, hourList.get(position9) + 12);
                }

                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getBaseContext(), 9, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                if (calSet.getTimeInMillis() <= calNow.getTimeInMillis()) {

                    if (intervalList.get(position9).equals("Every day")) {
                        _alarm = calSet.getTimeInMillis() + (AlarmManager.INTERVAL_DAY);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 12 hours")) {
                        _alarm = calSet.getTimeInMillis() + halfdayInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 8 hours")) {
                        _alarm = calSet.getTimeInMillis() + eightHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 6 hours")) {
                        _alarm = calSet.getTimeInMillis() + sixHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 4 hours")) {
                        _alarm = calSet.getTimeInMillis() + fourHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 2 hours")) {
                        _alarm = calSet.getTimeInMillis() + twoHoursInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every hour")) {
                        _alarm = calSet.getTimeInMillis() + oneHourInterval;
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                } else {
                    _alarm = calSet.getTimeInMillis();
                    if (intervalList.get(position9).equals("Every day")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, dayInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 12 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, halfdayInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 8 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, eightHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 6 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, sixHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 4 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, fourHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every 2 hours")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, twoHoursInterval, pendingIntent);
                    } else if (intervalList.get(position9).equals("Every hour")) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm, oneHourInterval, pendingIntent);
                    }

                }
            }



        }
    }


    private class addMedList extends AsyncTask <String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(user_activity.this);
            pDialog.setMessage("Loading user information. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_user_medication);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jArray = jsonObj.getJSONArray("medication");
                    int qty= 1;
                    /*
                    Calendar cal = new GregorianCalendar();
                    int hoursNow = cal.get(Calendar.HOUR);
                    int minNow = cal.get(Calendar.MINUTE);
                    int dayNow = cal.get(Calendar.DAY_OF_MONTH);
                    int monthNow = cal.get(Calendar.MONTH);
                    int yearNow = cal.get(Calendar.YEAR);
                    String minute;
                    String am_pmNow = "";
                    if(Calendar.AM_PM == Calendar.AM){
                        am_pmNow = "am";
                    }
                    else{
                        am_pmNow = "pm";
                    }
                    if (minNow < 10) {
                        minute = "0" + String.valueOf(minNow);
                        minNow = Integer.parseInt(minute);
                    }
                    */
                    for (int i = 0; i < jArray.length(); i++) {


                        JSONObject c = jArray.getJSONObject(i);
                        String user = c.getString(TAG_USER_VALUE);
                        String med = c.getString(TAG_MEDICINE);
                        int min = c.getInt(TAG_MIN_VALUE);
                        int hour = c.getInt(TAG_HOUR_VALUE);
                        String am_pm = c.getString(TAG_AM_PM);


                                    //user_activity_medication_checker checker = new user_activity_medication_checker();
                                    // checker.checkMedication(medList.get(i),hourList.get(i),minList.get(i),ampmList.get(i));

                                   if(user.equals(username)){

                                               HashMap<String, String> map = new HashMap<>();
                                               map.put("Quantity",Integer.toString(qty++));
                                               map.put(TAG_MEDICINE, med);
                                               map.put(TAG_MIN_VALUE,Integer.toString(min));
                                               map.put(TAG_HOUR_VALUE,Integer.toString(hour));
                                               map.put(TAG_AM_PM, am_pm);
                                               userList1.add(map);

                                   }

                    }
                }

                catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    e.getMessage(),
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
            super.onPostExecute(s);
            if (pDialog.isShowing()) {
                pDialog.dismiss();

                if(userList1.size()==0){
                    prompt_textview.setVisibility(View.VISIBLE);
                }else if(userList1.size()>0){
                    prompt_textview.setVisibility(View.INVISIBLE);
                }
                ListAdapter adapter = new SimpleAdapter(user_activity.this, userList1, R.layout.row_medication, new String[]{
                       "Quantity",TAG_MEDICINE,TAG_HOUR_VALUE,TAG_MIN_VALUE,TAG_AM_PM
                }, new int[]{R.id.tv70,R.id.tv72, R.id.tv74, R.id.tv76, R.id.tv77});
                listView.setAdapter(adapter);

            }
        }
    }

}
