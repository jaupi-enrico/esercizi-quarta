package com.abbonamento.abbonamento;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class HelloController {

    @FXML
    private RadioButton mensile;

    @FXML
    private RadioButton semestrale;

    @FXML
    private RadioButton annuale;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label welcomeText;

    private ToggleGroup gruppoAbbonamento;

    @FXML
    public void initialize() {
        gruppoAbbonamento = new ToggleGroup();
        mensile.setToggleGroup(gruppoAbbonamento);
        semestrale.setToggleGroup(gruppoAbbonamento);
        annuale.setToggleGroup(gruppoAbbonamento);
    }

    @FXML
    protected void onCalcolaButtonClick() {
        LocalDate inizio = datePicker.getValue();
        if (inizio == null) {
            welcomeText.setText("Seleziona prima una data di inizio!");
            return;
        }

        RadioButton selezionato = (RadioButton) gruppoAbbonamento.getSelectedToggle();
        if (selezionato == null) {
            welcomeText.setText("Seleziona prima un tipo di abbonamento!");
            return;
        }

        LocalDate fine = null;
        switch (selezionato.getText()) {
            case "Mensile" -> fine = inizio.plusMonths(1);
            case "Semestrale" -> fine = inizio.plusMonths(6);
            case "Annuale" -> fine = inizio.plusYears(1);
        }

        welcomeText.setText("Data di fine abbonamento: " + fine);
    }
}
