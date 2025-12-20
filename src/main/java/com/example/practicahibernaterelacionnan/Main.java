package com.example.practicahibernaterelacionnan;

import com.example.practicahibernaterelacionnan.Util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static String correo;
//    public static String ventanaAnterior;
    public static boolean ventanaJuegos;
    public static boolean ventanaTransacciones;
    public static boolean ventanaMenu;
    public static Stage stage;
}
