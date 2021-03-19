package com.example.c026_01_0319_2018_messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SMS_PERMISSION_CODE = 0;
    private String recipients[];
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when user taps the sendButton.
     * @param view
     */
    public void sendMessage(View view) {
        // Find inputs by their ids
        EditText recipientEditText = findViewById(R.id.editTextPhone);
        EditText messageEditText = findViewById(R.id.editTextMessage);
        // Extract values from the inputs
        recipients = recipientEditText.getText().toString().split(",");

        message = messageEditText.getText().toString();


        // Check for SMS sending permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Request SMS sending permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION_CODE);
        } else if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendMessages(recipients, message);
        }
    }

    public void sendMessages(String recipients[], String message) {
        // Send SMS if we have the permissions
        SmsManager smsManager = SmsManager.getDefault();
        for (String recipient : recipients ) {
            smsManager.sendTextMessage(recipient, null, message, null, null);
        }
        // Show success toast message
        Toast.makeText(getApplicationContext(), "SMS sent successfully.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendMessages(recipients, message);
            } else {
                // Show failure toast message
                Toast.makeText(getApplicationContext(), "Failure sending SMS. Try Again!", Toast.LENGTH_LONG).show();
            }
        }
    }

}