package com.bandiere.bandiere;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GiocoBandiere {
    private ArrayList<QuizOption> domande;
    private int punteggio;
    private ArrayList<Integer> fatte;
    private int status;

    public GiocoBandiere() {
        domande = new ArrayList<>();
        fatte = new ArrayList<>();
        punteggio = 0;
        status = 1;
        caricaDomandeDaFile("/bandiere.txt");
    }


    public void caricaDomandeDaFile(String percorsoFile) {
        ArrayList<String> righe = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(percorsoFile)))) {
            String riga;
            while ((riga = br.readLine()) != null) {
                righe.add(riga);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < righe.size(); i++) {
            String[] dati = righe.get(i).split(";");
            String imgSrc = dati[0];
            String correttaOpzione = dati[1];
            ArrayList<String> randomOptions = new ArrayList<>();
            randomOptions.add(correttaOpzione);
            for (int j = 0; j < 3; j++) {
                int random_index = (int) (Math.random() * righe.size());
                String opzione;
                while (randomOptions.contains(opzione = righe.get(random_index).split(";")[1])) {
                    random_index = (int) (Math.random() * righe.size());
                }
                randomOptions.add(opzione);
            }
            this.domande.add(new QuizOption(imgSrc, randomOptions, correttaOpzione));
        }
    }

    public QuizOption getRandomOpzione() {
        if (fatte.size() == domande.size()) {
            status = 2;
            return null;
        }
        int random_index = (int) (Math.random() * domande.size());
        while (fatte.contains(random_index)) {
            random_index = (int) (Math.random() * domande.size());
        }
        fatte.add(random_index);
        return domande.get(random_index);
    }

    public boolean isFinished() {
        if (status == 2) {
            return true;
        }
        return false;
    }

    public void addPoint() {
        this.punteggio++;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public int getTotale() {
        return domande.size();
    }
}
