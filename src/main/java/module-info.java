module com.example.pointagew {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.pointagew to javafx.fxml;
    exports com.example.pointagew;
}