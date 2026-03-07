package com.codicefiscale.codicefiscale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CodiceFiscaleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CodiceFiscaleApplication.class.getResource("codicefiscale-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calcolo codice fiscale");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}