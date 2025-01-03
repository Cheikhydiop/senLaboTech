package org.example.demo1;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.animation.Interpolator;
import javafx.util.Duration;

import org.fxyz3d.importers.Model3D;
import org.fxyz3d.importers.obj.ObjImporter;

import java.util.Objects;

public class HelloApplication extends Application {
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    private MeshView hydrogenMesh, chlorineMesh;
    private Line bondLine;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger les modèles .obj pour l'hydrogène et le chlore
            ObjImporter importer = new ObjImporter();
            Model3D hydrogenModel = importer.load(Objects.requireNonNull(getClass().getResource("/H.obj")));
            Model3D chlorineModel = importer.load(Objects.requireNonNull(getClass().getResource("/Cl.obj")));

            if (hydrogenModel == null || chlorineModel == null) {
                throw new RuntimeException("Aucun modèle trouvé pour H.obj ou Cl.obj");
            }

            // Appliquer des matériaux de base (plus brillants et dynamiques)
            PhongMaterial hydrogenMaterial = new PhongMaterial(Color.RED);
            hydrogenMaterial.setSpecularColor(Color.GREEN);
            hydrogenMesh = (MeshView) hydrogenModel.getMeshViews().get(0);
            hydrogenMesh.setMaterial(hydrogenMaterial);

            PhongMaterial chlorineMaterial = new PhongMaterial(Color.GREEN);
            chlorineMaterial.setSpecularColor(Color.WHITE);
            chlorineMesh = (MeshView) chlorineModel.getMeshViews().get(0);
            chlorineMesh.setMaterial(chlorineMaterial);

            // Ajouter une ligne pour la liaison entre les objets
            bondLine = new Line();
            bondLine.setStrokeWidth(3);
            bondLine.setStroke(Color.BLACK);

            // Groupe pour les objets
            Group modelGroup = new Group(hydrogenMesh, chlorineMesh, bondLine);

            // Appliquer des rotations
            modelGroup.getTransforms().addAll(rotateX, rotateY);

            // Scène et caméra
            Group root = new Group(modelGroup);
            Scene scene = new Scene(root, 800, 600, true);
            scene.setFill(new RadialGradient(0, 0, 0.5, 0.5, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                    new Stop(0, Color.DARKSLATEGRAY), new Stop(1, Color.BLACK)));  // Arrière-plan dégradé
            PerspectiveCamera camera = new PerspectiveCamera(true);
            camera.setTranslateZ(-500);
            camera.setTranslateY(-50);
            camera.setNearClip(0.1);
            camera.setFarClip(2000.0);
            camera.setFieldOfView(30);
            scene.setCamera(camera);

            // Calculer la position de la liaison
            updateBondLine();

            // Effet d'animation pour le mouvement fluide
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), hydrogenMesh);
            transition.setInterpolator(Interpolator.EASE_BOTH);

            // Gestion des événements de souris pour la rotation
            scene.setOnMousePressed((MouseEvent event) -> {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = rotateX.getAngle();
                anchorAngleY = rotateY.getAngle();
            });

            scene.setOnMouseDragged((MouseEvent event) -> {
                double dx = (anchorY - event.getSceneY()) * 0.5;
                double dy = (anchorX - event.getSceneX()) * 0.5;

                rotateX.setAngle(anchorAngleX - dx);
                rotateY.setAngle(anchorAngleY + dy);
            });

            // Déplacement des objets avec animation
            scene.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 1) {
                    // Animation fluide : Déplacer l'hydrogène vers le chlore
                    double dx = chlorineMesh.getTranslateX() - hydrogenMesh.getTranslateX();
                    double dy = chlorineMesh.getTranslateY() - hydrogenMesh.getTranslateY();
                    double dz = chlorineMesh.getTranslateZ() - hydrogenMesh.getTranslateZ();

                    transition.setToX(hydrogenMesh.getTranslateX() + dx * 0.1);
                    transition.setToY(hydrogenMesh.getTranslateY() + dy * 0.1);
                    transition.setToZ(hydrogenMesh.getTranslateZ() + dz * 0.1);
                    transition.play();
                }

                // Recalculer la ligne de liaison
                updateBondLine();
            });

            primaryStage.setTitle("Visualiseur de Liaison Moléculaire");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des modèles OBJ : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour la ligne de liaison
    private void updateBondLine() {
        if (hydrogenMesh != null && chlorineMesh != null) {
            double x1 = hydrogenMesh.getTranslateX();
            double y1 = hydrogenMesh.getTranslateY();
            double z1 = hydrogenMesh.getTranslateZ();

            double x2 = chlorineMesh.getTranslateX();
            double y2 = chlorineMesh.getTranslateY();
            double z2 = chlorineMesh.getTranslateZ();

            bondLine.setStartX(x1);
            bondLine.setStartY(y1);
            bondLine.setStartZ(z1);

            bondLine.setEndX(x2);
            bondLine.setEndY(y2);
            bondLine.setEndZ(z2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
