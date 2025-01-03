package org.example.labo.utils;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Objects;

public class AudioManager {
    private MediaPlayer mediaPlayer;

    /**
     * Joue un fichier audio basé sur le fichier .obj correspondant.
     *
     * @param objFile Le nom du fichier .obj (ex: "model.obj")
     */
    public void playAudio(String objFile) {
        stopCurrentAudio();
        try {
            // Vérifie si le fichier se termine par ".obj"
            if (objFile != null && objFile.endsWith(".obj")) {
                // Remplace l'extension ".obj" par ".mp3"
                String audioFileName = objFile.substring(0, objFile.lastIndexOf(".")) + ".mp3";

                // Charge l'audio correspondant
                URL audioUrl = getClass().getResource("/audio/" + audioFileName);
                if (audioUrl != null) {
                    Media media = new Media(audioUrl.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                } else {
                    showAlert("Erreur", "Le fichier audio associé n'a pas été trouvé.");
                }
            } else {
                showAlert("Erreur", "Le fichier spécifié n'a pas l'extension .obj.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de lire le fichier audio : " + objFile + "\n" + e.getMessage());
        }
    }

    /**
     * Arrête l'audio actuel si il y en a un qui joue.
     */
    public void stopCurrentAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }

    /**
     * Affiche une alerte d'erreur.
     *
     * @param title   Le titre de l'alerte.
     * @param content Le contenu de l'alerte.
     */
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
