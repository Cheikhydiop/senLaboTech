package org.example.labo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import org.fxyz3d.importers.Model3D;
import org.fxyz3d.importers.obj.ObjImporter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SvtController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(SvtController.class.getName());
    private static final double ROTATION_FACTOR = 0.5;
    private static final double MODEL_SCALE = 50.0;

    @FXML
    private Button actionButton;

    @FXML
    private VBox cardContainer;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTpCards();
        initialize3DModel();
    }

    private void initializeTpCards() {
        addTpCard("TP 1: Introduction à la SVT", "Description du TP 1");
        addTpCard("TP 2: Concepts avancés", "Description du TP 2");
        addTpCard("TP 3: Révision finale", "Description du TP 3");
    }

    private void initialize3DModel() {
        try {
            Model3D model3D = loadModel();
            Map<String, PhongMaterial> materials = loadMaterials("/molecule.mtl");
            Group modelGroup = createModelGroup(model3D, materials);
            setupMouseControls(modelGroup);
            cardContainer.getChildren().add(modelGroup);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement du modèle 3D", e);
        }
    }

    private Model3D loadModel() throws Exception {
        ObjImporter importer = new ObjImporter();
        Model3D model3D = importer.load(Objects.requireNonNull(getClass().getResource("/molecule.obj")));

        if (model3D == null) {
            throw new IllegalStateException("Aucun mesh trouvé dans le fichier OBJ");
        }

        return model3D;
    }

    private Group createModelGroup(Model3D model3D, Map<String, PhongMaterial> materials) {
        Group modelGroup = new Group();
        modelGroup.getTransforms().addAll(rotateX, rotateY);

        for (Node node : model3D.getMeshViews()) {
            if (node instanceof MeshView) {
                setupMeshView((MeshView) node, materials, modelGroup);
            }
        }

        return modelGroup;
    }

    private void setupMeshView(MeshView meshView, Map<String, PhongMaterial> materials, Group modelGroup) {
        String materialName = meshView.getId();
        PhongMaterial material = materials.get(materialName);

        if (material != null) {
            meshView.setMaterial(material);
        }

        meshView.setScaleX(MODEL_SCALE);
        meshView.setScaleY(MODEL_SCALE);
        meshView.setScaleZ(MODEL_SCALE);
        modelGroup.getChildren().add(meshView);
    }

    private void setupMouseControls(Group modelGroup) {
        modelGroup.setOnMousePressed(this::handleMousePressed);
        modelGroup.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleMousePressed(MouseEvent event) {
        anchorX = event.getSceneX();
        anchorY = event.getSceneY();
        anchorAngleX = rotateX.getAngle();
        anchorAngleY = rotateY.getAngle();
    }

    private void handleMouseDragged(MouseEvent event) {
        double dx = (anchorY - event.getSceneY()) * ROTATION_FACTOR;
        double dy = (anchorX - event.getSceneX()) * ROTATION_FACTOR;

        rotateX.setAngle(anchorAngleX - dx);
        rotateY.setAngle(anchorAngleY + dy);
    }

    @FXML
    void handleAction(ActionEvent event) {
        LOGGER.info("Action button clicked");
    }

    @FXML
    void btnSVT(ActionEvent event) {
        LOGGER.info("SVT button clicked");
    }

    private void addTpCard(String title, String description) {
        VBox card = createCardContainer();
        Label titleLabel = createTitleLabel(title);
        Label descLabel = new Label(description);

        card.getChildren().addAll(titleLabel, descLabel);
        cardContainer.getChildren().add(card);
    }

    private VBox createCardContainer() {
        VBox card = new VBox(10);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 15px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);" +
                        "-fx-background-radius: 5;"
        );
        card.setPrefWidth(300);
        return card;
    }

    private Label createTitleLabel(String title) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return titleLabel;
    }

    private Map<String, PhongMaterial> loadMaterials(String mtlFilePath) {
        Map<String, PhongMaterial> materials = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(mtlFilePath))))) {
            processMaterialFile(reader, materials);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement des matériaux", e);
        }

        return materials;
    }

    private void processMaterialFile(BufferedReader reader, Map<String, PhongMaterial> materials) throws Exception {
        String line;
        PhongMaterial currentMaterial = null;
        String materialName = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("newmtl")) {
                materialName = line.split(" ")[1];
                currentMaterial = new PhongMaterial();
                materials.put(materialName, currentMaterial);
            } else if (currentMaterial != null) {
                processMaterialProperties(line, currentMaterial);
            }
        }
    }

    private void processMaterialProperties(String line, PhongMaterial material) {
        if (line.startsWith("Kd")) {
            setDiffuseColor(line, material);
        } else if (line.startsWith("Ks")) {
            setSpecularColor(line, material);
        }
    }

    private void setDiffuseColor(String line, PhongMaterial material) {
        String[] parts = line.split(" ");
        material.setDiffuseColor(createColor(parts));
    }

    private void setSpecularColor(String line, PhongMaterial material) {
        String[] parts = line.split(" ");
        material.setSpecularColor(createColor(parts));
    }

    private Color createColor(String[] parts) {
        return Color.color(
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3])
        );
    }
}