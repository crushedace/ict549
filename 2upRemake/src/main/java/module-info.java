module com.example.twoupremake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires junit;


    opens com.example.twoupremake to javafx.fxml;
    exports com.example.twoupremake;
}