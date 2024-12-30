module com.example.tworcaquizow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;

    opens com.example.tworcaquizow to javafx.fxml;
    exports com.example.tworcaquizow;
}