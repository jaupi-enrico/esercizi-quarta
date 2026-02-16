module com.example.cartecoperte {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cartecoperte to javafx.fxml;
    exports com.example.cartecoperte;
}