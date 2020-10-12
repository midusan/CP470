package com.wlu.nade1680.androidassignments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button start_chat = (Button) findViewById(R.id.start_chat);
        start_chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivityForResult(intent,10);
            }
        });
        Button start_toolbar = (Button) findViewById(R.id.start_toolbar);
        start_toolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Toolbar");
                Intent intent = new Intent(StartActivity.this, TestToolbar.class);
                startActivityForResult(intent,10);
            }
        });
    }
}