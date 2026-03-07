package com.codicefiscale.codicefiscale;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class CodiceFiscaleController {
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCognome;
    @FXML
    private DatePicker dateNascita;
    @FXML
    private RadioButton rbMaschio;
    @FXML
    private RadioButton rbFemmina;
    @FXML
    private TextField txtComune;
    @FXML
    private TextField txtRisultato;

    @FXML
    public void onCalcola() {
        char sesso = rbFemmina.isSelected() ? 'F' : 'M';

        if (
            txtNome.getText().isEmpty() ||
            txtCognome.getText().isEmpty() ||
            dateNascita.getValue() == null ||
            txtComune.getText().isEmpty()
        ) {
            return;
        }

        String cf = CodiceFiscaleCalculator.calcola(
                txtNome.getText(),
                txtCognome.getText(),
                dateNascita.getValue(),
                sesso,
                txtComune.getText()
        );

        txtRisultato.setText(cf);
    }
}