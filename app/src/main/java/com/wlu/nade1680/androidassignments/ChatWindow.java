package com.wlu.nade1680.androidassignments;

import android.content.Context;
import android.os.Bundle;
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
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        list.setAdapter (messageAdapter);
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                messages.add(text.getText().toString());
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
                text.setText("");
            }
        });
    }
}