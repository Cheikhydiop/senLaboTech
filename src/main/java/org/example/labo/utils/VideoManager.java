package org.example.labo.utils;


import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.labo.HelloApplication;

public class VideoManager {
    private final HelloApplication application;
    private final AudioManager audioManager;

    public VideoManager(HelloApplication application, AudioManager audioManager) {
        this.application = application;
        this.audioManager = audioManager;
    }

    public void playVideo(String videoFile) {
        try {
            String videoPath = "/video/" + videoFile;
            var videoUrl = getClass().getResource(videoPath);

            if (videoUrl == null) {
                throw new Exception("Vidéo non trouvée à l'emplacement spécifié : " + videoPath);
            }

            Media media = new Media(videoUrl.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            VBox videoContainer = new VBox(10);
            videoContainer.setPadding(new Insets(20));
            videoContainer.setStyle("-fx-background-color: #f0f2f5;");

            Button stopButton = new Button("Arrêter la vidéo");
            stopButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            stopButton.setOnAction(e -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                application.handleMenuButtonClick("video");
            });

            videoContainer.getChildren().addAll(mediaView, stopButton);

            ScrollPane scrollPane = new ScrollPane(videoContainer);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: white;");

            application.getRoot().setCenter(scrollPane);

            mediaPlayer.play();
        } catch (Exception e) {
            audioManager.showAlert("Erreur", "Impossible de lire la vidéo : " + videoFile + "\n" + e.getMessage());
        }
    }
}