package com.example.izteeb.PMO;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.izteeb.PMO.slot1;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Izteeb on 3/1/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Medication ongoing. Please take now.", Toast.LENGTH_LONG).show();

        //Define Notification Manager
        String slotExtra = intent.getStringExtra("slot");
        String medExtra = intent.getStringExtra("medicine");
        Intent i = new Intent(context.getApplicationContext(), alert_dialog_alarm.class);
        i.putExtra("slot",slotExtra);
        i.putExtra("medicine",medExtra);
        i.getStringExtra("message");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
