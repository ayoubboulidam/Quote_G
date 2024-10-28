package com.example.my_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private Button generateQuoteButton;

    // Array of quotes
    private String[] quotes = {
            "The best way to predict the future is to create it.",
            "Do what you can, with what you have, where you are.",
            "Success is not final, failure is not fatal: It is the courage to continue that counts.",
            "Don’t watch the clock; do what it does. Keep going.",
            "Keep your eyes on the stars, and your feet on the ground."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        quoteTextView = findViewById(R.id.quoteTextView);
        generateQuoteButton = findViewById(R.id.generateQuoteButton);

        // Set button click listener
        generateQuoteButton.setOnClickListener(v -> showRandomQuote());
    }

    // Method to show a random quote
    private void showRandomQuote() {
        int randomIndex = new Random().nextInt(quotes.length);
        quoteTextView.setText(quotes[randomIndex]);
    }
}
