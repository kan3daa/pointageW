package com.example.pointagew;

import com.example.pointagew.database.Excel;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.DirectoryChooser;
import java.io.File;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class Main extends Application {

    // Identité chargée après validation du code
    private Integer currentCode = null;
    private String currentNom = null;
    private String currentPrenom = null;

    @Override
    public void start(Stage stage) {
        com.example.pointagew.database.DbInit.init();

        Font.loadFont(getClass().getResourceAsStream("/com/example/pointagew/fonts/Helvetica.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/com/example/pointagew/fonts/Helvetica-Bold.ttf"), 12);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1000, 650);

        // ======================
        // PAGE ACCUEIL
        // ======================
        Label accueilLabel = new Label("Bienvenue !");
        Button goToPointage = new Button("Suivant");
        Button adminB = new Button("admin");

        VBox accueilPage = new VBox(20, accueilLabel, goToPointage, adminB );
        accueilPage.setAlignment(Pos.CENTER);

        // ======================
        // PAGE POINTAGE (CODE)
        // ======================
        Label pointageLabel = new Label("Entrez votre code (4 chiffres)");
        pointageLabel.getStyleClass().add("pointageLabel");

        TextField codeInput = new TextField();
        codeInput.setPromptText("ex: 1234");

        // chiffres uniquement + max 4 caractères
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (!newText.matches("\\d*")) return null;
            if (newText.length() > 4) return null;
            return change;
        };
        codeInput.setTextFormatter(new TextFormatter<>(filter));

        Button confirmerCode = new Button("Confirmer");
        Button accueilRetour = new Button("Retour");
        VBox pointagePage = new VBox(12, pointageLabel, codeInput, confirmerCode,accueilRetour);
        pointagePage.setAlignment(Pos.CENTER);

        // ======================
        // PAGE MOTIF
        // ======================
        Label bonjourMotifLabel = new Label("");

        ToggleButton matin = new ToggleButton("Matin");
        ToggleButton apresMidi = new ToggleButton("Après-midi");
        ToggleGroup journeeGroup = new ToggleGroup();
        matin.setToggleGroup(journeeGroup);
        apresMidi.setToggleGroup(journeeGroup);

        HBox journeeChoice = new HBox(10, matin, apresMidi);
        journeeChoice.setAlignment(Pos.CENTER);

        Label repas = new Label("Repas");

        ToggleButton oui = new ToggleButton("Oui");
        ToggleButton non = new ToggleButton("Non");
        ToggleGroup repasGroup = new ToggleGroup();
        oui.setToggleGroup(repasGroup);
        non.setToggleGroup(repasGroup);

        HBox repasChoice = new HBox(10, oui, non);
        repasChoice.setAlignment(Pos.CENTER);

        Button retourPointage = new Button("Retour");
        Button confirmerMotif = new Button("Confirmer");

        VBox motifPage = new VBox(
                12,
                bonjourMotifLabel,
                journeeChoice,
                repas,
                repasChoice,
                retourPointage,
                confirmerMotif
        );
        motifPage.setAlignment(Pos.CENTER);

        // ======================
        // PAGE MERCI
        // ======================
        Label merciLabel = new Label("Merci !");
        VBox merciPage = new VBox(10, merciLabel);
        merciPage.setAlignment(Pos.CENTER);

        //======================
        // ADMIN
        //======================
        Label aTitre = new Label("Page admin");
        Label aDescription = new Label("sauvegarder sous exele ");
        Button sauvegarder = new Button("sauvegarder");
        Button aRetourAccueil = new  Button("Retour accueil");
        Label exelEtat = new Label("enregistre");
        exelEtat.setId("exelEtat");
        exelEtat.setVisible(false);

        VBox pageAdmin = new VBox(aTitre,exelEtat,sauvegarder,aRetourAccueil);
        pageAdmin.setAlignment(Pos.CENTER);


        // ======================
        // ACTIONS
        // ======================
        goToPointage.setOnAction(e ->
                switchPage(scene, root, pointagePage, "/com/example/pointagew/accueil.css")
        );
        accueilRetour.setOnAction(e ->{
            switchPage(scene,root, accueilPage, "/com/example/pointagew/accueil.css");
            exelEtat.setVisible(false);
        });
        aRetourAccueil.setOnAction(e -> {
            switchPage(scene, root, accueilPage, "/com/example/pointagew/accueil.css");
            exelEtat.setVisible(false);
        });

        adminB.setOnAction(e->{
            root.getChildren().setAll(pageAdmin);
        });
        sauvegarder.setOnAction(e ->  {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier de pointage");

            // nom du fichier proposé par défaut
            fileChooser.setInitialFileName("pointage.xlsx");

            // filtre extension
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Fichier Excel", "*.xlsx")
            );

            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                Excel excel = new Excel();

                if (excel.creeExcel(file.toString())) {
                    exelEtat.setText("Enregistre");

                    RotateTransition rt = new RotateTransition(Duration.seconds(1), exelEtat);
                    rt.setAxis(Rotate.Y_AXIS);
                    rt.setFromAngle(0);
                    rt.setToAngle(360);
                    rt.setOnFinished(ev -> exelEtat.setRotate(360)); // garde la rotation finale
                    rt.play();
                }
                else{
                    exelEtat.setText("Erreur");
                }
                exelEtat.setVisible(true);
            }
        });
        confirmerCode.setOnAction(e -> {
            // reset identité affichée
            currentCode = null;
            currentNom = null;
            currentPrenom = null;
            bonjourMotifLabel.setText("");


            String codeTxt = codeInput.getText().trim();
            if (codeTxt.length() != 4) {
                showError("Le code doit faire 4 chiffres.");
                return;
            }

            int code = Integer.parseInt(codeTxt);

            String sql = "SELECT nom, prenom FROM benevole WHERE id_benevole = ? AND actif = 1";
            try (var c = com.example.pointagew.database.DataBase.getConnection();
                var ps = c.prepareStatement(sql)) {

                ps.setInt(1, code);

                try (var rs = ps.executeQuery()) { // SELECT => executeQuery [web:9]
                    if (!rs.next()) {
                        showError("Code inconnu. Demande à l’admin de t’enregistrer.");
                        return;
                    }

                    currentCode = code;
                    currentNom = rs.getString("nom");
                    currentPrenom = rs.getString("prenom");

                    bonjourMotifLabel.setText("Bonjour " + currentPrenom + " " + currentNom + " !");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showError("Erreur BDD.");
                return;
            }

            switchPage(scene, root, motifPage, "/com/example/pointagew/pointage.css");
        });

        retourPointage.setOnAction(e -> {
            // reset page motif + identité
            currentCode = null;
            currentNom = null;
            currentPrenom = null;
            bonjourMotifLabel.setText("");

            journeeGroup.selectToggle(null);
            repasGroup.selectToggle(null);

            switchPage(scene, root, pointagePage, "/com/example/pointagew/accueil.css");
        });


        confirmerMotif.setOnAction(e -> {
            if (currentCode == null) {


                showError("Valide d'abord ton code.");
                return;
            }

            Toggle tMoment = journeeGroup.getSelectedToggle();
            if (tMoment == null) {
                showError("Choisis Matin ou Après-midi.");
                return;
            }
            String moment = ((ToggleButton) tMoment).getText().equals("Matin") ? "MATIN" : "APRES_MIDI";

            int repasVal;
            if (!repasChoice.isVisible()) {
                repasVal = 0;
            } else if (oui.isSelected()) {
                repasVal = 1;
            } else if (non.isSelected()) {
                repasVal = 0;
            } else {
                showError("Choisis Oui ou Non pour le repas.");
                return;
            }

            String jour = LocalDate.now().toString();
            String ts = LocalDateTime.now().toString();

            String sql = "INSERT OR IGNORE INTO  pointage(id_benevole, jour, moment, repas, ts) VALUES (?,?,?,?,?)";
            try (var c = com.example.pointagew.database.DataBase.getConnection();
                 var ps = c.prepareStatement(sql)) {

                ps.setInt(1, currentCode);
                ps.setString(2, jour);
                ps.setString(3, moment);
                ps.setInt(4, repasVal);
                ps.setString(5, ts);

                ps.executeUpdate(); // INSERT => executeUpdate [web:9]
            } catch (SQLException ex) {
                ex.printStackTrace();
                showError("Impossible d'enregistrer le pointage.");
                return;
            }

            // reset UI après pointage
            journeeGroup.selectToggle(null);
            repasGroup.selectToggle(null);

            switchPage(scene,root,merciPage, "/com/example/pointagew/acceuil.css");
            PauseTransition p = new PauseTransition(Duration.seconds(5));
            p.setOnFinished(ev ->
                    switchPage(scene, root, accueilPage, "/com/example/pointagew/accueil.css")
            );
            p.play();
        });

        // ======================
        // PAGE INITIALE
        // ======================
        switchPage(scene, root, accueilPage, "/com/example/pointagew/accueil.css");

        stage.setTitle("Pointage App");
        stage.setScene(scene);
        stage.show();
    }

    private void purgeAuDemarrage() {
        // localtime pour être calé sur l'heure locale (France), pas UTC
        String sql = "DELETE FROM pointage WHERE jour = date('now','localtime','-1 day')";

        try (var c = com.example.pointagew.database.DataBase.getConnection();
             var st = c.createStatement()) {

            int deleted = st.executeUpdate(sql);
            System.out.println("Purge J-1 : " + deleted + " lignes supprimées");

        } catch (SQLException ex) {
            ex.printStackTrace();
            showError("Erreur purge J-1.");
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait(); // dialog bloquant [web:247]
    }

    private void switchPage(Scene scene, VBox root, VBox page, String cssPath) {
        scene.getStylesheets().clear();
        var css = getClass().getResource(cssPath);
        if (css == null) {
            System.err.println("CSS introuvable : " + cssPath);
        } else {
            scene.getStylesheets().add(css.toExternalForm());
        }
        root.getChildren().setAll(page);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
