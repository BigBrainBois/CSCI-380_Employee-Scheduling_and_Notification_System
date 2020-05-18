package com.nyit.employee_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewRequest extends AppCompatActivity {

    ListView listView;
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> msg = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        listView = findViewById(R.id.listView);
        configureListContent();
        listView.setAdapter(new MyListAdapter(this, R.layout.request_bar, date, msg));
    }

    private void configureListContent() {
        for(int i = 0; i < 30; i++) {
            date.add("Date " + i);
            msg.add("Message " + i);
        }
    }


    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private List<String> mObjects1;
        private MyListAdapter(Context context, int resource, List<String> objects, List<String> objects1) {
            super(context, resource, objects);
            mObjects = objects;
            mObjects1 = objects1;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.date = convertView.findViewById(R.id.date);
                viewHolder.message = convertView.findViewById(R.id.message);
                viewHolder.accept = convertView.findViewById(R.id.acceptButton);
                viewHolder.decline = convertView.findViewById(R.id.declineButton);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Accept request #" + position, Toast.LENGTH_SHORT).show();
                }
            });

            mainViewholder.decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Decline request #" + position, Toast.LENGTH_SHORT).show();
                }
            });

            mainViewholder.date.setText(getItem(position));
            mainViewholder.message.setText(getItem(position));

            return convertView;
        }
    }
    public static class ViewHolder {

        TextView date;
        TextView message;
        Button accept;
        Button decline;
    }

}
