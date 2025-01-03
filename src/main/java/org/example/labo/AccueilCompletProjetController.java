package org.example.labo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilCompletProjetController implements Initializable {

    @FXML
    private Button svt;

    @FXML
    private Button chimie;

    @FXML
    void btnCHIMIE(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("accueil.xml"));
        Scene scene = new Scene(root1);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.show();
    }

    @FXML
    void btnSVT(ActionEvent event) throws IOException {
        System.out.println("boutton SVT clicker");
        // Assurez-vous que le chemin est correct
        Parent root2 = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        Scene scene = new Scene(root2);
        Stage stage2 = new Stage();
        stage2.setScene(scene);
        stage2.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
