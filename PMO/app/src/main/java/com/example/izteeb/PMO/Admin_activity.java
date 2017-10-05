package com.example.izteeb.PMO;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Admin_activity extends AppCompatActivity {

    String usernameSP;
    Button back1;
    TextView userText;
    ImageView add_admin_bttn,view_user_bttn,view_medication_bttn,search_user_bttn, delete_user_bttn,
            delete_medication_bttn, search_med_bttn, edit_user_bttn, edit_med_bttn;

    Handler mHandler = new Handler();
    Boolean isRunning = true;

    public void internetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            add_admin_bttn.setEnabled(true);
            view_user_bttn.setEnabled(true);
            view_medication_bttn.setEnabled(true);
            search_user_bttn.setEnabled(true);
            delete_user_bttn.setEnabled(true);
            delete_medication_bttn.setEnabled(true);
            search_med_bttn.setEnabled(true);
            edit_user_bttn.setEnabled(true);
            edit_med_bttn.setEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please connect to internet to continue.",Toast.LENGTH_SHORT).show();
            add_admin_bttn.setEnabled(false);
            view_user_bttn.setEnabled(false);
            view_medication_bttn.setEnabled(false);
            search_user_bttn.setEnabled(false);
            delete_user_bttn.setEnabled(false);
            delete_medication_bttn.setEnabled(false);
            search_med_bttn.setEnabled(false);
            edit_user_bttn.setEnabled(false);
            edit_med_bttn.setEnabled(false);
        }
    }

    public void backButtonPressed(){
        back1 = (Button) findViewById(R.id.bckBttn61);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(Admin_activity.this);
                adb.setTitle("Signing out");
                adb.setMessage("Are you sure you want to sign out?");
                adb.setCancelable(false);
                String yesButton= "YES";
                String noButton = "NO";
                adb.setPositiveButton(yesButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Admin_activity.this,login_page.class));
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
        });

    }

    public void addAdminButtonPressed(){
        add_admin_bttn = (ImageView) findViewById(R.id.imageView2);
        add_admin_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_activity.this, add_newAdmin.class));
            }
        });
    }

    public void viewUserButtonPressed(){
        view_user_bttn = (ImageView) findViewById(R.id.imageView12);
        view_user_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_activity.this, viewUser.class));
            }
        });
    }

    public void searchUserButtonPressed(){
        search_user_bttn = (ImageView) findViewById(R.id.imageView7);
        search_user_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_activity.this, search_user.class));
            }
        });
    }



    public void viewMedicationButtonPressed(){
        view_medication_bttn = (ImageView) findViewById(R.id.imageView9);
        view_medication_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_activity.this, MedicineListview.class));
            }
        });
    }

    public void editUserButtonPressed(){
        edit_user_bttn = (ImageView) findViewById(R.id.imageView11);
        edit_user_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_activity.this, edit_user_for_admin_account.class));
            }
        });
    }

    public void deleteUserButtonPressed(){
        delete_user_bttn = (ImageView) findViewById(R.id.imageView10);
        delete_user_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(Admin_activity.this, delete_user.class));
                startActivity(new Intent(Admin_activity.this, deleteUserAdmin.class));
            }
        });
    }

    public void editMedicationButtonPressed(){
        edit_med_bttn = (ImageView) findViewById(R.id.imageView5);
        edit_med_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Admin_activity.this, edit_medication.class));
                startActivity(new Intent(Admin_activity.this, edit_userslotMedication.class));
            }
        });
    }

    public void deleteMedicationButtonPressed(){
        delete_medication_bttn = (ImageView) findViewById(R.id.imageView4);
        delete_medication_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_activity.this, deleteMedicationAdmin.class));
            }
        });
    }

    public void searchMedicationButtonPressed(){
        search_med_bttn = (ImageView) findViewById(R.id.imageView3);
        search_med_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_activity.this, search_med.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_activity);


        userText = (TextView) findViewById(R.id.textView61);
        SharedPreferences prefs = getSharedPreferences("user_a",MODE_PRIVATE);
        usernameSP = prefs.getString("username","");
        userText.setText(usernameSP);

        backButtonPressed();
        addAdminButtonPressed();
        viewUserButtonPressed();
        viewMedicationButtonPressed();
        searchUserButtonPressed();
        deleteUserButtonPressed();
        deleteMedicationButtonPressed();
        searchMedicationButtonPressed();
        editUserButtonPressed();
        editMedicationButtonPressed();

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
