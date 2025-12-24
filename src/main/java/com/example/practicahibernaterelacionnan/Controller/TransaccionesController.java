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

public class TransaccionesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.ventanaTransacciones = false;
    }

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnAnadir;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnGestionarCatalogo;

    @FXML
    private Button btnGestionarClientes;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnQuitar;

    @FXML
    private ComboBox<?> cbCliente;

    @FXML
    private ComboBox<?> cbTransaccion;

    @FXML
    private TableColumn<?, ?> colCatId;

    @FXML
    private TableColumn<?, ?> colCatPegi;

    @FXML
    private TableColumn<?, ?> colCatPrecio;

    @FXML
    private TableColumn<?, ?> colCatTitulo;

    @FXML
    private TableColumn<?, ?> colJuegosId;

    @FXML
    private TableColumn<?, ?> colJuegosPegi;

    @FXML
    private TableColumn<?, ?> colJuegosPrecio;

    @FXML
    private TableColumn<?, ?> colJuegosTitulo;

    @FXML
    private TableColumn<?, ?> colTransCliente;

    @FXML
    private TableColumn<?, ?> colTransEmpleado;

    @FXML
    private TableColumn<?, ?> colTransId;

    @FXML
    private TableColumn<?, ?> colTransTipo;

    @FXML
    private TableColumn<?, ?> colTransTotal;

    @FXML
    private TableView<?> tblCatalogo;

    @FXML
    private TableView<?> tblJuegos;

    @FXML
    private TableView<?> tblTransacciones;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtEmpleado;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtTotal;

    @FXML
    void onBtnAceptar(ActionEvent event) {

    }

    @FXML
    void onBtnAnadir(ActionEvent event) {

    }

    @FXML
    void onBtnBorrar(ActionEvent event) {

    }

    @FXML
    void onBtnCancelar(ActionEvent event) {

    }

    @FXML
    void onBtnCrear(ActionEvent event) {

    }

    @FXML
    void onBtnGestionarCatalogo(ActionEvent event) throws IOException {
        Main.ventanaTransacciones = true;
        SceneManager.showVentana(event, "juegos", 700, 475);
    }

    @FXML
    void onBtnGestionarClientes(ActionEvent event) throws IOException {
        Main.ventanaTransacciones = true;
        SceneManager.showVentana(event, "clientes", 900, 475);
    }

    @FXML
    void onBtnModificar(ActionEvent event) {

    }

    @FXML
    void onBtnQuitar(ActionEvent event) {

    }

    @FXML
    void onBtnVolver(ActionEvent event) throws IOException {
        SceneManager.showVentana(event, "menu", 300, 325);
    }
}
