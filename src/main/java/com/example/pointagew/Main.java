package com.example.pointagew;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.lang.classfile.Signature;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // --- Charger les polices ---
        Font.loadFont(getClass().getResourceAsStream("/com/example/pointagew/fonts/Helvetica.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/com/example/pointagew/fonts/Helvetica-Bold.ttf"), 12);

        // --- Scene unique ---
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1000, 650);

        // --- PAGE ACCUEIL ---
        Label accueilLabel = new Label("Bienvenue !");
        Button goToPointage = new Button("Suivant");

        VBox accueilPage = new VBox(12, accueilLabel, goToPointage);
        accueilPage.setAlignment(Pos.CENTER);

        // --- Saisie de l'id ---
        Label titre = new Label("Saisie");
        TextArea zText = new TextArea("ex: 123456789");
        Button confirmer = new Button("Confirmer");
        Button accueil = new Button("accueil");

        VBox pointagePage = new VBox(12, titre, zText, confirmer, accueil);
        pointagePage.setAlignment(Pos.CENTER);

        // --- PAGE Motif ---
        Label pointageLabel = new Label("Page Motif");
        Button matin = new Button("Matin");
        Button apresMidi = new Button("Après-midi");
        Button repas = new Button("Repas");
        Button retour = new Button("Retour");
        Button confirmer1 = new Button("Confirmer");

        HBox repasChoice = new HBox(10);
        Button oui = new Button("Oui");
        Button non = new Button("Non");
        repasChoice.getChildren().addAll(oui, non);
        repasChoice.setAlignment(Pos.CENTER);
        repasChoice.setVisible(false);



        VBox motifPage = new VBox(12, pointageLabel, matin, apresMidi, repas, repasChoice, retour,confirmer1);
        motifPage.setAlignment(Pos.CENTER);

        // --- Merci --
        Label titreM = new Label("Merci ");

        VBox merciPage = new VBox(12,titreM);
        merciPage.setAlignment(Pos.CENTER);

        // --- Navigation ---
        goToPointage.setOnAction(e -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/com/example/pointagew/pointage.css")).toExternalForm());
            root.getChildren().setAll(pointagePage);
        });

        confirmer.setOnAction(e -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/com/example/pointagew/pointage.css")).toExternalForm());
            root.getChildren().setAll(motifPage);
        });

        confirmer1.setOnAction(e -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/com/example/pointagew/pointage.css")).toExternalForm());
            root.getChildren().setAll(merciPage);
        });

        retour.setOnAction(e -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/com/example/pointagew/accueil.css")).toExternalForm());
            root.getChildren().setAll(accueilPage);
        });

        accueil.setOnAction(retour.getOnAction());

        matin.setOnAction(e -> System.out.println("Matin sélectionné"));
        apresMidi.setOnAction(e -> System.out.println("Après-midi sélectionné"));

        repas.setOnAction(e -> repasChoice.setVisible(!repasChoice.isVisible()));
        oui.setOnAction(e -> System.out.println("Repas: Oui"));
        non.setOnAction(e -> System.out.println("Repas: Non"));

        // --- Afficher page accueil ---
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/example/pointagew/accueil.css")).toExternalForm());
        root.getChildren().setAll(accueilPage);

        // --- Stage ---
        stage.setTitle("Pointage App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
