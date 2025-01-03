package org.example.labo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ChimieViewController {
    @FXML
    private Button homeButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label courseLabel;
    @FXML
    private Label tpLabel;
    @FXML
    private Label modelLabel;

    private void loadView(String fxmlPath, Object source) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) source).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Consider logging this to a file for production.
        }
    }

    @FXML
    private void onActionHome(ActionEvent event) {
        loadView("/com/example/projetjavax/main-view.fxml", homeButton);
    }

    @FXML
    private void onActionLogout(ActionEvent event) {
        loadView("/com/example/projetjavax/login-view.fxml", logoutButton);
    }

    @FXML
    private void onActionCourse(ActionEvent event) {
        loadView("/com/example/projetjavax/course-view.fxml", courseLabel);
    }

    @FXML
    private void onActionTP(ActionEvent event) {
        loadView("/com/example/projetjavax/tp-view.fxml", tpLabel);
    }

    @FXML
    private void onAction3DModel(ActionEvent event) {
        loadView("/com/example/projetjavax/model3d-view.fxml", modelLabel);
    }
}
