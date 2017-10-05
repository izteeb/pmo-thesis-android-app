package com.example.izteeb.PMO;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static java.util.Calendar.AM;
import static java.util.Calendar.AM_PM;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.PM;
import static java.util.Calendar.getInstance;

public class add_medication extends AppCompatActivity {

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView alarm_info;
    Context context;
    PendingIntent pending_intent;

    public void setAlarm() {
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_info = (TextView) findViewById(R.id.alarm_info);
        final Calendar calendar = getInstance();
        final Intent myIntent = new Intent(add_medication.this, AlarmReceiver.class);
        final Button set_alarm = (Button) findViewById(R.id.set_alarm);
        final Button stop_alarm = (Button) findViewById(R.id.stop_alarm);


        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
                calendar.set(MINUTE, alarm_timepicker.getCurrentMinute());

                int hour = alarm_timepicker.getCurrentHour();
                int min = alarm_timepicker.getCurrentMinute();
                String hour_string = String.valueOf(hour);
                String min_string = String.valueOf(min);
                String am_pm = "";
                if (calendar.get(AM_PM) == AM) {
                    am_pm = "AM";
                } else if (calendar.get(AM_PM) == PM) {
                    am_pm = "PM";
                }

                if (hour == 0) {
                    hour_string = String.valueOf(hour + 12);
                }
                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);

                }
                if (min < 10) {
                    min_string = "0" + String.valueOf(min);
                }

                myIntent.putExtra("Extra","Alarm on");
                pending_intent = PendingIntent.getBroadcast(add_medication.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
                startActivity(new Intent(add_medication.this, slot1.class));
                String time = hour_string+":"+min_string+" "+am_pm;
                String time1 = time.toString();
                Intent intent = new Intent(add_medication.this, slot1.class);
                intent.putExtra("time", time1);
                startActivity(intent);
            }

        });





        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alarm_manager.cancel(pending_intent);
                myIntent.putExtra("Extra", "Alarm off");
                sendBroadcast(myIntent);

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
            setAlarm();

        Intent intent = getIntent();
        String str = intent.getStringExtra("medicine_name");
        alarm_info.setText(str);

    }



}
