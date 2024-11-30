package com.mobdeve.mc02.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobdeve.mc02.R;

import com.mobdeve.mc02.Task.Reminder;

import java.util.ArrayList;

public class ReminderAdapter extends ArrayAdapter<Reminder> {

    public ReminderAdapter(Context context, ArrayList<Reminder> reminders) {
        super(context, 0, reminders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reminder reminder = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reminder_list_item, parent, false);
        }

        TextView taskName = convertView.findViewById(R.id.taskName);
        TextView taskDate = convertView.findViewById(R.id.taskDate);

        taskName.setText(reminder.getTaskName());
        taskDate.setText(reminder.getTaskDate());

        return convertView;
    }
}
