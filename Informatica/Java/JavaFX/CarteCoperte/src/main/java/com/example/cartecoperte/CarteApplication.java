package com.example.cartecoperte;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CarteApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarteApplication.class.getResource("carte-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Indovina la carta!");
        stage.setScene(scene);
        stage.show();
    }
}
