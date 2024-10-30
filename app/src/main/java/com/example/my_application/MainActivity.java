package com.example.my_application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private Button generateQuoteButton, addQuoteButton;
    private List<Quote> quotes;
    private static final String QUOTES_PREF = "quotes_pref";
    private static final String QUOTES_KEY = "quotes_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        quoteTextView = findViewById(R.id.quoteTextView);
        generateQuoteButton = findViewById(R.id.generateQuoteButton);
        addQuoteButton = findViewById(R.id.addQuoteButton);

        // Initialize quotes list
        quotes = new ArrayList<>();
        loadQuotes(); // Load saved quotes

        // Populate with default quotes if none are saved
        if (quotes.isEmpty()) {
            populateDefaultQuotes();
        }

        // Show a random quote when the button is clicked
        generateQuoteButton.setOnClickListener(v -> showRandomQuote());

        // Navigate to AddQuoteActivity
        addQuoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddQuoteActivity.class);
            startActivityForResult(intent, 1); // Request code 1 for result handling
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Retrieve the new quote from AddQuoteActivity
            String text = data.getStringExtra("quote_text");
            String owner = data.getStringExtra("quote_owner");
            quotes.add(new Quote(text, owner));
            saveQuotes(); // Save updated quotes
        }
    }

    private void showRandomQuote() {
        if (!quotes.isEmpty()) {
            int randomIndex = new Random().nextInt(quotes.size());
            Quote randomQuote = quotes.get(randomIndex);
            quoteTextView.setText("\"" + randomQuote.getText() + "\" - " + randomQuote.getOwner());
        }
    }

    private void loadQuotes() {
        SharedPreferences sharedPreferences = getSharedPreferences(QUOTES_PREF, MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(QUOTES_KEY, null);

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonQuote = jsonArray.getJSONObject(i);
                    String text = jsonQuote.getString("text");
                    String owner = jsonQuote.getString("owner");
                    quotes.add(new Quote(text, owner));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveQuotes() {
        SharedPreferences sharedPreferences = getSharedPreferences(QUOTES_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        JSONArray jsonArray = new JSONArray();
        for (Quote quote : quotes) {
            JSONObject jsonQuote = new JSONObject();
            try {
                jsonQuote.put("text", quote.getText());
                jsonQuote.put("owner", quote.getOwner());
                jsonArray.put(jsonQuote);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        editor.putString(QUOTES_KEY, jsonArray.toString());
        editor.apply();
    }

    private void populateDefaultQuotes() {
        // Populate the list with some default quotes
        quotes.add(new Quote("The best way to predict the future is to create it.", "Peter Drucker"));
        quotes.add(new Quote("Do what you can, with what you have, where you are.", "Theodore Roosevelt"));
        quotes.add(new Quote("Success is not final, failure is not fatal: It is the courage to continue that counts.", "Winston Churchill"));
        quotes.add(new Quote("Don’t watch the clock; do what it does. Keep going.", "Sam Levenson"));
        quotes.add(new Quote("Keep your eyes on the stars, and your feet on the ground.", "Theodore Roosevelt"));

        // Save the default quotes to SharedPreferences
        saveQuotes();
    }
}
