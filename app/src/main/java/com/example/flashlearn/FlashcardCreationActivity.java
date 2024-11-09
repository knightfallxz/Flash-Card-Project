package com.example.flashlearn;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

        // Debugging: Write a test value to a different node "testNode"
        FirebaseDatabase.getInstance().getReference("testNode").setValue("Hello, Firebase!")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FirebaseDebug", "Test data saved to Firebase");
                    } else {
                        Log.d("FirebaseDebug", "Error saving test data: " + task.getException());
                    }
                });

        // Now save the flashcard to the "flashcards" node
        flashcardDatabase.child(flashcardId).setValue(flashcard)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FlashcardCreationActivity.this, "Flashcard saved", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(FlashcardCreationActivity.this, "Error saving flashcard", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
