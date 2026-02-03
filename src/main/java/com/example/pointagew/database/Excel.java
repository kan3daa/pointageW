package com.example.pointagew.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

    // Méthode pour afficher une alerte d'erreur
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public Boolean creeExcel() {

        String sql = "SELECT benevole.id_benevole, benevole.nom, benevole.prenom, " +
                "pointage.repas, pointage.jour, pointage.moment " +
                "FROM benevole " +
                "JOIN pointage ON benevole.id_benevole = pointage.id_benevole;";

        List<Object[]> donnees = new ArrayList<>();

        try (var c = DataBase.getConnection();
            var ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                donnees.add(new Object[]{
                        rs.getInt("id_benevole"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("jour"),
                        rs.getString("moment"),
                        rs.getBoolean("repas")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            showError("Erreur BDD.");
            return false;
        }

        if (donnees.isEmpty()) {
            showError("Aucun bénévole trouvé.");
            return false;
        }

        // Chemin portable pour Linux et Windows
        String nomFichier = System.getProperty("user.home") + "/pointage.xlsx";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Feuille1");

        // En-têtes
        Row headerRow = sheet.createRow(0);
        String[] entetes = {"ID", "Nom", "Prénom", "Jour", "Moment", "Repas"};

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < entetes.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(entetes[i]);
            cell.setCellStyle(headerStyle);
        }

        // Données
        int rowNum = 1;
        for (Object[] ligne : donnees) {
            Row row = sheet.createRow(rowNum++);
            for (int col = 0; col < ligne.length; col++) {
                Cell cell = row.createCell(col);
                Object val = ligne[col];
                if (val instanceof String s) {
                    cell.setCellValue(s);
                } else if (val instanceof Integer i) {
                    cell.setCellValue(i);
                } else if (val instanceof Boolean b) {
                    cell.setCellValue(b ? "oui" : "non");
                }
            }
        }

        // Ajustement automatique des colonnes
        for (int i = 0; i < entetes.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Écriture du fichier Excel
        try (FileOutputStream fileOut = new FileOutputStream(nomFichier)) {
            workbook.write(fileOut);
            System.out.println("Fichier Excel créé : " + nomFichier);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur lors de la création du fichier Excel.");
            return false;
        } finally {
            try {
                workbook.close();
            } catch (IOException ignored) {
            }
        }
    }

} // <-- dernière accolade fermante pour la classe
