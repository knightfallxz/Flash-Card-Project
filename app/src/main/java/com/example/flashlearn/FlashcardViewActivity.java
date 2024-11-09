package com.example.flashlearn;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FlashcardViewActivity extends AppCompatActivity {

    private TextView textFlashcardFront, textFlashcardBack;
    private boolean isFrontVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_view);

        textFlashcardFront = findViewById(R.id.text_flashcard_front);
        textFlashcardBack = findViewById(R.id.text_flashcard_back);

        textFlashcardFront.setOnClickListener(view -> flipFlashcard());
        textFlashcardBack.setOnClickListener(view -> flipFlashcard());
    }

    private void flipFlashcard() {
        Animation flipOut = AnimationUtils.loadAnimation(this, R.anim.flip_out);
        Animation flipIn = AnimationUtils.loadAnimation(this, R.anim.flip_in);

        if (isFrontVisible) {
            textFlashcardFront.startAnimation(flipOut);
            textFlashcardFront.setVisibility(View.GONE);
            textFlashcardBack.startAnimation(flipIn);
            textFlashcardBack.setVisibility(View.VISIBLE);
        } else {
            textFlashcardBack.startAnimation(flipOut);
            textFlashcardBack.setVisibility(View.GONE);
            textFlashcardFront.startAnimation(flipIn);
            textFlashcardFront.setVisibility(View.VISIBLE);
        }
        isFrontVisible = !isFrontVisible;
    }
}
