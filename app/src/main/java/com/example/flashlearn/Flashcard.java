package com.example.flashlearn;

public class Flashcard {

    private String question;
    private String answer;

    // Default constructor for Firebase
    public Flashcard() {
    }

    // Constructor to initialize flashcard
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getter and Setter methods
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
