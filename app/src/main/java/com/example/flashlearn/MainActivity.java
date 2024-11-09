package com.example.flashlearn;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashcardAdapter adapter;
    private List<Flashcard> flashcardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.flashcard_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flashcardList = new ArrayList<>();
        adapter = new FlashcardAdapter(flashcardList, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fab_add_card);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FlashcardCreationActivity.class);
            startActivity(intent);
        });

        // Load flashcards from Firebase (or mock data for now)
        loadFlashcards();
    }

    private void loadFlashcards() {
        // TODO: Load from Firebase
        flashcardList.add(new Flashcard("Question 1", "Answer 1"));
        flashcardList.add(new Flashcard("Question 2", "Answer 2"));
        adapter.notifyDataSetChanged();
    }
}
