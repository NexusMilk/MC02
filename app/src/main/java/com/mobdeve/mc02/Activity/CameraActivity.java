package com.mobdeve.mc02.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.mobdeve.mc02.R;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = "ActivityCamera";
    private static final int CAMERA_PERMISSION_CODE = 100;

    private Button cameraButton;
    private Button backButton;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imageUri; // Declare Uri for the image

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = findViewById(R.id.image);
        cameraButton = findViewById(R.id.btncamera_id);
        backButton = findViewById(R.id.backButton);

        // Register the ActivityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // The image is saved at the provided URI
                            imageView.setImageURI(imageUri); // Set the image to ImageView
                            returnImageUri(); // Return the image URI to the calling activity
                        } else {
                            Toast.makeText(CameraActivity.this, "Failed to capture image.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Set onClickListener for the camera button
        cameraButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request Camera Permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            } else {
                // Permission already granted
                launchCamera();
            }
        });

        // Set onClickListener for the back button
        backButton.setOnClickListener(v -> goBackToMainActivity());
    }

    private void launchCamera() {
        // Create a file to save the image
        File imageFile = new File(getExternalFilesDir(null), "captured_image.jpg");

        // Ensure the file is created successfully
        try {
            if (imageFile.createNewFile()) {
                Log.d(TAG, "Image file created: " + imageFile.getAbsolutePath());
            } else {
                Log.d(TAG, "Image file already exists: " + imageFile.getAbsolutePath());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error creating image file: " + e.getMessage());
            Toast.makeText(this, "Error creating image file.", Toast.LENGTH_SHORT).show();
            return; // Exit if file creation fails
        }

        imageUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", imageFile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // Use the URI to save the image
        activityResultLauncher.launch(cameraIntent);
    }

    private void goBackToMainActivity() {
        Intent intent = new Intent(CameraActivity.this, MainActivity.class); // Create an intent to go back to MainActivity
        startActivity(intent);
        finish(); // Optionally finish the current activity
    }

    private void returnImageUri() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("imageUri", imageUri.toString()); // Return the image URI
        setResult(RESULT_OK, returnIntent);
        finish(); // Close the Activity
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
