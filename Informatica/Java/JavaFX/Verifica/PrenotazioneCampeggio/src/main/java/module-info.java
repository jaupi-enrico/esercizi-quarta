module com.prenotazionecampeggio.prenotazionecampeggio {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.prenotazionecampeggio.prenotazionecampeggio to javafx.fxml;
    exports com.prenotazionecampeggio.prenotazionecampeggio;
}