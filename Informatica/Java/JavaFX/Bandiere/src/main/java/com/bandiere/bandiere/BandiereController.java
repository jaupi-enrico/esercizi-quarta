package com.bandiere.bandiere;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;

public class BandiereController {
    final private GiocoBandiere game = new GiocoBandiere();
    QuizOption opzione_attuale;
    ArrayList<RadioButton> opzioni = new ArrayList<>();

    @FXML
    private ImageView imgHolder;
    @FXML
    private RadioButton opzione1;
    @FXML
    private RadioButton opzione2;
    @FXML
    private RadioButton opzione3;
    @FXML
    private RadioButton opzione4;
    @FXML
    private Button nextButton;
    @FXML
    private Label lbStatus;

    private Timing time;

    @FXML
    private void initialize() {
        opzione_attuale = game.getRandomOpzione();
        opzioni.add(opzione1);
        opzioni.add(opzione2);
        opzioni.add(opzione3);
        opzioni.add(opzione4);
        set_opzione();

        time = new Timing(10, lbStatus, this);
    }

    @FXML
    public void setImg(String imgSrc) {
        InputStream is = getClass().getResourceAsStream("/images/" + imgSrc);

        if (is == null) {
            System.out.println("Immagine non trovata: " + imgSrc);
            return;
        }

        Image image = new Image(is);
        imgHolder.setImage(image);
    }

    private void set_opzione() {
        opzione_attuale.shuffle();
        for (int i = 0; i < opzioni.size(); i++) {
            opzioni.get(i).setText(opzione_attuale.getOpzioni().get(i));
        }
        setImg(opzione_attuale.getImgSrc());
    }

    @FXML
    protected void onNextButtonClick() {
        nextQuestion(false);
    }

    public void nextQuestion(boolean bypass) {
        String selected = null;
        for (RadioButton rb : opzioni) {
            if (rb.isSelected()) {
                selected = rb.getText();
                break;
            }
        }
        if (!bypass && selected == null) {
            return;
        }
        if (!bypass && opzione_attuale.checkCorretta(selected)) {
            game.addPoint();
        }
        opzione_attuale = game.getRandomOpzione();
        if (opzione_attuale != null) {
            set_opzione();
            for (RadioButton rb : opzioni) {
                rb.setSelected(false);
            }
            time.start(10);
        }
        else {
            if (game.isFinished()) {
                time.stop();
                showResult();
            }
        }
    }

    private void showResult() {
        imgHolder.setVisible(false);
        nextButton.setVisible(false);
        for (RadioButton rb : opzioni) {
            rb.setVisible(false);
        }
        lbStatus.setText("Punteggio: " + game.getPunteggio() + "/" + game.getTotale());
    }
}