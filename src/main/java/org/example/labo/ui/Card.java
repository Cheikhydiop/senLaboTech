package org.example.labo.ui;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.paint.Color;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Card extends VBox {
    public Card(String title, String description, String buttonText) {
        setPadding(new Insets(10));
        setSpacing(10);
        setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.5, 0, 0);"
        );
        setPrefWidth(250);
        setPrefHeight(200);

        Label cardTitle = new Label(title);
        cardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        cardTitle.setTextFill(Color.BLACK);

        Label cardDescription = new Label(description);
        cardDescription.setFont(Font.font("Arial", 14));
        cardDescription.setTextFill(Color.GRAY);
        cardDescription.setWrapText(true);

        Button actionButton = new Button(buttonText);
        actionButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        getChildren().addAll(cardTitle, cardDescription, actionButton);
    }
}
