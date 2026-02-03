module com.example.pointagew {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Apache POI
    requires org.apache.poi.poi;        // Classes de base (Workbook, Sheet, Row, CellStyle, Font)
    requires org.apache.poi.ooxml;      // Format OOXML (XSSFWorkbook, XSSFSheet)
    requires org.apache.poi.ooxml.schemas; // Pour certains styles et propriétés avancées

    exports com.example.pointagew;
    
    opens com.example.pointagew to javafx.fxml;
    opens com.example.pointagew.database to javafx.fxml;
}
