package org.example.labo.ui;

import javafx.scene.paint.Color;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class TopBar extends HBox {
    public TopBar() {
        setStyle("-fx-background-color: #2c3e50; -fx-padding: 10px;");
        setSpacing(20);

        Label title = new Label("SenLabo Tech");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.WHITE);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");
        searchField.setPrefWidth(300);

        Button profileBtn = new Button("Profile");
        profileBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        getChildren().addAll(title, searchField, profileBtn);
    }
}
