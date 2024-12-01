module com.example.tworcaquizow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tworcaquizow to javafx.fxml;
    exports com.example.tworcaquizow;
}