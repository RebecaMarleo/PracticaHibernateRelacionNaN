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

public class EtiquetasController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.ventanaJuegos) {
            Main.ventanaMenu = false;
        }
    }

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPegi;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private TableColumn<?, ?> colTitulo;

    @FXML
    private ListView<?> lvEtiquetas;

    @FXML
    private TableView<?> tblJuegos;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    void onBtnAceptar(ActionEvent event) {

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
    void onBtnModificar(ActionEvent event) {

    }

    @FXML
    void onBtnVolver(ActionEvent event) throws IOException {
        if (Main.ventanaMenu) {
            SceneManager.showVentana(event, "menu", 300, 325);
        } else if (Main.ventanaJuegos) {
            Main.ventanaMenu = true;
            SceneManager.showVentana(event, "juegos", 700, 475);
        }
    }
}
