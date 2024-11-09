package com.example.flashlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FlashcardCreationActivity extends AppCompatActivity {

    private EditText questionEditText;
    private EditText answerEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_creation);

        // Initialize views
        questionEditText = findViewById(R.id.editTextQuestion);
        answerEditText = findViewById(R.id.editTextAnswer);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Flashcards", MODE_PRIVATE);
    }

    // Method to save flashcard
    public void saveFlashcard(View view) {
        String question = questionEditText.getText().toString().trim();
        String answer = answerEditText.getText().toString().trim();

        if (question.isEmpty() || answer.isEmpty()) {
            // Show a Toast if either question or answer is empty
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get existing flashcards from SharedPreferences
        List<Flashcard> flashcards = getFlashcards();

        // Add the new flashcard to the list
        flashcards.add(new Flashcard(question, answer));

        // Save the updated flashcard list to SharedPreferences
        saveFlashcards(flashcards);

        // Show a Toast to confirm the flashcard was saved
        Toast.makeText(this, "Flashcard saved", Toast.LENGTH_SHORT).show();

        // Navigate back to the MainActivity to display the flashcards
        Intent intent = new Intent(FlashcardCreationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // Method to get the list of flashcards from SharedPreferences
    private List<Flashcard> getFlashcards() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("flashcards", null);
        List<Flashcard> flashcards;

        if (json == null) {
            flashcards = new ArrayList<>();
        } else {
            flashcards = gson.fromJson(json, List.class);
        }

        return flashcards;
    }

    // Method to save the list of flashcards to SharedPreferences
    private void saveFlashcards(List<Flashcard> flashcards) {
        Gson gson = new Gson();
        String json = gson.toJson(flashcards);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("flashcards", json);
        editor.apply();
    }
}
