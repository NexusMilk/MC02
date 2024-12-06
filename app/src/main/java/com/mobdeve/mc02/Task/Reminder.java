package com.mobdeve.mc02.Task;

public class Reminder {
    private String taskName;
    private String taskDate;

    public Reminder(String taskName, String taskDate) {
        this.taskName = taskName;
        this.taskDate = taskDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }
}
