package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.Main;
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
        cambiarVentana("juegos");
    }

    @FXML
    public void onBtnTransacciones(ActionEvent event) {
        cambiarVentana("transacciones");
    }

    private void cambiarVentana(String ventana) {
        try {
            FXMLLoader fxmlLoader;
            switch (ventana) {
                case "juegos":
                    fxmlLoader = new FXMLLoader(R.getUI("juegos.fxml"));
                    break;
                case "transacciones":
                    fxmlLoader = new FXMLLoader(R.getUI("transacciones.fxml"));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ventana);
            }

            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            switch (ventana) {
                case "juegos":
                    stage.setTitle("Juegos");
                    break;
                case "transacciones":
                    stage.setTitle("Transacciones");
                    break;
            }

            stage.setScene(scene);
            stage.show();

            Stage myStage;

            switch (ventana) {
                case "juegos":
                    JuegosController controladorJuegos = fxmlLoader.getController();
                    stage.setOnCloseRequest(e -> controladorJuegos.closeWindow());
                    myStage = (Stage) this.btnJuegos.getScene().getWindow();
                    break;
                case "transacciones":
                    TransaccionesController controladorTransacciones = fxmlLoader.getController();
                    stage.setOnCloseRequest(e -> controladorTransacciones.closeWindow());
                    myStage = (Stage) this.btnJuegos.getScene().getWindow();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ventana);
            }

            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void closeWindow() {
        try {
            // se obtiene la interfaz que se va a cargar
            FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("login.fxml"));

            Parent root = fxmlLoader.load();

            Main.correo = "";

            // se muestra la nueva interfaz
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Login");
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
