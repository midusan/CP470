package com.wlu.nade1680.androidassignments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {
    String message = "You selected item 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        switch(mi.getItemId()) {
            case R.id.option1:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(findViewById(R.id.toolbar), message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.option2:
                Log.d("Toolbar", "Option 2 selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.dialog_text);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.option3:
                Log.d("Toolbar", "Option 3 selected");
                AlertDialog.Builder message_builder = new AlertDialog.Builder(TestToolbar.this);
                // Get the layout inflater
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_signin, null);
                message_builder.setView(v);
                message_builder.setTitle(R.string.message_title);
                message_builder.setMessage(R.string.message_text);
                final EditText input = v.findViewById(R.id.new_message);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                message_builder
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                message = input.getText().toString();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // Show the AlertDialog
                AlertDialog message_dialog = message_builder.create();
                message_dialog.show();
                break;
            
            case R.id.action_about:
                Toast.makeText(getApplicationContext(),"Version 1.0, by Midusa Nadeswarathasan", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mi.getItemId());
        }return true;
    }
}