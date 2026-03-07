package com.prenotazionecampeggio.prenotazionecampeggio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prenotazione implements Serializable {
    private String nome;
    private LocalDate data_inizio;
    private LocalDate data_fine;
    private int nPiazzola;
    private String attrezzatura;
    private boolean animali;
    private int nAdulti;
    private int nBambini;
    private double prezzo;

    public Prenotazione(String nome, LocalDate data_inizio, LocalDate data_fine, int nPiazzola, String attrezzatura, boolean animali, int nAdulti, int nBambini) {
        this.nome = nome;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.nPiazzola = nPiazzola;
        this.attrezzatura = attrezzatura;
        this.animali = animali;
        this.nAdulti = nAdulti;
        this.nBambini = nBambini;

        this.prezzo = calcolaPrezzo(this.data_inizio, this.data_fine, this.attrezzatura, this.animali, this.nAdulti, this.nBambini);
    }

    public double getPrezzo() {
        return prezzo;
    }

    static public double calcolaPrezzo(LocalDate data_inizio, LocalDate data_fine, String attrezzatura, boolean animali, int nAdulti, int nBambini) {
        double prezzo;
        boolean altaStagione = data_fine.isAfter(LocalDate.of(2026, 6, 1));

        switch (attrezzatura) {
            case "basic" -> {
                prezzo = altaStagione ? 42.50 : 36.50;
            }
            case "comfort" -> {
                prezzo = altaStagione ? 45 : 40.50;
            }
            case "comfort plus" -> {
                prezzo = altaStagione ? 50 : 43;
            }
            default -> {
                return -1;
            }
        }

        long giorni = ChronoUnit.DAYS.between(data_inizio, data_fine);
        prezzo += giorni * (nAdulti * 12L + nBambini * 7L);
        prezzo += animali ? 5 : 0;
        return prezzo;
    }

    public String getNome() {
        return nome;
    }
}
