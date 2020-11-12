package com.wlu.nade1680.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    ArrayList<String> messages = new ArrayList<>();
    ChatDatabaseHelper db_helper;
    SQLiteDatabase database;
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){
            return messages.size();
        }
        public String getItem(int position){
            return messages.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            else
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        ListView list = (ListView) findViewById(R.id.chatView);
        final EditText text = (EditText) findViewById(R.id.edit_text);
        final ChatAdapter messageAdapter = new ChatAdapter( this );
        list.setAdapter (messageAdapter);
        db_helper = new ChatDatabaseHelper(this);
        database = db_helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from "+ ChatDatabaseHelper.TABLE_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:"+cursor.getString(cursor.getColumnIndex((ChatDatabaseHelper.KEY_MESSAGE))));
            Log.i(ACTIVITY_NAME,"Cursor's column count ="+cursor.getColumnCount());
            messages.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        for (int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Column name: "+cursor.getColumnName(i));
        }
        cursor.close();
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                String message = text.getText().toString();
                messages.add(message);
                values.put(ChatDatabaseHelper.KEY_MESSAGE, message);
                database.insert(ChatDatabaseHelper.TABLE_NAME, "NullPlaceholder", values) ;
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
                text.setText("");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        db_helper.close();
    }

}