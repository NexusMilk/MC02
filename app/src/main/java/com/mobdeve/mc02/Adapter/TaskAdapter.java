package com.mobdeve.mc02.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobdeve.mc02.Domain.TaskDomain;
import com.mobdeve.mc02.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements Filterable {

    private ArrayList<TaskDomain> taskList;
    private ArrayList<TaskDomain> taskListFull; // Backup list for filtering
    private Activity activity;
    private Context context;

    public TaskAdapter(ArrayList<TaskDomain> taskList, Activity activity) {
        this.taskList = taskList;
        this.taskListFull = new ArrayList<>(taskList); // Backup list
        this.activity = activity;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskDomain task = taskList.get(position);
        holder.titleTxt.setText(task.getTitle());

        // Load image using Glide with proper context
        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(task.getPicPath(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.icon);

        // Background and image setup based on position
        switch (position % 5) {
            case 0:
                holder.background_img.setImageResource(R.drawable.bg_1);
                holder.layout.setBackgroundResource(R.drawable.list_background_1);
                break;
            case 1:
                holder.background_img.setImageResource(R.drawable.bg_2);
                holder.layout.setBackgroundResource(R.drawable.list_background_2);
                break;
            case 2:
                holder.background_img.setImageResource(R.drawable.bg_3);
                holder.layout.setBackgroundResource(R.drawable.list_background_3);
                break;
            case 3:
                holder.background_img.setImageResource(R.drawable.bg_4);
                holder.layout.setBackgroundResource(R.drawable.list_background_4);
                break;
            case 4:
                holder.background_img.setImageResource(R.drawable.bg_5);
                holder.layout.setBackgroundResource(R.drawable.list_background_5);
                break;
        }

        // Attach file button handling
        holder.attachFileButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Choose an Action")
                    .setItems(new String[]{"Camera", "Files"}, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                activity.startActivityForResult(cameraIntent, 100); // Request code for camera
                                break;
                            case 1:
                                Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                fileIntent.setType("*/*");
                                activity.startActivityForResult(fileIntent, 101); // Request code for file selection
                                break;
                        }
                    })
                    .show();
        });

        // Check if there is an attached file and update the visibility accordingly
        if (task.getAttachedFileUri() != null) {
            holder.attachedFileTextView.setVisibility(View.VISIBLE);
            holder.attachedFileTextView.setText("Attached: " + task.getAttachedFileUri().getLastPathSegment());
        } else {
            holder.attachedFileTextView.setVisibility(View.GONE);
        }

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Remove task from the list
                        taskList.remove(position);
                        // Notify the adapter that the item has been removed
                        notifyItemRemoved(position);
                        // Notify the adapter about the item range change
                        notifyItemRangeChanged(position, taskList.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public Filter getFilter() {
        return taskFilter;
    }

    // Filter logic optimized for performance
    private final Filter taskFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TaskDomain> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(taskListFull); // If no query, show all tasks
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TaskDomain task : taskListFull) {
                    // Add task if the title matches the search pattern
                    if (task.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(task);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            taskList.clear();
            taskList.addAll((List<TaskDomain>) results.values); // Update the task list with filtered results
            notifyDataSetChanged();
        }
    };

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView icon, background_img;
        Button attachFileButton;
        TextView attachedFileTextView;
        ConstraintLayout layout;
        ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            icon = itemView.findViewById(R.id.pic);
            background_img = itemView.findViewById(R.id.background_img);
            attachFileButton = itemView.findViewById(R.id.btncamera_id);
            attachedFileTextView = itemView.findViewById(R.id.attached_file_txt);
            layout = itemView.findViewById(R.id.mail_layout);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
