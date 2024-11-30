package com.mobdeve.mc02.Task;


public class Planner {
    private String title;
    private String content;
    private String deadlineDate; // New field for deadline date
    private String plannerTime; // New field for reminder time

    // Default constructor
    public Planner() {

    }

    // Constructor with all fields
    public Planner(String title, String content, String deadlineDate, String PlannerTime) {
        this.title = title;
        this.content = content;
        this.deadlineDate = deadlineDate; // Initialize deadline date
        this.plannerTime = plannerTime; // Initialize reminder time
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and Setter for deadlineDate
    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    // Getter and Setter for reminderTime
    public String getPlannerTime() {
        return plannerTime;
    }

    public void setPlannerTime(String plannerTime) {
        this.plannerTime = plannerTime;
    }
}
