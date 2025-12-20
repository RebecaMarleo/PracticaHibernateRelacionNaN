package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.ventanaMenu = false;
    }

    @FXML
    private Button btnJuegos;

    @FXML
    private Button btnTransacciones;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnEtiquetas;

    @FXML
    private Button btnLogout;

    @FXML
    public void onBtnJuegos(ActionEvent event) throws IOException {
        Main.ventanaMenu = true;
        SceneManager.showVentana(event, "juegos", 700, 475);
    }

    @FXML
    public void onBtnTransacciones(ActionEvent event) throws IOException {
        Main.ventanaMenu = true;
        SceneManager.showVentana(event, "transacciones", 1000, 575);
    }

    @FXML
    public void onBtnClientes(ActionEvent event) throws IOException {
        Main.ventanaMenu = true;
        SceneManager.showVentana(event, "clientes", 900, 475);
    }

    @FXML
    public void onBtnEtiquetas(ActionEvent event) throws IOException {
        Main.ventanaMenu = true;
        SceneManager.showVentana(event, "etiquetas", 700, 475);
    }

    @FXML
    public void onBtnLogout(ActionEvent event) throws IOException {
        SceneManager.showVentana(event, "login", 500, 400);
    }
}
