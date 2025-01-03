package org.example.labo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author user
 */
public class Page2Controller implements Initializable {

    private static final int W = 600;
    private static final int E = 600;
    public final Sphere sphere = new Sphere(100);
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnTerre;
     @FXML
    private Button back;
     
       @FXML
    void back(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("acc.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("button clicker");
    }

    @FXML
    void terre(ActionEvent event) {

        Stage primaryStage = new Stage();
        PointLight point = new PointLight();
        point.setColor(Color.YELLOW);
        Sphere sphere2 = new Sphere(50);
        sphere2.getTransforms().setAll(point.getTransforms());
        PhongMaterial material2 = new PhongMaterial();
        material2.setDiffuseMap(new Image(getClass().getResourceAsStream("soleil.jpg")));
        sphere2.setMaterial(material2);
        sphere2.setTranslateX(100);
        sphere2.setTranslateY(-100);
        sphere2.setTranslateZ(-500);

        smartObject group = new smartObject();

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("terre.jpg")));
        sphere.setMaterial(material);
        Rotate rotation = new Rotate();
        sphere.setTranslateX(35);
        sphere.setTranslateY(20);
        sphere.setTranslateZ(0);
        rotation.setPivotX(200);
        rotation.setPivotY(200);
        sphere.getTransforms().add(rotation);

        rotation.setPivotX(200);
        rotation.setPivotY(200);

        rotation.setAngle(30);

        rotation.setAxis(Rotate.Y_AXIS);
        sphere.getTransforms().add(rotation);
//       xRotate.angleProperty().bind(angleX);
//        yRotate.angleProperty().bind(angleY);
        group.getChildren().addAll(sphere2);
        group.getChildren().addAll(sphere);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(5), new KeyValue(rotation.angleProperty(), 360))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        sphere.setRotationAxis(Rotate.Y_AXIS);
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                ;
//                sphere.rotateProperty().set(sphere.getRotate() + 1);
//
//            }
//        }; 

        Scene scene = new Scene(group, 1300, 700);
        scene.setFill(Color.BLACK);
        Camera camera = new PerspectiveCamera(true);

        scene.setCamera(camera);

        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-200);

        camera.setNearClip(1);
        camera.setFarClip(6000);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> {
            switch (event1.getCode()) {
                case A:
                    camera.translateZProperty().set(camera.getTranslateZ() + 140);
                    break;
                case B:
                    camera.translateZProperty().set(camera.getTranslateZ() - 140);
                    break;

            }
        });

//        timer.start();
        primaryStage.setTitle("Planet");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
