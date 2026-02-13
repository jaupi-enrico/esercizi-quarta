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
    private static ImageView imgHolder;
    @FXML
    private static RadioButton opzione1;
    @FXML
    private static RadioButton opzione2;
    @FXML
    private static RadioButton opzione3;
    @FXML
    private static RadioButton opzione4;
    @FXML
    private static Button nextButton;
    @FXML
    private static Label lbStatus;

    @FXML
    private void initialize() {
        opzione_attuale = game.getRandomOpzione();
        opzioni.add(opzione1);
        opzioni.add(opzione2);
        opzioni.add(opzione3);
        opzioni.add(opzione4);
        set_opzione();
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
        for (int i = 0; i < opzioni.size(); i++) {
            opzioni.get(i).setText(opzione_attuale.getOpzioni(i));
        }
        setImg(opzione_attuale.getImgSrc());
    }

    static public void timerScaduto() {

    }

    static public void timeUpdate(int tempoRimanente) {
        lbStatus.setText(Integer.toString(tempoRimanente));
    }

    @FXML
    protected void onNextButtonClick() {
        String selected = null;
        for (RadioButton rb : opzioni) {
            if (rb.isSelected()) {
                selected = rb.getText();
                break;
            }
        }
        if (selected == null) {
            return;
        }
        if (opzione_attuale.checkCorretta(selected)) {
            game.addPoint();
        }
        opzione_attuale = game.getRandomOpzione();
        if (opzione_attuale != null) {
            set_opzione();
            for (RadioButton rb : opzioni) {
                rb.setSelected(false);
            }
        }
        else {
            if (game.isFinished()) {
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
        lbStatus.setText("Fine " + game.getPunteggio() + "/" + game.getTotale());
    }
}