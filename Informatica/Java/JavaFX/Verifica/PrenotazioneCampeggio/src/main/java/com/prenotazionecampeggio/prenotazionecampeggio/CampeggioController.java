package com.prenotazionecampeggio.prenotazionecampeggio;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class CampeggioController {
    private CampeggioModel gestore;

    @FXML
    private Label lblRisultato;
    @FXML
    private TextField nameTextField;
    @FXML
    private DatePicker inizioDatePicker;
    @FXML
    private DatePicker fineDatePicker;
    @FXML
    private TextField piazzolaTextField;
    @FXML
    private ChoiceBox<String> attrezzaturaChoiceBox;
    @FXML
    private CheckBox animaliCheckBox;
    @FXML
    private TextField adultiTextField;
    @FXML
    private TextField bambiniTextField;


    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        gestore = new CampeggioModel();
        attrezzaturaChoiceBox.getItems().add("basic");
        attrezzaturaChoiceBox.getItems().add("comfort");
        attrezzaturaChoiceBox.getItems().add("comfort plus");
        attrezzaturaChoiceBox.getSelectionModel().selectFirst();
    }

    private double getPrezzo() {
        LocalDate data_inizio = inizioDatePicker.getValue();
        if (data_inizio == null) {
            setRisultato("Inserisci una data d'inizio");
            return -1;
        }
        LocalDate data_fine = fineDatePicker.getValue();
        if (data_fine == null) {
            setRisultato("Inserisci una data di fine");
            return -1;
        }
        String attrezzatura = attrezzaturaChoiceBox.getSelectionModel().getSelectedItem();
        if (attrezzatura == null) {
            setRisultato("Inserisci un opzione per l'attrezzatura");
            return -1;
        }
        boolean animali = animaliCheckBox.isSelected();
        int nAdulti;
        int nbambini;
        try {
            nAdulti = Integer.parseInt(adultiTextField.getText());
            nbambini = Integer.parseInt(bambiniTextField.getText());
        } catch (NumberFormatException e) {
            setRisultato("Inserisci un numero corretto");
            return -1;
        }
        return gestore.showPrice(data_inizio, data_fine, attrezzatura, animali, nAdulti, nbambini);
    }

    private void setRisultato(String risultato) {
        lblRisultato.setVisible(true);
        lblRisultato.setText(risultato);
    }

    @FXML
    protected void calcolaPrezzo() {
        double prezzo = getPrezzo();
        if (prezzo == -1) {
            return;
        }
        setRisultato("Prezzo = " + prezzo + " €");
    }

    @FXML
    protected void prenota() throws IOException {
        String nome = nameTextField.getText();
        if (nome == null) {
            setRisultato("Inserisci il nome");
            return;
        }
        LocalDate data_inizio = inizioDatePicker.getValue();
        if (data_inizio == null) {
            setRisultato("Inserisci una data d'inizio");
            return;
        }
        LocalDate data_fine = fineDatePicker.getValue();
        if (data_fine == null) {
            setRisultato("Inserisci una data di fine");
            return;
        }
        int piazzola;
        try {
            piazzola = Integer.parseInt(piazzolaTextField.getText());
        } catch (NumberFormatException e) {
            setRisultato("Inserisci un numero corretto");
            return;
        }
        String attrezzatura = attrezzaturaChoiceBox.getSelectionModel().getSelectedItem();
        if (attrezzatura == null) {
            setRisultato("Inserisci un opzione per l'attrezzatura");
            return;
        }
        boolean animali = animaliCheckBox.isSelected();
        int nAdulti;
        int nbambini;
        try {
            nAdulti = Integer.parseInt(adultiTextField.getText());
            nbambini = Integer.parseInt(bambiniTextField.getText());
        } catch (NumberFormatException e) {
            setRisultato("Inserisci un numero corretto");
            return;
        }
        if (gestore.addPrenotazione(nome, data_inizio, data_fine, piazzola, attrezzatura, animali, nAdulti, nbambini)) {
            setRisultato("Prenotazione effettuata con successo");
        }
        else  {
            setRisultato("Piazzola non disponibile");
        }
    }

    @FXML
    protected void showPrenotazioni() {
        StringBuilder prenotazioni = new StringBuilder();
        Set<Integer> keys = gestore.getPrenotazioni().keySet();
        for (Integer key : keys) {
            prenotazioni.append(key).append(" -> ").append(gestore.getPrenotazioni().get(key).getNome());
            prenotazioni.append("\n");
        }
        setRisultato(prenotazioni.toString());
    }
}