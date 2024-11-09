package com.example.flashlearn;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.OnCompleteListener;
import com.google.firebase.database.Task;

public class FlashcardCreationActivity extends AppCompatActivity {

    private EditText editQuestion, editAnswer;
    private Button btnSave;

    // Firebase reference
    private DatabaseReference flashcardDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_creation);

        // Initialize Firebase
        flashcardDatabase = FirebaseDatabase.getInstance().getReference("flashcards");

        // Bind UI elements
        editQuestion = findViewById(R.id.edit_question);
        editAnswer = findViewById(R.id.edit_answer);
        btnSave = findViewById(R.id.btn_save_flashcard);

        // Set up the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editQuestion.getText().toString().trim();
                String answer = editAnswer.getText().toString().trim();

                // Debug log to check inputs
                Log.d("FlashcardDebug", "Button clicked. Question: " + question + ", Answer: " + answer);

                saveFlashcardToFirebase(question, answer);
            }
        });
    }

    private void saveFlashcardToFirebase(String question, String answer) {
        // Log the inputs for debugging
        Log.d("FlashcardDebug", "Question: " + question);
        Log.d("FlashcardDebug", "Answer: " + answer);

        // Validate inputs
        if (TextUtils.isEmpty(question) || TextUtils.isEmpty(answer)) {
            Log.d("FlashcardDebug", "Question or Answer is empty!");
            Toast.makeText(this, "Please enter both question and answer", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with Firebase operation
        String flashcardId = flashcardDatabase.push().getKey();
        Flashcard flashcard = new Flashcard(question, answer);

        flashcardDatabase.child(flashcardId).setValue(flashcard)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("FlashcardDebug", "Flashcard saved successfully");
                            Toast.makeText(FlashcardCreationActivity.this, "Flashcard saved", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity after saving
                        } else {
                            Log.e("FlashcardDebug", "Error saving flashcard: " + task.getException());
                            Toast.makeText(FlashcardCreationActivity.this, "Error saving flashcard", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
