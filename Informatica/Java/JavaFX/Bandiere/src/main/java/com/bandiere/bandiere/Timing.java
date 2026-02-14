package com.bandiere.bandiere;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class Timing {

    private Timer timer;
    private TimerTask task;
    private int tempoRimanente;
    private Label label;
    private BandiereController controller;

    public Timing(int secondi, Label label, BandiereController controller) {
        this.label = label;
        this.controller = controller;
        start(secondi);
    }

    public void start(int secondi) {
        stop();

        tempoRimanente = secondi;
        label.setText(String.valueOf(tempoRimanente));

        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                tempoRimanente--;

                Platform.runLater(() -> {
                    label.setText(String.valueOf(tempoRimanente));
                });

                if (tempoRimanente <= 0) {
                    stop();
                    Platform.runLater(() -> controller.nextQuestion(true));
                }
            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void stop() {
        if (task != null) task.cancel();
        if (timer != null) timer.cancel();
    }
}
