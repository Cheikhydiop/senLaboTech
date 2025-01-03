package org.example.labo;//package org.example.demo1;

//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.PhongMaterial;
//import javafx.scene.shape.MeshView;
//import javafx.scene.transform.Rotate;
//import javafx.stage.Stage;
//import javafx.scene.PerspectiveCamera;
//import org.fxyz3d.importers.Model3D;
//import org.fxyz3d.importers.obj.ObjImporter;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class HelloApplication extends Application {
//    private double anchorX, anchorY;
//    private double anchorAngleX = 0;
//    private double anchorAngleY = 0;
//    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
//    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
//
//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            // Charger le modèle 3D à partir du fichier .obj
//            ObjImporter importer = new ObjImporter();
//            Model3D model3D = importer.load(Objects.requireNonNull(getClass().getResource("/molecule.obj")));
//
//            if (model3D == null) {
//                throw new RuntimeException("Aucun mesh trouvé dans le fichier OBJ");
//            }
//
//            // Charger les matériaux à partir du fichier .mtl
//            Map<String, PhongMaterial> materials = loadMaterials("/molecule.mtl");
//
//            Group modelGroup = new Group();
//
//            // Appliquer les transformations de rotation au groupe
//            modelGroup.getTransforms().addAll(rotateX, rotateY);
//
//            // Assigner les matériaux aux MeshView des objets
//            for (Node node : model3D.getMeshViews()) {
//                if (node instanceof MeshView) {
//                    MeshView meshView = (MeshView) node;
//                    String materialName = meshView.getId(); // Utiliser l'ID du MeshView pour déterminer le matériau
//                    PhongMaterial material = materials.get(materialName);
//                    if (material != null) {
//                        meshView.setMaterial(material);
//                    }
//                    meshView.setScaleX(50);
//                    meshView.setScaleY(50);
//                    meshView.setScaleZ(50);
//                    modelGroup.getChildren().add(meshView);
//                }
//            }
//
//            // Configuration de la scène
//            Group root = new Group(modelGroup);
//            Scene scene = new Scene(root, 800, 600, true);
//            scene.setFill(Color.LIGHTGRAY);
//
//            // Configuration de la caméra
//            PerspectiveCamera camera = new PerspectiveCamera(true);
//            camera.setTranslateZ(-500);
//            camera.setTranslateY(-50);
//            camera.setNearClip(0.1);
//            camera.setFarClip(2000.0);
//            camera.setFieldOfView(30);
//            scene.setCamera(camera);
//
//            // Gestion des événements de souris pour la rotation de l'objet
//            scene.setOnMousePressed((MouseEvent event) -> {
//                anchorX = event.getSceneX();
//                anchorY = event.getSceneY();
//                anchorAngleX = rotateX.getAngle();
//                anchorAngleY = rotateY.getAngle();
//            });
//
//            scene.setOnMouseDragged((MouseEvent event) -> {
//                double dx = (anchorY - event.getSceneY()) * 0.5;
//                double dy = (anchorX - event.getSceneX()) * 0.5;
//
//                rotateX.setAngle(anchorAngleX - dx);
//                rotateY.setAngle(anchorAngleY + dy);
//            });
//
//            primaryStage.setTitle("Visualiseur de modèle 3D - Glissez pour orienter");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//
//        } catch (Exception e) {
//            System.err.println("Erreur lors du chargement du modèle OBJ : " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    // Méthode pour charger les matériaux à partir du fichier .mtl
//    private Map<String, PhongMaterial> loadMaterials(String mtlFilePath) {
//        Map<String, PhongMaterial> materials = new HashMap<>();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(mtlFilePath)))) {
//            String line;
//            PhongMaterial currentMaterial = null;
//            String materialName = null;
//
//            while ((line = reader.readLine()) != null) {
//                line = line.trim();
//
//                if (line.startsWith("newmtl")) {
//                    materialName = line.split(" ")[1];
//                    currentMaterial = new PhongMaterial();
//                    materials.put(materialName, currentMaterial);
//                } else if (line.startsWith("Kd")) {
//                    String[] parts = line.split(" ");
//                    if (currentMaterial != null) {
//                        currentMaterial.setDiffuseColor(Color.color(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
//                    }
//                } else if (line.startsWith("Ks")) {
//                    String[] parts = line.split(" ");
//                    if (currentMaterial != null) {
//                        currentMaterial.setSpecularColor(Color.color(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
//                    }
//                } else if (line.startsWith("Ka")) {
//                    // Gérer la couleur ambiante si nécessaire
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return materials;
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.labo.ui.Card;
import org.example.labo.ui.SideMenu;
import org.example.labo.ui.TopBar;
import org.example.labo.utils.AudioManager;
import org.example.labo.utils.Object3DManager;
import org.example.labo.utils.VideoManager;

public class HelloApplication extends Application {
    private BorderPane root;
    private AudioManager audioManager;
    private Object3DManager object3DManager;
    private VideoManager videoManager;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f2f5;");



        // Initialize managers
        audioManager = new AudioManager();
        object3DManager = new Object3DManager();
        videoManager = new VideoManager(this, audioManager);

        TopBar topBar = new TopBar();
        root.setTop(topBar);

        SideMenu sideMenu = new SideMenu(this);
        root.setLeft(sideMenu);

        VBox emptyContent = new VBox();
        emptyContent.setStyle("-fx-background-color: white;");


        root.setCenter(emptyContent);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Simulation TP - Chimie & SVT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public BorderPane getRoot() {
        return root;
    }

    public void handleMenuButtonClick(String buttonId) {
        GridPane content = new GridPane();
        content.setHgap(20);
        content.setVgap(20);
        content.setPadding(new Insets(20));

        String prefix = buttonId.equals("chimie") ? "Chimie" : "SVT";

        VBox simulation3DCard = new Card("Simulation 3D " + prefix, "Explorez les concepts en 3D", "Lancer la simulation");
        VBox coursCard = new Card("Cours " + prefix, "Accédez aux supports de cours", "Voir les cours");
        VBox quizCard = new Card("Quiz " + prefix, "Testez vos connaissances", "Commencer le quiz");
        VBox videoCard = new Card("Vidéo démonstration " + prefix, "Regardez les manipulations en vidéo", "Voir les vidéos");

        content.add(simulation3DCard, 0, 0);
        content.add(coursCard, 1, 0);
        content.add(quizCard, 0, 1);
        content.add(videoCard, 1, 1);

        Button simButton = (Button) simulation3DCard.getChildren().get(2);
        simButton.setOnAction(e -> showObjectsList(prefix));

        Button videoButton = (Button) videoCard.getChildren().get(2);
        videoButton.setOnAction(e -> showVideosList(prefix));

        Button quizButton = (Button) quizCard.getChildren().get(2);
        quizButton.setOnAction(e -> showQuizList(prefix));

        Button courButton = (Button) coursCard.getChildren().get(2);
        courButton.setOnAction(e -> showCourseList(prefix));


        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        root.setCenter(scrollPane);
    }

    private void showObjectsList(String subject) {
        VBox simulationContent = new VBox(20);
        simulationContent.setPadding(new Insets(20));
        simulationContent.setStyle("-fx-background-color: white;");

        // Titre
        Label title = new Label("Objets 3D disponibles - " + subject);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Label d'instructions
        Label instructionLabel = new Label("Sélectionnez un objet 3D à visualiser :");
        instructionLabel.setFont(Font.font("Arial", 14));

        // Liste des objets
        ObservableList<String> objFiles;
        if (subject.equals("Chimie")) {
            objFiles = FXCollections.observableArrayList(
                    "nonane.obj",
                    "ethane.obj",
                    "H2O.obj",
                    "liaison_covalente.obj"
            );
        } else {
            objFiles = FXCollections.observableArrayList(
                    "cellule_vegetale.obj",
                    "adn_structure.obj",
                    "neurone.obj",
                    "coeur_humain.obj"
            );
        }

        ListView<String> objectList = new ListView<>(objFiles);
        objectList.setPrefHeight(300);
        objectList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.replace(".obj", "").replace("_", " "));
                }
            }
        });

        // Boîte de prévisualisation
        VBox previewBox = new VBox(10);
        previewBox.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-border-color: #dee2e6; " +
                        "-fx-border-radius: 5; " +
                        "-fx-padding: 10;"
        );
        previewBox.setPrefHeight(200);
        Label previewLabel = new Label("Aperçu de l'objet 3D");
        previewLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        previewBox.getChildren().add(previewLabel);

        // Boîte de contrôles
        HBox controlsBox = new HBox(10);
        controlsBox.setAlignment(Pos.CENTER);
        Button animateButton = new Button("Animer");
        Button audioButton = new Button("Écouter l'audio");
        Button returnButton = new Button("Retour");

        // Configuration initiale des boutons
        animateButton.setDisable(true);
        audioButton.setDisable(true);

        // Style des boutons
        String buttonStyle = """
            -fx-background-color: %s;
            -fx-text-fill: white;
            -fx-padding: 8 15;
            -fx-background-radius: 5;
            """;

        animateButton.setStyle(String.format(buttonStyle, "#3498db"));
        audioButton.setStyle(String.format(buttonStyle, "#2ecc71"));
        returnButton.setStyle(String.format(buttonStyle, "#95a5a6"));

        controlsBox.getChildren().addAll(animateButton, audioButton, returnButton);

        // Labels pour l'équilibrage et la description
        Label equilibrageLabel = new Label();
        equilibrageLabel.setWrapText(true);
        equilibrageLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");

        Label descriptionLabel = new Label();
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14;");

        // Conteneur pour les informations
        VBox infoBox = new VBox(10);
        infoBox.getChildren().addAll(equilibrageLabel, descriptionLabel);
        infoBox.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-color: #dee2e6; -fx-border-radius: 5;");

        // Gestionnaire de sélection
        objectList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                animateButton.setDisable(false);
                audioButton.setDisable(false);
                previewLabel.setText("Aperçu : " + newVal.replace(".obj", "").replace("_", " "));
                equilibrageLabel.setText(getEquilibrageText(newVal));
                descriptionLabel.setText(getDescriptionText(newVal));
            }
        });

        // Gestionnaires d'événements des boutons
        animateButton.setOnAction(e -> {
            String selectedObj = objectList.getSelectionModel().getSelectedItem();
            if (selectedObj != null) {
                object3DManager.animateObject(selectedObj);
            }
        });

        audioButton.setOnAction(e -> {
            String selectedObj = objectList.getSelectionModel().getSelectedItem();
            if (selectedObj != null) {
                audioManager.playAudio(selectedObj);
            }
        });

        returnButton.setOnAction(e -> handleMenuButtonClick(subject.toLowerCase()));

        // Assemblage des éléments
        simulationContent.getChildren().addAll(
                title,
                instructionLabel,
                objectList,
                previewBox,
                infoBox,
                controlsBox
        );

        // ScrollPane pour le défilement
        ScrollPane scrollPane = new ScrollPane(simulationContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        root.setCenter(scrollPane);
    }

    // Méthodes d'aide pour le texte
    private String getEquilibrageText(String objFiles) {
        return switch (objFiles.replaceAll(".obj", "").toLowerCase()) {
            case "h2o" -> "Équilibrage : 2 H₂ + O₂ → 2 H₂O\n" +
                    "Masse molaire : H = 1 g/mol, O = 16 g/mol\n" +
                    "Masse molaire H₂O = 18 g/mol";

            case "ethane" -> "Équilibrage : 2 C + 3 H₂ → C₂H₆\n" +
                    "Masse molaire : C = 12 g/mol, H = 1 g/mol\n" +
                    "Masse molaire C₂H₆ = 30 g/mol";

            case "nonane" -> "Équilibrage : 9 C + 10 H₂ → C₉H₂₀\n" +
                    "Masse molaire : C = 12 g/mol, H = 1 g/mol\n" +
                    "Masse molaire C₉H₂₀ = 128 g/mol";

            case "liaison_covalente" -> "Équilibrage : H₂ → 2 H\n" +
                    "Énergie de liaison : 436 kJ/mol\n" +
                    "Distance interatomique : 74 pm";

            default -> "Équation non disponible";
        };
    }

    private String getDescriptionText(String objFiles) {
        return switch (objFiles.replaceAll(".obj", "").toLowerCase()) {
            case "h2o" -> "• Molécule d'eau\n" +
                    "• État naturel : liquide entre 0°C et 100°C\n" +
                    "• Géométrie : Coudée (angle 104,5°)\n" +
                    "• Polarité : Molécule polaire\n" +
                    "• Rôle : Solvant universel, thermorégulation";

            case "ethane" -> "• Hydrocarbure saturé (alcane)\n" +
                    "• État naturel : Gaz incolore\n" +
                    "• Géométrie : Tétraédrique\n" +
                    "• Point d'ébullition : -88,6°C\n" +
                    "• Applications : Pétrochimie, combustible";

            case "nonane" -> "• Alcane linéaire\n" +
                    "• État naturel : Liquide incolore\n" +
                    "• Point d'ébullition : 150,8°C\n" +
                    "• Densité : 0,718 g/cm³\n" +
                    "• Applications : Carburant, solvant";

            case "liaison_covalente" -> "• Type de liaison chimique\n" +
                    "• Mise en commun d'électrons\n" +
                    "• Force de liaison : Forte\n" +
                    "• Exemple : H₂, O₂, N₂\n" +
                    "• Géométrie : Dépend des atomes";

            default -> "Description non disponible";
        };
    }






    private void showVideosList(String subject) {
        VBox videoContent = new VBox(20);
        videoContent.setPadding(new Insets(20));
        videoContent.setStyle("-fx-background-color: white;");

        Label title = new Label("Vidéos disponibles - " + subject);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ObservableList<String> videoFiles;
        if (subject.equals("Chimie")) {
            videoFiles = FXCollections.observableArrayList(
                    "nonane.mp4",
                    "ethane.mp4",
                    "H20.mp4",
                    "liaison_covalente.mp4"
            );
        } else {
            videoFiles = FXCollections.observableArrayList(
                    "cellule_vegetale.mp4",
                    "adn_structure.mp4",
                    "neurone.mp4",
                    "coeur_humain.mp4"
            );
        }

        ListView<String> videoList = new ListView<>(videoFiles);
        videoList.setPrefHeight(300);

        VBox previewBox = new VBox(10);
        previewBox.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-border-color: #dee2e6; " +
                        "-fx-border-radius: 5; " +
                        "-fx-padding: 10;"
        );
        previewBox.setPrefHeight(200);
        Label previewLabel = new Label("Aperçu de la vidéo");
        previewLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        previewBox.getChildren().add(previewLabel);

        HBox controlsBox = new HBox(10);
        Button playButton = new Button("Visionner");
        Button returnButton = new Button("Retour");

        playButton.setDisable(true);

        playButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        returnButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");

        controlsBox.getChildren().addAll(playButton, returnButton);

        videoList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                playButton.setDisable(false);
                previewLabel.setText("Aperçu de : " + newVal);
            }
        });

        playButton.setOnAction(e -> {
            String selectedVideo = videoList.getSelectionModel().getSelectedItem();
            if (selectedVideo != null) {
                videoManager.playVideo(selectedVideo);
            }
        });

        returnButton.setOnAction(e -> handleMenuButtonClick(subject.toLowerCase()));

        videoContent.getChildren().addAll(
                title,
                new Label("Sélectionnez une vidéo à visionner :"),
                videoList,
                previewBox,
                controlsBox
        );

        ScrollPane scrollPane = new ScrollPane(videoContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        root.setCenter(scrollPane);
    }

    public void showQuizList(String subject) {
        VBox quizContent = new VBox(20);
        quizContent.setPadding(new Insets(20));
        quizContent.setStyle("-fx-background-color: white;");

        Label title = new Label("Quiz disponibles - " + subject);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ObservableList<String> quizzes;
        if (subject.equals("Chimie")) {
            quizzes = FXCollections.observableArrayList(
                    "Les liaisons chimiques",
                    "Structure moléculaire",
                    "Réactions organiques",
                    "États de la matière"
            );
        } else {
            quizzes = FXCollections.observableArrayList(
                    "Structure cellulaire",
                    "Génétique fondamentale",
                    "Système nerveux",
                    "Anatomie humaine"
            );
        }

        ListView<String> quizList = new ListView<>(quizzes);
        quizList.setPrefHeight(300);

        VBox descriptionBox = new VBox(10);
        descriptionBox.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-border-color: #dee2e6; " +
                        "-fx-border-radius: 5; " +
                        "-fx-padding: 10;"
        );
        descriptionBox.setPrefHeight(150);

        Label descriptionLabel = new Label("Description du quiz");
        descriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label difficultyLabel = new Label("Difficulté: -");
        Label questionsLabel = new Label("Nombre de questions: -");
        Label timeLabel = new Label("Temps estimé: -");

        descriptionBox.getChildren().addAll(
                descriptionLabel,
                difficultyLabel,
                questionsLabel,
                timeLabel
        );
        HBox controlsBox = new HBox(10);
        Button startButton = new Button("Commencer");
        Button returnButton = new Button("Retour");

        startButton.setDisable(true);
        startButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        returnButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");

        controlsBox.getChildren().addAll(startButton, returnButton);

        quizList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                startButton.setDisable(false);
                updateQuizDescription(newVal, difficultyLabel, questionsLabel, timeLabel);
            }
        });

        startButton.setOnAction(e -> {
            String selectedQuiz = quizList.getSelectionModel().getSelectedItem();
            if (selectedQuiz != null) {
                startQuiz(selectedQuiz, subject);
            }
        });

        returnButton.setOnAction(e -> handleMenuButtonClick(subject.toLowerCase()));

        quizContent.getChildren().addAll(
                title,
                new Label("Sélectionnez un quiz à passer :"),
                quizList,
                descriptionBox,
                controlsBox
        );

        ScrollPane scrollPane = new ScrollPane(quizContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        root.setCenter(scrollPane);
    }

    private void updateQuizDescription(String quizName, Label difficultyLabel, Label questionsLabel, Label timeLabel) {
        // Simulate getting quiz metadata
        String difficulty = switch (quizName) {
            case "Les liaisons chimiques", "Structure cellulaire" -> "Débutant";
            case "Structure moléculaire", "Génétique fondamentale" -> "Intermédiaire";
            default -> "Avancé";
        };

        int questions = switch (quizName) {
            case "Les liaisons chimiques", "Structure cellulaire" -> 10;
            case "Structure moléculaire", "Génétique fondamentale" -> 15;
            default -> 20;
        };

        int time = questions * 2; // 2 minutes per question

        difficultyLabel.setText("Difficulté: " + difficulty);
        questionsLabel.setText("Nombre de questions: " + questions);
        timeLabel.setText("Temps estimé: " + time + " minutes");
    }

    private void startQuiz(String quizName, String subject) {
        VBox quizContainer = new VBox(20);
        quizContainer.setPadding(new Insets(20));
        quizContainer.setStyle("-fx-background-color: white;");

        Label title = new Label("Quiz: " + quizName);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(500);

        VBox questionBox = new VBox(10);
        Label questionLabel = new Label("Question 1: Quelle est la réponse?");
        questionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox answersBox = new VBox(5);
        ToggleGroup answersGroup = new ToggleGroup();
        for (int i = 1; i <= 4; i++) {
            RadioButton option = new RadioButton("Option " + i);
            option.setToggleGroup(answersGroup);
            answersBox.getChildren().add(option);
        }

        HBox controlsBox = new HBox(10);
        Button previousButton = new Button("Précédent");
        Button nextButton = new Button("Suivant");
        Button finishButton = new Button("Terminer");

        previousButton.setDisable(true);
        finishButton.setDisable(true);

        controlsBox.getChildren().addAll(previousButton, nextButton, finishButton);

        quizContainer.getChildren().addAll(
                title,
                progressBar,
                questionBox,
                questionLabel,
                answersBox,
                controlsBox
        );

        ScrollPane scrollPane = new ScrollPane(quizContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        root.setCenter(scrollPane);
    }

    public void showCourseList(String subject) {
        VBox courseContent = new VBox(20);
        courseContent.setPadding(new Insets(20));
        courseContent.setStyle("-fx-background-color: white;");

        Label title = new Label("Cours disponibles - " + subject);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ObservableList<String> courses;
        if (subject.equals("Chimie")) {
            courses = FXCollections.observableArrayList(
                    "Introduction à la chimie organique",
                    "Les liaisons chimiques fondamentales",
                    "Thermodynamique chimique",
                    "Cinétique des réactions"
            );
        } else {
            courses = FXCollections.observableArrayList(
                    "Biologie cellulaire",
                    "Génétique moléculaire",
                    "Physiologie humaine",
                    "Écologie fondamentale"
            );
        }

        ListView<String> courseList = new ListView<>(courses);
        courseList.setPrefHeight(300);

        VBox descriptionBox = new VBox(10);
        descriptionBox.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-border-color: #dee2e6; " +
                        "-fx-border-radius: 5; " +
                        "-fx-padding: 10;"
        );
        descriptionBox.setPrefHeight(150);

        Label descriptionLabel = new Label("Description du cours");
        descriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label chapterLabel = new Label("Nombre de chapitres: -");
        Label durationLabel = new Label("Durée estimée: -");

        descriptionBox.getChildren().addAll(
                descriptionLabel,
                chapterLabel,
                durationLabel
        );

        HBox controlsBox = new HBox(10);
        Button openButton = new Button("Ouvrir");
        Button downloadButton = new Button("Télécharger PDF");
        Button returnButton = new Button("Retour");

        openButton.setDisable(true);
        downloadButton.setDisable(true);

        openButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        downloadButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        returnButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");

        controlsBox.getChildren().addAll(openButton, downloadButton, returnButton);

        courseList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                openButton.setDisable(false);
                downloadButton.setDisable(false);
                updateCourseDescription(newVal, chapterLabel, durationLabel);
            }
        });

        openButton.setOnAction(e -> {
            String selectedCourse = courseList.getSelectionModel().getSelectedItem();
            if (selectedCourse != null) {
                openCourse(selectedCourse, subject);
            }
        });

        returnButton.setOnAction(e -> handleMenuButtonClick(subject.toLowerCase()));

        courseContent.getChildren().addAll(
                title,
                new Label("Sélectionnez un cours à consulter :"),
                courseList,
                descriptionBox,
                controlsBox
        );

        ScrollPane scrollPane = new ScrollPane(courseContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        root.setCenter(scrollPane);
    }




    private void updateCourseDescription(String courseName, Label chapterLabel, Label durationLabel) {
        // Simulate getting course metadata
        int chapters = switch (courseName) {
            case "Introduction à la chimie organique", "Biologie cellulaire" -> 5;
            case "Les liaisons chimiques fondamentales", "Génétique moléculaire" -> 7;
            default -> 6;
        };

        int duration = chapters * 3; // 3 hours per chapter

        chapterLabel.setText("Nombre de chapitres: " + chapters);
        durationLabel.setText("Durée estimée: " + duration + " heures");
    }

    private void openCourse(String courseName, String subject) {
        VBox courseContainer = new VBox(20);
        courseContainer.setPadding(new Insets(20));
        courseContainer.setStyle("-fx-background-color: white;");

        Label title = new Label(courseName);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TabPane chaptersPane = new TabPane();
        chaptersPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        for (int i = 1; i <= 5; i++) {
            Tab chapterTab = new Tab("Chapitre " + i);
            VBox chapterContent = new VBox(10);
            chapterContent.setPadding(new Insets(10));

            Label chapterTitle = new Label("Titre du chapitre " + i);
            chapterTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            TextArea content = new TextArea("Contenu du chapitre " + i);
            content.setWrapText(true);
            content.setPrefRowCount(10);
            content.setEditable(false);

            chapterContent.getChildren().addAll(chapterTitle, content);
            chapterTab.setContent(chapterContent);
            chaptersPane.getTabs().add(chapterTab);
        }

        Button returnButton = new Button("Retour aux cours");
        returnButton.setOnAction(e -> showCourseList(subject));

        courseContainer.getChildren().addAll(title, chaptersPane, returnButton);

        ScrollPane scrollPane = new ScrollPane(courseContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        root.setCenter(scrollPane);
    }

    @Override
    public void stop() {
        if (audioManager != null) {
            audioManager.stopCurrentAudio();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}