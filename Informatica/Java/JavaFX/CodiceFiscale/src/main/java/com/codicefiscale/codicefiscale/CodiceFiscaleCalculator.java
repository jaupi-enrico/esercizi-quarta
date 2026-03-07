package com.codicefiscale.codicefiscale;

import java.time.LocalDate;

public class CodiceFiscaleCalculator {
    private static char getCodeMonth(int n) {
        return switch (n) {
            case 1 -> 'A';
            case 2 -> 'B';
            case 3 -> 'C';
            case 4 -> 'D';
            case 5 -> 'E';
            case 6 -> 'H';
            case 7 -> 'L';
            case 8 -> 'M';
            case 9 -> 'P';
            case 10 -> 'R';
            case 11 -> 'S';
            case 12 -> 'T';
            default -> throw new IllegalArgumentException("Mese non valido: " + n);
        };
    }

    public static String calcola(
            String nome,
            String cognome,
            LocalDate dataNascita,
            char sesso,
            String codiceComune
    ) {
        String codiceFiscale = "";

        codiceFiscale += estraiCognome(cognome);
        codiceFiscale += estraiNome(nome);
        codiceFiscale += String.format("%02d", dataNascita.getYear() % 100);
        codiceFiscale += getCodeMonth(dataNascita.getMonthValue());
        codiceFiscale += calcolaGiorno(dataNascita.getDayOfMonth(), sesso);
        codiceFiscale += codiceComune.toUpperCase();

        codiceFiscale += calcolaCarattereControllo(codiceFiscale);

        return codiceFiscale;
    }

    private static String estraiCognome(String cognome) {
        return estraiTreCaratteri(cognome);
    }

    private static String estraiNome(String nome) {
        String consonanti = nome.replaceAll("[^BCDFGHJKLMNPQRSTVWXYZ]", "");
        if (consonanti.length() >= 4) {
            return "" + consonanti.charAt(0)
                    + consonanti.charAt(2)
                    + consonanti.charAt(3);
        }
        return estraiTreCaratteri(nome);
    }

    private static String estraiTreCaratteri(String s) {
        s = s.toUpperCase();
        String consonanti = s.replaceAll("[^BCDFGHJKLMNPQRSTVWXYZ]", "");
        String vocali = s.replaceAll("[^AEIOU]", "");

        String risultato = consonanti + vocali + "XXX";
        return risultato.substring(0, 3);
    }

    private static String calcolaGiorno(int giorno, char sesso) {
        if (sesso == 'F' || sesso == 'f') {
            giorno += 40;
        }
        return String.format("%02d", giorno);
    }

    private static char calcolaCarattereControllo(String codice) {
        int pari = 0, dispari = 0;

        for (int i = 0; i < codice.length(); i++) {
            char c = codice.charAt(i);
            if ((i + 1) % 2 == 0) {
                pari += valorePari(c);
            } else {
                dispari += valoreDispari(c);
            }
        }

        return (char) ('A' + (pari + dispari) % 26);
    }

    private static int valorePari(char c) {
        if (Character.isDigit(c)) return c - '0';
        return c - 'A';
    }

    private static int valoreDispari(char c) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int[] values = {
                1,0,5,7,9,13,15,17,19,21,
                1,0,5,7,9,13,15,17,19,21,
                2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23
        };
        return values[chars.indexOf(c)];
    }
}