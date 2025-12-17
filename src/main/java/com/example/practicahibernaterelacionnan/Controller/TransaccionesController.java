package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransaccionesController {
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
    void onBtnGestionarCatalogo(ActionEvent event) {

    }

    @FXML
    void onBtnGestionarClientes(ActionEvent event) {

    }

    @FXML
    void onBtnModificar(ActionEvent event) {

    }

    @FXML
    void onBtnQuitar(ActionEvent event) {

    }

    public void closeWindow() {
        try {
            // se obtiene la interfaz que se va a cargar
            FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("menu.fxml"));

            Parent root = fxmlLoader.load();

            // se muestra la nueva interfaz
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Menú principal");
            stage.setScene(scene);
            stage.show();

            // se cierra esta interfaz
            Stage myStage = (Stage) this.tblTransacciones.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
