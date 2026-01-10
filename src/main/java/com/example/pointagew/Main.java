package com.example.pointagew;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // --- Charger les polices ---
        Font.loadFont(getClass().getResourceAsStream("/com/example/pointagew/fonts/Helvetica.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/com/example/pointagew/fonts/Helvetica-Bold.ttf"), 12);

        // --- Root unique et scène ---
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1000, 650);

        // ======================
        // PAGE ACCUEIL
        // ======================
        Label accueilLabel = new Label("Bienvenue !");
        Button goToPointage = new Button("Suivant");
        VBox accueilPage = new VBox(20, accueilLabel, goToPointage);
        accueilPage.setAlignment(Pos.CENTER);

        // ======================
        // PAGE POINTAGE (ID)
        // ======================
        Label pointageLabel = new Label("Entrez votre ID");
        pointageLabel.getStyleClass().add("pointageLabel");
        TextField idInput = new TextField();
        idInput.setPromptText("ex: 123456789");
        Button confirmerID = new Button("Confirmer");
        Button retourAccueil = new Button("Retour à l'accueil");
        VBox pointagePage = new VBox(12, pointageLabel, idInput, confirmerID, retourAccueil);
        pointagePage.setAlignment(Pos.CENTER);

        // ======================
        // PAGE MOTIF
        // ======================
        Button matin = new Button("Matin");
        matin.getStyleClass().add("matinButton");
        Button apresMidi = new Button("Après-midi");
        Button repas = new Button("Repas");
        Button retourPointage = new Button("Retour");
        Button confirmerMotif = new Button("Confirmer");

        // Choix repas
        HBox repasChoice = new HBox(10);
        ToggleButton oui = new ToggleButton("Oui");
        ToggleButton non = new ToggleButton("Non");
        repasChoice.getChildren().addAll(oui, non);
        repasChoice.setAlignment(Pos.CENTER);
        repasChoice.setVisible(false);

        ToggleGroup group = new ToggleGroup();
        oui.setToggleGroup(group);
        non.setToggleGroup(group);

        VBox motifPage = new VBox(12, matin, apresMidi, repas, repasChoice, retourPointage, confirmerMotif);
        motifPage.setAlignment(Pos.CENTER);

        // ======================
        // PAGE MERCI
        // ======================
        Label merciLabel = new Label("Merci !");
        VBox merciPage = new VBox(20, merciLabel);
        merciPage.setAlignment(Pos.CENTER);

        // ======================
        // ACTIONS BOUTONS
        // ======================
        goToPointage.setOnAction(e -> switchPage(scene, root, pointagePage, "/com/example/pointagew/accueil.css"));
        confirmerID.setOnAction(e -> switchPage(scene, root, motifPage, "/com/example/pointagew/accueil.css"));
        retourAccueil.setOnAction(e -> switchPage(scene, root, accueilPage, "/com/example/pointagew/accueil.css"));

        retourPointage.setOnAction(e -> switchPage(scene, root, pointagePage, "/com/example/pointagew/accueil.css"));
        confirmerMotif.setOnAction(e -> switchPage(scene, root, merciPage, "/com/example/pointagew/accueil.css"));

        matin.setOnAction(e -> System.out.println("Matin sélectionné"));
        apresMidi.setOnAction(e -> System.out.println("Après-midi sélectionné"));
        repas.setOnAction(e -> repasChoice.setVisible(!repasChoice.isVisible()));
        oui.setOnAction(e -> System.out.println("Repas: Oui"));
        non.setOnAction(e -> System.out.println("Repas: Non"));

        // ======================
        // AFFICHER PAGE ACCUEIL PAR DÉFAUT
        // ======================
        switchPage(scene, root, accueilPage, "/com/example/pointagew/accueil.css");

        // --- Stage ---
        stage.setTitle("Pointage App");
        stage.setScene(scene);
        stage.show();
    }

    // --- Méthode pour changer de page et appliquer le CSS ---
    private void switchPage(Scene scene, VBox root, VBox page, String cssPath) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
        root.getChildren().setAll(page);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
