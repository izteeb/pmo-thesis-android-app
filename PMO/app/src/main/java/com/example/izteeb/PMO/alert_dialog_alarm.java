package com.example.izteeb.PMO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Izteeb on 4/20/2017.
 */

public class alert_dialog_alarm extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);

        Intent intent = getIntent();
        String medMessage = intent.getStringExtra("medicine");
        String slotNumber = intent.getStringExtra("slot");

        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.super_mario_bros);
        mediaPlayer.start();

        AlertDialog.Builder adb = new AlertDialog.Builder(alert_dialog_alarm.this);
        adb.setIcon(R.mipmap.pmo_icon1);
        adb.setTitle(medMessage+" Slot "+slotNumber);

        adb.setMessage("Please take medicine now.");
        adb.setCancelable(false);
        String yesButton = "OKAY";
        String noButton = "DISMISS";
        adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               mediaPlayer.stop();
                finish();
                dialog.dismiss();
            }
        });
        adb.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               mediaPlayer.stop();
                finish();
                dialog.dismiss();
            }
        });
        adb.show();
    }
}
