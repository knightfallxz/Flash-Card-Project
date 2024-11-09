package com.example.flashlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashcardAdapter flashcardAdapter;
    private List<Flashcard> flashcards;
    private FloatingActionButton fabAddCard;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Flashcards", MODE_PRIVATE);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.flashcard_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize FloatingActionButton
        fabAddCard = findViewById(R.id.fab_add_card);

        // Load flashcards from SharedPreferences
        flashcards = getFlashcards();

        // Set up the RecyclerView with the FlashcardAdapter
        flashcardAdapter = new FlashcardAdapter(flashcards);
        recyclerView.setAdapter(flashcardAdapter);

        // Set the onClickListener for the FloatingActionButton to open FlashcardCreationActivity
        fabAddCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FlashcardCreationActivity.class);
            startActivity(intent);
        });
    }

    // Method to get flashcards from SharedPreferences
    private List<Flashcard> getFlashcards() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("flashcards", null);
        Type type = new TypeToken<List<Flashcard>>() {}.getType();
        List<Flashcard> flashcards = gson.fromJson(json, type);

        if (flashcards == null) {
            flashcards = new ArrayList<>();
        }
        return flashcards;
    }

    // Method to refresh the flashcard list in RecyclerView
    public void refreshFlashcardList() {
        flashcards = getFlashcards();
        flashcardAdapter.updateFlashcards(flashcards);
    }
}
