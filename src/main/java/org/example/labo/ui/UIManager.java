package org.example.labo.ui;

import javafx.scene.*;

import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class UIManager {
    private BorderPane root;
    private Stage primaryStage;

    public UIManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #f0f2f5;");
    }

    public void initialize() {
        HBox topBar = createTopBar();
        root.setTop(topBar);

        VBox sideMenu = createSideMenu();
        root.setLeft(sideMenu);

        VBox emptyContent = new VBox();
        emptyContent.setStyle("-fx-background-color: white;");
        root.setCenter(emptyContent);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Simulation TP - Chimie & SVT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 10px;");
        topBar.setSpacing(20);

        Label title = new Label("Simulation TP");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.WHITE);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");
        searchField.setPrefWidth(300);

        Button profileBtn = new Button("Profile");
        profileBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        topBar.getChildren().addAll(title, searchField, profileBtn);
        return topBar;
    }

    private VBox createSideMenu() {
        VBox sideMenu = new VBox();
        sideMenu.setStyle("-fx-background-color: #34495e; -fx-padding: 10px;");
        sideMenu.setPrefWidth(200);
        sideMenu.setSpacing(10);

        Button[] menuButtons = {
                createMenuButton("Chimie", "chimie"),
                createMenuButton("SVT", "svt")
        };

        sideMenu.getChildren().addAll(menuButtons);
        return sideMenu;
    }

    private Button createMenuButton(String text, String id) {
        Button button = new Button(text);
        button.setId(id);
        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-pref-width: 180px;" +
                        "-fx-alignment: CENTER_LEFT;" +
                        "-fx-padding: 10px;"
        );
        button.setOnMouseEntered(e ->
                button.setStyle(button.getStyle() + "-fx-background-color: #2c3e50;"));
        button.setOnMouseExited(e ->
                button.setStyle(button.getStyle() + "-fx-background-color: transparent;"));

        button.setOnAction(e -> handleMenuButtonClick(button.getId()));
        return button;
    }

    private void handleMenuButtonClick(String buttonId) {
        // Logique pour gérer le clic sur les boutons du menu
    }
}
