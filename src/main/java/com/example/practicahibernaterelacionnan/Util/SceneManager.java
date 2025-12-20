package com.example.practicahibernaterelacionnan.Util;

import com.example.practicahibernaterelacionnan.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public static void showVentana(ActionEvent event, String fxml, int anchura, int altura) throws IOException {
        Parent root = FXMLLoader.load(R.getUI(fxml+".fxml"));
        Main.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, anchura, altura);
        Main.stage.setTitle(fxml.substring(0, 1).toUpperCase() + fxml.substring(1));
        Main.stage.setScene(scene);
        Main.stage.centerOnScreen();
        Main.stage.show();
    }
}
