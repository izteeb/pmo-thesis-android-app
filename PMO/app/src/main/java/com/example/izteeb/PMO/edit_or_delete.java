package com.example.izteeb.PMO;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class edit_or_delete extends AppCompatActivity {


    Button backBttn;
    ImageView deleteBttn, editBttn;


    public void findViewInit(){
        backBttn = (Button) findViewById(R.id.back_bttn6);
        deleteBttn = (ImageView) findViewById(R.id.imageView13);
        editBttn = (ImageView) findViewById(R.id.imageView14);
    }

    public void deleteBttnClicked(){
        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(edit_or_delete.this, chooseSlotForDelete.class));
            }
        });
    }

    public void editBttnClicked(){
        editBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(edit_or_delete.this, chooseSlotForEdit.class));
            }
        });
    }

    public void backBttnClicked(){
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(edit_or_delete.this, user_activity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete);

        findViewInit();
        deleteBttnClicked();
        editBttnClicked();
        backBttnClicked();
    }
}
