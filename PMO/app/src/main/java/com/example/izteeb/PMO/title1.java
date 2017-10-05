package com.example.izteeb.PMO;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class title1 extends ActionBarActivity {

    Button login1;
    Button register1;

    public void loginOnClick(){
        login1 = (Button) findViewById(R.id.login1);
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(title1.this, login_page.class));
            }
        });
    }

    public void registerOnClick(){
        register1 = (Button) findViewById(R.id.sign1);
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(title1.this,register.class));
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        loginOnClick();
        registerOnClick();
    }
}
