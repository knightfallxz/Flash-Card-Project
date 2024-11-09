package com.example.flashlearn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashcardAdapter adapter;
    private List<Flashcard> flashcardList;
    private DatabaseReference flashcardDatabase;

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

        // Get a reference to the Firebase Realtime Database
        flashcardDatabase = FirebaseDatabase.getInstance().getReference("flashcards");

        // Load flashcards from Firebase
        loadFlashcards();
    }

    private void loadFlashcards() {
        // Attach a listener to the "flashcards" node
        flashcardDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flashcardList.clear();  // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flashcard flashcard = snapshot.getValue(Flashcard.class);
                    flashcardList.add(flashcard);  // Add the flashcard to the list
                }
                adapter.notifyDataSetChanged();  // Notify the adapter to update the UI
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error loading flashcards: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
