package com.prenotazionecampeggio.prenotazionecampeggio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CampeggioApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CampeggioApplication.class.getResource("campeggio-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestore prenotazioni del campeggio");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}