package com.mobdeve.mc02.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
        ImageButton deleteButton = convertView.findViewById(R.id.delete_button);

        taskName.setText(reminder.getTaskName());
        taskDate.setText(reminder.getTaskDate());

        // Set click listener for delete button
        deleteButton.setOnClickListener(v -> {
            remove(reminder); // Remove the reminder from the adapter
            notifyDataSetChanged(); // Notify the adapter of the data change
            if (onReminderDeletedListener != null) {
                onReminderDeletedListener.onReminderDeleted(reminder);
            }
        });

        return convertView;
    }

    // Interface for delete callback
    private OnReminderDeletedListener onReminderDeletedListener;

    public void setOnReminderDeletedListener(OnReminderDeletedListener listener) {
        this.onReminderDeletedListener = listener;
    }

    public interface OnReminderDeletedListener {
        void onReminderDeleted(Reminder reminder);
    }
}
