package com.example.izteeb.PMO;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Izteeb on 3/1/2017.
 */

public class RingtonePlayingService extends Service{

    MediaPlayer media_song;
    int startId;
    boolean isRunning;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
            Log.i("LocalService","Receive start id: "+startId+":"+intent);


        String state= intent.getExtras().getString("Extra");
        Log.e("Ringtone extra is",state);



        assert state!=null;
        switch (state){
            case "Alarm on":
                startId =1;
                break;
            case "Alarm off":
                startId= 0;
                Log.e("Start ID is",state);
                break;
            default:
                startId=0;
                break;
        }

        if(this.isRunning && startId==1){
            Log.e("There is no music,","and you want to start.");
            this.isRunning=true;
            this.startId=1;
        }
        else if(this.isRunning && startId==0){
            Log.e("There is no music,","and you want to end.");
            media_song.stop();
            media_song.reset();
            this.isRunning=false;
            this.startId=0;
        }
        else if(!this.isRunning && startId==0){
            Log.e("There is no music,","and you want to end.");
            this.isRunning=false;
            this.startId=0;


        }
        else if(!this.isRunning && startId==1){
            Log.e("There is no music,","and you want to start");

            media_song = MediaPlayer.create(this,R.raw.minion);
            media_song.start();
            this.isRunning= true;
            this.startId=0;

            NotificationManager notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent_add_medication = new Intent(this.getApplicationContext(), add_medication.class);
            PendingIntent pendingIntent_addMedication = PendingIntent.getActivity(this, 0, intent_add_medication,0);

            Notification notification_popUp = new Notification.Builder(this)
                    .setContentTitle("Portable Medicine Organizer")
                    .setContentText("Medication ongoing.")
                    .setContentIntent(pendingIntent_addMedication)
                    .addAction(android.R.drawable.ic_dialog_alert,"OFF ALARM",pendingIntent_addMedication)
                    .addAction(android.R.drawable.ic_dialog_alert,"SNOOZE ALARM", pendingIntent_addMedication)
                    .setSmallIcon(R.mipmap.pmo_icon)
                    .setAutoCancel(true)
                    .build();
            notify_manager.notify(0,notification_popUp);
        }
        else {
            Log.e("else ","somehow you reached this");
        }

           return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        Toast.makeText(this,"on Destroy called",Toast.LENGTH_SHORT).show();
        super.onDestroy();
        this.isRunning=false;
    }
}
