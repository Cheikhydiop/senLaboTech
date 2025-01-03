package org.example.labo.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private final List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    public QuizManager() {
        questions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public VBox createQuizUI() {
        VBox quizContainer = new VBox(10);
        quizContainer.setStyle("-fx-background-color: #f0f2f5; -fx-padding: 20;");

        Label questionLabel = new Label();
        questionLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        ToggleGroup toggleGroup = new ToggleGroup();
        List<RadioButton> options = new ArrayList<>();

        Button nextButton = new Button("Suivant");
        nextButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        nextButton.setOnAction(e -> {
            if (currentQuestionIndex < questions.size()) {
                Question currentQuestion = questions.get(currentQuestionIndex);
                for (RadioButton option : options) {
                    if (option.isSelected() && option.getText().equals(currentQuestion.getCorrectAnswer())) {
                        score++;
                        break;
                    }
                }
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion(currentQuestionIndex, questionLabel, options, toggleGroup);
                } else {
                    showQuizResult();
                }
            }
        });

        Button previousButton = new Button("Précédent");
        previousButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");
        previousButton.setOnAction(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion(currentQuestionIndex, questionLabel, options, toggleGroup);
            }
        });

        quizContainer.getChildren().addAll(questionLabel);
        quizContainer.getChildren().addAll(options);
        quizContainer.getChildren().addAll(previousButton, nextButton);

        if (!questions.isEmpty()) {
            displayQuestion(currentQuestionIndex, questionLabel, options, toggleGroup);
        }

        return quizContainer;
    }

    private void displayQuestion(int index, Label questionLabel, List<RadioButton> options, ToggleGroup toggleGroup) {
        Question question = questions.get(index);
        questionLabel.setText(question.getQuestionText());

        options.clear();
        for (String option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(option);
            radioButton.setToggleGroup(toggleGroup);
            options.add(radioButton);
        }
    }

    private void showQuizResult() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultat du Quiz");
        alert.setHeaderText("Votre score est : " + score + "/" + questions.size());
        alert.showAndWait();
    }
}


