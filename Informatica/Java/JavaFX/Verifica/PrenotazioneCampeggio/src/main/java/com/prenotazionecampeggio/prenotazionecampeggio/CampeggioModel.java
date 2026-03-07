package com.prenotazionecampeggio.prenotazionecampeggio;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class CampeggioModel implements Serializable {
    private final static int nPiazzole = 30;

    private Map<Integer, Prenotazione> prenotazioni;

    public CampeggioModel() throws IOException, ClassNotFoundException {
        try (FileInputStream f = new FileInputStream("data/prenotazioni.bin");
             ObjectInputStream in = new ObjectInputStream(f)) {
            prenotazioni = (TreeMap<Integer, Prenotazione>) in.readObject();
            if (prenotazioni == null) {
                prenotazioni = new TreeMap<>();
            }
        } catch (FileNotFoundException e) {
            prenotazioni = new TreeMap<>();
        }
    }

    public double showPrice(LocalDate data_inizio, LocalDate data_fine, String attrezzatura, boolean animali, int nAdulti, int nBambini) {
        return Prenotazione.calcolaPrezzo(data_inizio, data_fine, attrezzatura, animali, nAdulti, nBambini);
    }

    public boolean addPrenotazione(String nome, LocalDate data_inizio, LocalDate data_fine,
                                   int nPiazzola, String attrezzatura, boolean animali,
                                   int nAdulti, int nBambini) throws IOException {

        if (nPiazzola >= 1 && nPiazzola <= nPiazzole && !prenotazioni.containsKey(nPiazzola)) {

            prenotazioni.put(nPiazzola,
                    new Prenotazione(nome, data_inizio, data_fine, nPiazzola,
                            attrezzatura, animali, nAdulti, nBambini));

            try (FileOutputStream f = new FileOutputStream("data/prenotazioni.bin");
                 ObjectOutputStream out = new ObjectOutputStream(f)) {

                out.writeObject(prenotazioni);
            }

            return true;
        }

        return false;
    }

    public Map<Integer, Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
}
