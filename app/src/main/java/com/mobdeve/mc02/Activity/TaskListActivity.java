package com.mobdeve.mc02.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ImageView;

import com.mobdeve.mc02.Adapter.TaskAdapter;
import com.mobdeve.mc02.Domain.TaskDomain;
import com.mobdeve.mc02.R;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private TaskAdapter taskAdapter;
    private ArrayList<TaskDomain> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Back button functionality
        ImageView backButton = findViewById(R.id.imageViewBack);
        backButton.setOnClickListener(v -> finish());

        // Initialize RecyclerView and tasks
        initRecyclerView();

        // Setup SearchView for filtering tasks
        setupSearchView();
    }

    private void initRecyclerView() {
        // Initialize task list with sample data
        taskList = new ArrayList<>();
        taskList.add(new TaskDomain("Create a Program for MOBDEV", "icon1"));
        taskList.add(new TaskDomain("Create Mind Mapping", "icon3"));
        taskList.add(new TaskDomain("Solve the Arithmetic Equation", "icon4"));
        taskList.add(new TaskDomain("CCPROG2 Assignment", "icon4"));
        taskList.add(new TaskDomain("Read Chapter 119 of Discrete Mathematics", "icon5"));
        taskList.add(new TaskDomain("IT PROG Essay", "icon1"));
        taskList.add(new TaskDomain("CAPSTONE PROJECT", "icon3"));

        // Set up RecyclerView
        RecyclerView recyclerViewTasks = findViewById(R.id.recyclerViewCourse);
        if (recyclerViewTasks != null) {
            recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            taskAdapter = new TaskAdapter(taskList, this);
            recyclerViewTasks.setAdapter(taskAdapter);
        }
    }

    private void setupSearchView() {
        // Set up SearchView to filter tasks
        SearchView searchView = findViewById(R.id.search_bar);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    taskAdapter.getFilter().filter(query); // Filter tasks on submit
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    taskAdapter.getFilter().filter(newText); // Filter tasks as the text changes
                    return false;
                }
            });
        }
    }
}
