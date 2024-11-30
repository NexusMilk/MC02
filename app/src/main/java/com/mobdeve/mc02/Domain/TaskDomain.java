package com.mobdeve.mc02.Domain;

import android.net.Uri;

/**
 * Represents a task with a title, an associated image path, and an optional attached file.
 */
public class TaskDomain {
    private String title;            // The title of the task
    private String picPath;          // The path to the picture associated with the task
    private Uri attachedFileUri;     // The URI of any attached file related to the task

    /**
     * Constructs a TaskDomain with a title and picture path.
     *
     * @param title   The title of the task.
     * @param picPath The path to the associated picture.
     */
    public TaskDomain(String title, String picPath) {
        this.title = title;
        this.picPath = picPath;
        this.attachedFileUri = null;  // No attached file by default
    }

    /**
     * Constructs a TaskDomain with a title, picture path, and an attached file URI.
     *
     * @param title          The title of the task.
     * @param picPath        The path to the associated picture.
     * @param attachedFileUri The URI of the attached file.
     */
    public TaskDomain(String title, String picPath, Uri attachedFileUri) {
        this.title = title;
        this.picPath = picPath;
        this.attachedFileUri = attachedFileUri;
    }

    // Getter and setter methods

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Uri getAttachedFileUri() {
        return attachedFileUri;
    }

    public void setAttachedFileUri(Uri attachedFileUri) {
        this.attachedFileUri = attachedFileUri;
    }

    @Override
    public String toString() {
        return "TaskDomain{" +
                "title='" + title + '\'' +
                ", picPath='" + picPath + '\'' +
                ", attachedFileUri=" + attachedFileUri +
                '}';
    }
}
