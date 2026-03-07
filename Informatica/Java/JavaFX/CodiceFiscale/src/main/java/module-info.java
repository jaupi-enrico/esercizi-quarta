module com.codicefiscale.codicefiscale {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.codicefiscale.codicefiscale to javafx.fxml;
    exports com.codicefiscale.codicefiscale;
}