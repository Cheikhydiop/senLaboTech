package org.example.demo1;


public class Question {
    String text;
    String correctAnswer;
    String audioPath;
    String modelPath;
    String explanation;

    public Question(String text, String correctAnswer, String audioPath, String modelPath, String explanation) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.audioPath = audioPath;
        this.modelPath = modelPath;
        this.explanation = explanation;
    }
}