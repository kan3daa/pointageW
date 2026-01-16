module com.example.pointagew {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.desktop;

    // Apache POI (Excel)
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    // Pour chargement FXML et controllers
    opens com.example.pointagew to javafx.fxml;
    exports com.example.pointagew;

    opens com.example.pointagew.database to javafx.fxml;
}
