module com.example.tworcaquizow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.tworcaquizow to javafx.fxml;
    exports com.example.tworcaquizow;
}