package com.example.my_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddQuoteActivity extends AppCompatActivity {

    private EditText quoteEditText, ownerEditText;
    private Button submitButton;
    private Button cancelButton; // Cancel button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        quoteEditText = findViewById(R.id.quoteEditText);
        ownerEditText = findViewById(R.id.ownerEditText);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton); // Initialize the cancel button

        submitButton.setOnClickListener(v -> {
            String quoteText = quoteEditText.getText().toString().trim();
            String quoteOwner = ownerEditText.getText().toString().trim();

            if (!quoteText.isEmpty() && !quoteOwner.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("quote_text", quoteText);
                resultIntent.putExtra("quote_owner", quoteOwner);
                setResult(RESULT_OK, resultIntent);
                finish(); // Close the activity
            }
        });

        // Set onClick listener for the Cancel button
        cancelButton.setOnClickListener(v -> finish()); // Close the activity
    }
}
