package com.wlu.nade1680.androidassignments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {
    EditText editText;
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
        editText = findViewById(R.id.new_message);
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
                builder = new AlertDialog.Builder(TestToolbar.this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setView(getLayoutInflater().inflate(R.layout.dialog_signin, null)) //Add a dialog message to strings.xml

                        .setTitle("New Message")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                message = editText.getText().toString();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .show();
                break;
            case R.id.action_about:
                Toast.makeText(getApplicationContext(),"Version 1.0, by Midusa Nadeswarathasan", Toast.LENGTH_SHORT).show();
                break;
        }return true;
    }
}