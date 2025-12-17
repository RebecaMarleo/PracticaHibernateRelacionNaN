package com.example.practicahibernaterelacionnan.Controller;

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

public class JuegosController {
    @FXML
    private Button btnAnadir;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnGestionar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnQuitar;

    @FXML
    private ComboBox<?> cbPegi;

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
    private ListView<?> lvEtiquetasDelJuego;

    @FXML
    private TableView<?> tblJuegos;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtTitulo;

    @FXML
    void onBtnAnadir(ActionEvent event) {

    }

    @FXML
    void onBtnBorrar(ActionEvent event) {

    }

    @FXML
    void onBtnCrear(ActionEvent event) {

    }

    @FXML
    void onBtnGestionar(ActionEvent event) {

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
            Stage myStage = (Stage) this.tblJuegos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
