package com.amit.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.amit.flashlight.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.button.getText().toString().equals("Turn on")) {
                    binding.button.setText("Turn off");
                    binding.flashImage.setImageResource(R.drawable.lighton);
                    changeLightState(true);
                } else {
                    binding.button.setText("Turn on");
                    binding.flashImage.setImageResource(R.drawable.lightoff);
                    changeLightState(false);
                }
            }
        });
    }

    private void changeLightState(boolean state) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String camId = null;
            try {
                 camId = cameraManager.getCameraIdList()[0];
                 cameraManager.setTorchMode(camId,state);
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        binding.button.setText("Turn on");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
        .setMessage ("Are you sure you want to exit!")
                .setCancelable (false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }
}