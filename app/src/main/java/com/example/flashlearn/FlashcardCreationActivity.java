package com.example.flashlearn;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FlashcardCreationActivity extends AppCompatActivity {

    private EditText editQuestion, editAnswer;
    private Button btnSave;
    private DatabaseReference flashcardDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_creation);

        editQuestion = findViewById(R.id.edit_question);
        editAnswer = findViewById(R.id.edit_answer);
        btnSave = findViewById(R.id.btn_save);

        flashcardDatabase = FirebaseDatabase.getInstance().getReference("flashcards");

        btnSave.setOnClickListener(view -> {
            String question = editQuestion.getText().toString();
            String answer = editAnswer.getText().toString();

            if (TextUtils.isEmpty(question) || TextUtils.isEmpty(answer)) {
                Toast.makeText(FlashcardCreationActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
            } else {
                saveFlashcardToFirebase(question, answer);
            }
        });
    }

    private void saveFlashcardToFirebase(String question, String answer) {
        String flashcardId = flashcardDatabase.push().getKey();
        Flashcard flashcard = new Flashcard(question, answer);
        flashcardDatabase.child(flashcardId).setValue(flashcard)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(FlashcardCreationActivity.this, "Flashcard saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(FlashcardCreationActivity.this, "Error saving flashcard", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}