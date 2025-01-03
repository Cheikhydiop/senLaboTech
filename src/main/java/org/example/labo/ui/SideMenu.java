package org.example.labo.ui;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.labo.HelloApplication;

public class SideMenu extends VBox {
    private HelloApplication mainApp;

    public SideMenu(HelloApplication mainApp) {
        this.mainApp = mainApp;
        setStyle("-fx-background-color: #34495e; -fx-padding: 10px;");
        setPrefWidth(200);
        setSpacing(10);

        Button[] menuButtons = {
                createMenuButton("Chimie", "chimie"),
                createMenuButton("SVT", "svt")
        };

        getChildren().addAll(menuButtons);
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

        button.setOnAction(e -> mainApp.handleMenuButtonClick(button.getId()));
        return button;
    }
}
