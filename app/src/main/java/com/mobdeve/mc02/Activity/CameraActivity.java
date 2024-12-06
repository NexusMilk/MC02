package com.mobdeve.mc02.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.mobdeve.mc02.R;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = "CameraActivity";
    private static final int CAMERA_PERMISSION_CODE = 100;

    private Button cameraButton, backButton;
    private ImageView clickImageView; // Reference the correct ImageView
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Initialize UI components
        clickImageView = findViewById(R.id.click_image); // Use the correct ImageView ID
        cameraButton = findViewById(R.id.btncamera_id);
        backButton = findViewById(R.id.backButton);

        // Register ActivityResultLauncher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleCameraResult
        );

        // Set listeners for buttons
        cameraButton.setOnClickListener(v -> checkCameraPermission());
        backButton.setOnClickListener(v -> navigateBackToMainActivity());
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        try {
            File imageFile = createImageFile();
            imageUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", imageFile);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activityResultLauncher.launch(cameraIntent);

        } catch (IOException e) {
            Log.e(TAG, "Error creating image file: " + e.getMessage());
            Toast.makeText(this, "Error creating image file.", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        File imageFile = new File(getExternalFilesDir(null), "captured_image.jpg");

        if (!imageFile.exists() && !imageFile.createNewFile()) {
            throw new IOException("Failed to create image file.");
        }

        Log.d(TAG, "Image file created at: " + imageFile.getAbsolutePath());
        return imageFile;
    }

    private void handleCameraResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            if (imageUri != null) {
                clickImageView.setImageURI(imageUri); // Update the correct ImageView
            } else {
                Toast.makeText(this, "Image URI is null.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Failed to capture image.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateBackToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
