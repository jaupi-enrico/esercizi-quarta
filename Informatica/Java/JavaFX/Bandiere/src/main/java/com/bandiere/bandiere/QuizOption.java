package com.bandiere.bandiere;
import java.util.ArrayList;
import java.util.Collections;

public class QuizOption {
    private String imgSrc;
    private ArrayList<String> opzioni;
    private String opzioneCorretta;

    public QuizOption(String imgSrc, ArrayList<String> opzioni, String opzioneCorretta) {
        this.imgSrc = imgSrc;
        this.opzioni = opzioni;
        this.opzioneCorretta = opzioneCorretta;
    }

    public boolean checkCorretta(String s) {
        return s.equals(opzioneCorretta);
    }

    public void shuffle() {
        Collections.shuffle(opzioni);
    }

    public ArrayList<String> getOpzioni() {
        return opzioni;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}
