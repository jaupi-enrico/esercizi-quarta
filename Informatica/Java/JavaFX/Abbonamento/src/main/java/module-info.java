module com.abbonamento.abbonamento {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.abbonamento.abbonamento to javafx.fxml;
    exports com.abbonamento.abbonamento;
}