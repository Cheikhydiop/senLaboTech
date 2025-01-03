package org.example.labo.utils;


import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.paint.Color;
import javafx.scene.PerspectiveCamera;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.scene.input.ScrollEvent;
import org.fxyz3d.importers.Model3D;
import org.fxyz3d.importers.obj.ObjImporter;
import javafx.scene.AmbientLight;
import javafx.scene.PointLight;

import java.util.*;
public class Object3DManager {
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private final Scale scale = new Scale(1, 1, 1);

    public void animateObject(String objFile) {
        try {
            Stage modelStage = new Stage();

            // Charger le modèle 3D avec FXyz3D
            ObjImporter importer = new ObjImporter();
            Model3D model = importer.load(getClass().getResource("/model/" + objFile));

            if (model == null) {
                throw new RuntimeException("Aucun mesh trouvé dans le fichier OBJ");
            }

            // Créer un groupe pour le modèle
            Group modelGroup = new Group();

            // Ajouter des lumières pour un meilleur éclairage
            PointLight light1 = new PointLight(Color.WHITE);
            light1.setTranslateX(-2000);
            light1.setTranslateY(-2000);
            light1.setTranslateZ(-2000);

            PointLight light2 = new PointLight(Color.WHITE);
            light2.setTranslateX(2000);
            light2.setTranslateY(2000);
            light2.setTranslateZ(2000);

            AmbientLight ambientLight = new AmbientLight(Color.gray(0.3));

            // Créer un objet Transformations pour l'échelle, la rotation, etc.
            Scale scale = new Scale();
            Rotate rotateX = new Rotate();
            Rotate rotateY = new Rotate();

            // Calculer la taille maximale pour ajuster l'échelle
            double maxDimension = Math.max(
                    Math.max(model.getRoot().getBoundsInLocal().getWidth(),
                            model.getRoot().getBoundsInLocal().getHeight()),
                    model.getRoot().getBoundsInLocal().getDepth());
            double maxScale = 1000.0 / maxDimension; // Agrandir encore l'objet avec un facteur plus élevé

            // Appliquer les transformations
            modelGroup.getTransforms().addAll(rotateX, rotateY, scale);

            // Ajuster la position initiale du modèle pour le centrer
            model.getRoot().setTranslateX(0);
            model.getRoot().setTranslateY(0);
            model.getRoot().setTranslateZ(0);

            // Augmenter la taille initiale du modèle à la taille maximale
            scale.setX(maxScale);
            scale.setY(maxScale);
            scale.setZ(maxScale);

            // Configurer le modèle et ajouter les lumières
            modelGroup.getChildren().addAll(model.getRoot(), light1, light2, ambientLight);

            // Configuration de la scène
            VBox container = new VBox(10);
            container.setPadding(new Insets(10));
            container.setStyle("-fx-background-color: #2b2b2b;");

            // Sous-scène 3D avec dimensions plus petites
            SubScene subScene = new SubScene(modelGroup, 800, 600, true, SceneAntialiasing.BALANCED);
            subScene.setFill(Color.rgb(43, 43, 43));

            // Caméra ajustée pour une meilleure vue de l'objet plus grand
            PerspectiveCamera camera = new PerspectiveCamera(true);
            camera.setTranslateZ(-3000); // Reculer davantage la caméra
            camera.setTranslateY(-400);
            camera.setNearClip(0.1);
            camera.setFarClip(20000.0);
            camera.setFieldOfView(30);
            subScene.setCamera(camera);

            // Interface utilisateur
            Label title = new Label("Visualisation 3D - " + objFile);
            title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            title.setTextFill(Color.WHITE);

            // Contrôles de la vue améliorés
            HBox controls = new HBox(15);
            Button resetButton = new Button("Réinitialiser la vue");
            resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

            // Slider de zoom avec plage plus grande
            Slider zoomSlider = new Slider(5.0, 40.0, 20.0); // Plage de zoom augmentée, valeur initiale à 20.0
            zoomSlider.setShowTickLabels(true);
            zoomSlider.setShowTickMarks(true);
            zoomSlider.setPrefWidth(200);

            // Contrôle de la luminosité
            Label brightnessLabel = new Label("Luminosité:");
            brightnessLabel.setTextFill(Color.WHITE);
            Slider brightnessSlider = new Slider(0, 1, 0.8); // Luminosité initiale augmentée
            brightnessSlider.setShowTickLabels(true);
            brightnessSlider.setShowTickMarks(true);
            brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                double brightness = newVal.doubleValue();
                light1.setColor(Color.WHITE.deriveColor(0, 1, brightness, 1));
                light2.setColor(Color.WHITE.deriveColor(0, 1, brightness, 1));
            });

            resetButton.setOnAction(e -> {
                rotateX.setAngle(0);
                rotateY.setAngle(0);
                scale.setX(maxScale);
                scale.setY(maxScale);
                scale.setZ(maxScale);
                zoomSlider.setValue(20.0);
                brightnessSlider.setValue(0.8);
            });

            zoomSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                // Appliquer le zoom en fonction de la taille initiale de l'objet
                double zoomFactor = newVal.doubleValue() / 20.0; // 20.0 est la valeur initiale du zoom
                double newScale = maxScale * zoomFactor;

                // Limiter l'échelle pour éviter que l'objet devienne trop petit
                if (newScale < maxScale) {
                    newScale = maxScale; // Ne pas réduire l'objet sous sa taille initiale
                }

                scale.setX(newScale);
                scale.setY(newScale);
                scale.setZ(newScale);
            });

            controls.getChildren().addAll(
                    resetButton,
                    new Separator(Orientation.VERTICAL),
                    new Label("Zoom:") {{ setTextFill(Color.WHITE); }},
                    zoomSlider,
                    new Separator(Orientation.VERTICAL),
                    brightnessLabel,
                    brightnessSlider
            );
            controls.setAlignment(Pos.CENTER_LEFT);
            controls.setPadding(new Insets(10));

            // Gestionnaires d'événements
            subScene.setOnMousePressed(event -> {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = rotateX.getAngle();
                anchorAngleY = rotateY.getAngle();
            });

            subScene.setOnMouseDragged(event -> {
                double dx = (anchorY - event.getSceneX()) * 0.5;
                double dy = (anchorX - event.getSceneY()) * 0.5;
                rotateX.setAngle(anchorAngleX - dx);
                rotateY.setAngle(anchorAngleY + dy);
            });

            subScene.setOnScroll((ScrollEvent event) -> {
                double delta = event.getDeltaY();
                double zoomFactor = 1.05;
                if (delta < 0) {
                    zoomFactor = 0.95;
                }

                double newValue = zoomSlider.getValue() * zoomFactor;
                if (newValue >= zoomSlider.getMin() && newValue <= zoomSlider.getMax()) {
                    zoomSlider.setValue(newValue);
                }
            });

            // Ajouter tous les éléments au conteneur
            container.getChildren().addAll(title, subScene, controls);
            container.setAlignment(Pos.TOP_CENTER);

            // Configuration finale de la fenêtre
            Scene scene = new Scene(container);
            modelStage.setTitle("Visualisation 3D - " + objFile);
            modelStage.setScene(scene);
            modelStage.setMaximized(false); // Fenêtre non maximisée
            modelStage.setWidth(1000); // Taille de la fenêtre réduite
            modelStage.setHeight(800); // Taille de la fenêtre réduite
            modelStage.show();

        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors du chargement du modèle : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
