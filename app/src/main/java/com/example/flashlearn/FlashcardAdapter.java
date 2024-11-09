package com.example.flashlearn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Flashcard> flashcards;

    public FlashcardAdapter(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_item, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);
        holder.questionTextView.setText(flashcard.getQuestion());
        holder.answerTextView.setText(flashcard.getAnswer());
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    // Method to update the flashcard list
    public void updateFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        notifyDataSetChanged();
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {

        TextView questionTextView;
        TextView answerTextView;

        public FlashcardViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.flashcard_question);
            answerTextView = itemView.findViewById(R.id.flashcard_answer);
        }
    }
}
