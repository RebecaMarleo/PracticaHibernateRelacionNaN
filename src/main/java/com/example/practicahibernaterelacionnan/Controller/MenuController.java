package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.Util.R;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController {
    @FXML
    private Button btnJuegos;

    @FXML
    private Button btnTransacciones;

    @FXML
    public void onBtnJuegos(ActionEvent event) {

    }

    @FXML
    public void onBtnTransacciones(ActionEvent event) {

    }

    public void closeWindow() {
        try {
            // se obtiene la interfaz que se va a cargar
            FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("login.fxml"));

            Parent root = fxmlLoader.load();

            // se muestra la nueva interfaz
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Reparaciones de coches");
            stage.setScene(scene);
            stage.show();

            // se cierra esta interfaz
            Stage myStage = (Stage) this.btnJuegos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
