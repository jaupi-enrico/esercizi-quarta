package com.example.cartecoperte;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarteController {

    @FXML
    private Label lbGeneral;

    @FXML
    private ImageView imgCard1;
    @FXML
    private ImageView imgCard2;
    @FXML
    private ImageView imgCard3;
    @FXML
    private Button btnShuffle;

    private List<Image> carte = new ArrayList<>();
    private List<String> nomiCarte = new ArrayList<>();
    private int posizioneAsso;
    private boolean inGioco = false;

    private Image dorso;

    @FXML
    public void initialize() {
        // carica il dorso e le carte
        dorso = new Image(getClass().getResourceAsStream("/imgs/dorso.jpg"));

        carte.add(new Image(getClass().getResourceAsStream("/imgs/asso.jpg")));
        carte.add(new Image(getClass().getResourceAsStream("/imgs/due.jpg")));
        carte.add(new Image(getClass().getResourceAsStream("/imgs/tre.jpg")));

        nomiCarte.add("asso");
        nomiCarte.add("due");
        nomiCarte.add("tre");

        mostraCarte();
        lbGeneral.setText("Individua l'Asso e premi Mescola");
    }

    // mostra le carte scoperte
    private void mostraCarte() {
        imgCard1.setImage(carte.get(0));
        imgCard2.setImage(carte.get(1));
        imgCard3.setImage(carte.get(2));
    }

    // mostra i dorsi
    private void mostraDorsi() {
        imgCard1.setImage(dorso);
        imgCard2.setImage(dorso);
        imgCard3.setImage(dorso);
    }

    // mescola le carte e mostra i dorsi
    @FXML
    protected void shuffle() {
        // mescola sia le immagini che i nomi
        List<Integer> indici = new ArrayList<>();
        indici.add(0);
        indici.add(1);
        indici.add(2);
        Collections.shuffle(indici);

        List<Image> carteMescolate = new ArrayList<>();
        List<String> nomiMescolati = new ArrayList<>();
        for (int i : indici) {
            carteMescolate.add(carte.get(i));
            nomiMescolati.add(nomiCarte.get(i));
        }
        carte = carteMescolate;
        nomiCarte = nomiMescolati;

        // trova la posizione dell'Asso
        posizioneAsso = nomiCarte.indexOf("asso");

        mostraDorsi();
        lbGeneral.setText("Dov'è l'Asso?");
        inGioco = true;
    }

    // click sulle ImageView
    @FXML
    protected void show1() { controllaScelta(0); }

    @FXML
    protected void show2() { controllaScelta(1); }

    @FXML
    protected void show3() { controllaScelta(2); }

    // verifica se l'utente ha indovinato
    private void controllaScelta(int scelta) {
        if (!inGioco) return;

        mostraCarte();

        if (scelta == posizioneAsso) {
            lbGeneral.setText("Hai INDOVINATO!");
        } else {
            lbGeneral.setText("Hai SBAGLIATO");
        }

        inGioco = false;
    }
}
