package com.example.pointagew;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label bonjour = new Label("Bonjour");
        Button suivant = new Button("Suivant");

        VBox root = new VBox(12, bonjour, suivant);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1000, 650);

        URL cssUrl = getClass().getResource("app.css");
        assert cssUrl != null;
        scene.getStylesheets().add(cssUrl.toExternalForm());

        // Action du bouton (pour l’instant un test)
        suivant.setOnAction(e -> System.out.println("Suivant cliqué")); // evenement bouton

        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show(); // afficher stage + scene
    }

    public static void main(String[] args) {
        launch(args);
    }
}
