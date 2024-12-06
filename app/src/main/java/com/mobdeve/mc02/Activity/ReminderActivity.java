package com.mobdeve.mc02.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobdeve.mc02.Adapter.ReminderAdapter;
import com.mobdeve.mc02.R;
import com.mobdeve.mc02.Receiver.ReminderNotificationReceiver;
import com.mobdeve.mc02.Task.Reminder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private ArrayList<Reminder> reminders;
    private ReminderAdapter adapter;
    private ActivityResultLauncher<Intent> createReminderLauncher;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        ListView listView = findViewById(R.id.listView);
        Button btnAddReminder = findViewById(R.id.btnAddReminder);

        gson = new Gson();
        sharedPreferences = getSharedPreferences("reminderPrefs", MODE_PRIVATE);

        // Load the reminders from SharedPreferences
        reminders = loadReminders();

        adapter = new ReminderAdapter(this, reminders);
        listView.setAdapter(adapter);

        // Register the activity result launcher
        createReminderLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        String title = data.getStringExtra("title");
                        String date = data.getStringExtra("date");
                        String time = data.getStringExtra("time");

                        // Add the new reminder to the list
                        Reminder reminder = new Reminder(title, date + " " + time);
                        reminders.add(reminder);
                        adapter.notifyDataSetChanged();

                        // Save the reminders in SharedPreferences
                        saveReminders();

                        // Schedule a notification for the new reminder
                        setReminderNotification(title);
                    }
                });

        // Handle delete callback
        adapter.setOnReminderDeletedListener(deletedReminder -> {
            reminders.remove(deletedReminder); // Remove reminder from the list
            saveReminders(); // Save the updated list
        });

        // Handle the "Create a Reminder" button click
        btnAddReminder.setOnClickListener(v -> {
            Intent intent = new Intent(ReminderActivity.this, CreateReminderActivity.class);
            createReminderLauncher.launch(intent);
        });
    }

    private void setReminderNotification(String title) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);

        Intent intent = new Intent(ReminderActivity.this, ReminderNotificationReceiver.class);
        intent.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    // For Android 12 and above
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                } else {
                    // For Android 11 and below
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to save reminders to SharedPreferences
    private void saveReminders() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(reminders);  // Convert reminders list to JSON
        editor.putString("reminders_list", json);
        editor.apply();  // Apply the changes to save
    }

    // Method to load reminders from SharedPreferences
    private ArrayList<Reminder> loadReminders() {
        String json = sharedPreferences.getString("reminders_list", null);
        Type type = new TypeToken<ArrayList<Reminder>>() {}.getType();
        ArrayList<Reminder> loadedReminders = gson.fromJson(json, type);

        if (loadedReminders == null) {
            loadedReminders = new ArrayList<>();  // If no reminders are found, initialize an empty list
        }

        return loadedReminders;
    }
}
