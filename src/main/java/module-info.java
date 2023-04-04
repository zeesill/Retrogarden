module com.example.retrogarden {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.retrogarden to javafx.fxml;
    exports com.example.retrogarden;
    exports com.example.retrogarden.Controllers;
    opens com.example.retrogarden.Controllers to javafx.fxml;
    opens com.example.retrogarden.Models to javafx.base;
}