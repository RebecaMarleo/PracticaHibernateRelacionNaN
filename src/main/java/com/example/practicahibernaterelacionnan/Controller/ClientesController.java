package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.ventanaTransacciones) {
            Main.ventanaMenu = false;
        }
    }

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<?, ?> colCliCorreo;

    @FXML
    private TableColumn<?, ?> colCliId;

    @FXML
    private TableColumn<?, ?> colCliNombre;

    @FXML
    private TableColumn<?, ?> colJueId;

    @FXML
    private TableColumn<?, ?> colJuePegi;

    @FXML
    private TableColumn<?, ?> colJuePrecio;

    @FXML
    private TableColumn<?, ?> colJueTitulo;

    @FXML
    private ListView<?> lvTransacciones;

    @FXML
    private TableView<?> tblClientes;

    @FXML
    private TableView<?> tblJuegos;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtEmpleado;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtTotal;

    @FXML
    void onBtnBorrar(ActionEvent event) {

    }

    @FXML
    void onBtnCrear(ActionEvent event) {

    }

    @FXML
    void onBtnModificar(ActionEvent event) {

    }

    @FXML
    void onBtnVolver(ActionEvent event) throws IOException {
        if (Main.ventanaMenu) {
            SceneManager.showVentana(event, "menu", 300, 325);
        } else if (Main.ventanaTransacciones) {
            Main.ventanaMenu = true;
            SceneManager.showVentana(event, "transacciones", 1000, 575);
        }
    }
}
