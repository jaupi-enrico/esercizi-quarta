package com.bandiere.bandiere;

import java.util.Timer;
import java.util.TimerTask;

public class Timing {
    private Timer timer;
    private int tempoRimanente;


    public Timing(int secondi) {
        timer = new Timer();
        tempoRimanente = secondi;
        timer.schedule(task2, 1000);
        timer.schedule(task1, secondi * 1000);
    }

    TimerTask task1 = new TimerTask() {
        @Override
        public void run() {
            BandiereController.timerScaduto();
        }
    };

    TimerTask task2 = new TimerTask() {
        @Override
        public void run() {
            BandiereController.timeUpdate(tempoRimanente);
            timer.schedule(task2, 1000);
        }
    };
}
