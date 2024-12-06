package com.mobdeve.mc02.Activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.mc02.R;
import com.mobdeve.mc02.Task.Planner;

import java.util.ArrayList;
import java.util.List;

public class TaskPlannerActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PlanPrefs";
    private static final String KEY_NOTE_COUNT = "PlanCount";
    private LinearLayout plannerContainer;
    private List<Planner> plannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_activity);

        plannerContainer = findViewById(R.id.plannerContainer);
        Button saveButton = findViewById(R.id.saveButton);
        EditText deadlineDateEditText = findViewById(R.id.et_deadline_date);
        EditText plannerTimeEditText = findViewById(R.id.et_planner_time);

        plannerList = new ArrayList<>();

        // Set up the TextWatcher for date formatting
        deadlineDateEditText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String format = "##/##/####";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String cleanString = s.toString().replaceAll("[^\\d]", "");
                    StringBuilder formatted = new StringBuilder();
                    int i = 0;

                    for (char c : format.toCharArray()) {
                        if (i >= cleanString.length()) break;
                        if (c == '#') {
                            formatted.append(cleanString.charAt(i));
                            i++;
                        } else {
                            formatted.append(c);
                        }
                    }

                    current = formatted.toString();
                    deadlineDateEditText.setText(current);
                    deadlineDateEditText.setSelection(current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up the TextWatcher for time formatting
        plannerTimeEditText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String format = "##:##";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String cleanString = s.toString().replaceAll("[^\\d]", "");
                    StringBuilder formatted = new StringBuilder();
                    int i = 0;

                    for (char c : format.toCharArray()) {
                        if (i >= cleanString.length()) break;
                        if (c == '#') {
                            formatted.append(cleanString.charAt(i));
                            i++;
                        } else if (formatted.length() > 0) {
                            formatted.append(c);
                        }
                    }

                    current = formatted.toString();
                    plannerTimeEditText.setText(current);
                    plannerTimeEditText.setSelection(current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlan();
            }
        });

        loadPlansFromPreferences();
        displayPlans();
    }

    private void displayPlans() {
        plannerContainer.removeAllViews(); // Clear previous views before displaying
        for (Planner planner : plannerList) {
            createPlanView(planner);
        }
    }

    private void loadPlansFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int planCount = sharedPreferences.getInt(KEY_NOTE_COUNT, 0);

        for (int i = 0; i < planCount; i++) {
            String title = sharedPreferences.getString("planner_title_" + i, "");
            String content = sharedPreferences.getString("planner_content_" + i, "");
            String deadlineDate = sharedPreferences.getString("planner_deadline_date_" + i, "");
            String plannerTime = sharedPreferences.getString("planner_planner_time_" + i, "");

            Planner planner = new Planner();
            planner.setTitle(title);
            planner.setContent(content);
            planner.setDeadlineDate(deadlineDate);
            planner.setPlannerTime(plannerTime);

            plannerList.add(planner);
        }
    }

    private void savePlan() {
        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText contentEditText = findViewById(R.id.contentEditText);
        EditText deadlineDateEditText = findViewById(R.id.et_deadline_date);
        EditText plannerTimeEditText = findViewById(R.id.et_planner_time);

        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String deadlineDate = deadlineDateEditText.getText().toString().trim();
        String plannerTime = plannerTimeEditText.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Planner planner = new Planner();
        planner.setTitle(title);
        planner.setContent(content);
        planner.setDeadlineDate(deadlineDate);
        planner.setPlannerTime(plannerTime);

        plannerList.add(planner);
        savePlansToPreferences();
        createPlanView(planner);
        clearInputFields();
    }

    private void clearInputFields() {
        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText contentEditText = findViewById(R.id.contentEditText);
        EditText deadlineDateEditText = findViewById(R.id.et_deadline_date);
        EditText plannerTimeEditText = findViewById(R.id.et_planner_time);

        titleEditText.setText("");
        contentEditText.setText("");
        deadlineDateEditText.setText("");
        plannerTimeEditText.setText("");
    }

    private void createPlanView(final Planner planner) {
        View planView = getLayoutInflater().inflate(R.layout.planner_note, null);
        TextView titleTextView = planView.findViewById(R.id.titleTextView);
        TextView contentTextView = planView.findViewById(R.id.contentTextView);
        TextView deadlineDateTextView = planView.findViewById(R.id.deadlineDateTextView);
        TextView plannerTimeTextView = planView.findViewById(R.id.plannerTimeTextView);
        Button deleteButton = planView.findViewById(R.id.deleteButton);

        titleTextView.setText(planner.getTitle());
        contentTextView.setText(planner.getContent());
        deadlineDateTextView.setText("Deadline: " + planner.getDeadlineDate());
        plannerTimeTextView.setText("Plan Time: " + planner.getPlannerTime());

        // Handle the delete button click
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(planner); // Show the delete confirmation dialog
            }
        });

        plannerContainer.addView(planView);
    }

    private void showDeleteDialog(final Planner planner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this plan.");
        builder.setMessage("Are you sure you want to delete this plan?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePlansAndRefresh(planner);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deletePlansAndRefresh(Planner planner) {
        plannerList.remove(planner);
        savePlansToPreferences();
        displayPlans();
    }

    private void savePlansToPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_NOTE_COUNT, plannerList.size());

        for (int i = 0; i < plannerList.size(); i++) {
            Planner planner = plannerList.get(i);
            editor.putString("planner_title_" + i, planner.getTitle());
            editor.putString("planner_content_" + i, planner.getContent());
            editor.putString("planner_deadline_date_" + i, planner.getDeadlineDate());
            editor.putString("planner_planner_time_" + i, planner.getPlannerTime());
        }

        editor.apply();
    }
}
