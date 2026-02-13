package com.example.pointagew;

import javafx.scene.control.Alert;

public class Erreur {

    public static void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait(); // dialog bloquant [web:247]
    }
}
