package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.EtiquetaDAO;
import com.example.practicahibernaterelacionnan.DAO.JuegoDAO;
import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Modelo.Etiqueta;
import com.example.practicahibernaterelacionnan.Modelo.Juego;
import com.example.practicahibernaterelacionnan.Util.AlertUtils;
import com.example.practicahibernaterelacionnan.Util.HibernateUtil;
import com.example.practicahibernaterelacionnan.Util.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EtiquetasController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();
        if (Main.ventanaJuegos) {
            Main.ventanaMenu = false;
        }
        cargarEtiquetas();
    }

    SessionFactory factory;
    Session session;

    private final EtiquetaDAO etiquetaDAO = new EtiquetaDAO();
    private final JuegoDAO juegoDAO = new JuegoDAO();

    private ArrayList<Etiqueta> etiquetas = new ArrayList<>();

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
    private TableColumn<Juego, Number> colId;

    @FXML
    private TableColumn<Juego, String> colPegi;

    @FXML
    private TableColumn<Juego, Double> colPrecio;

    @FXML
    private TableColumn<Juego, String> colTitulo;

    @FXML
    private ListView<String> lvEtiquetas;

    @FXML
    private TableView<Juego> tblJuegos;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private void onBtnCrear(ActionEvent event) {
        try {
            String nombre = this.txtNombre.getText();
            if (nombre.isEmpty()) {
                AlertUtils.Alerts("ERROR", "Error", "", "Tienes que añadir el nombre para la etiqueta").showAndWait();
            } else {
                // comprueba si ya existe una etiqueta con el mismo nombre
                boolean etiquetaExiste = etiquetaDAO.comprobarEtiqueta(session, nombre);
                // si hay una etiqueta con el nombre proporcionado se muestra un aviso
                if (etiquetaExiste) {
                    AlertUtils.Alerts("ERROR", "Error", "", "Ya existe una etiqueta con ese nombre").showAndWait();
                } else {
                    Etiqueta etiqueta = new Etiqueta(nombre);
                    etiquetaDAO.nuevaEtiqueta(session, etiqueta);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
        cargarEtiquetas();
        vaciarCampos();
    }

    @FXML
    private void onBtnModificar(ActionEvent event) {
        try {
            String nombre = lvEtiquetas.getSelectionModel().getSelectedItem();
            if (nombre == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ninguna etiqueta").showAndWait();
            } else {
                Etiqueta etiqueta = etiquetaDAO.obtenerEtiqueta(session, nombre);
                String nuevoNombre = this.txtNombre.getText();
                if (nuevoNombre.isEmpty()) {
                    AlertUtils.Alerts("ERROR", "Error", "", "Debes introducir un nuevo nombre para la etiqueta").showAndWait();
                } else {
                    Alert alert = AlertUtils.Alerts("CONFIRMATION", "Modificar etiqueta", "", "Se sobreescribirán los datos de la etiqueta seleccionada con los nuevos proporcionados. Este cambio no es reversible. ¿Deseas continuar?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        etiqueta.setNombre(nuevoNombre);
                        etiquetaDAO.modificarEtiqueta(session, etiqueta);
                    }
                }
                cargarEtiquetas();
                vaciarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    private void onBtnBorrar(ActionEvent event) {
        try {
            String nombre = lvEtiquetas.getSelectionModel().getSelectedItem();
            if (nombre == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ninguna etiqueta").showAndWait();
            } else {
                Etiqueta etiqueta = etiquetaDAO.obtenerEtiqueta(session, nombre);
                Alert alert = AlertUtils.Alerts("CONFIRMATION", "Borrar etiqueta", "", "Se va a borrar la etiqueta. Este cambio no es reversible. ¿Deseas continuar?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    etiquetaDAO.borrarEtiqueta(session, etiqueta);
                }
                cargarEtiquetas();
                vaciarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    private void onFilaSeleccionada(MouseEvent event) {
        try {
            String nombre = lvEtiquetas.getSelectionModel().getSelectedItem();
            if (nombre != null) {
                Etiqueta etiqueta = etiquetaDAO.obtenerEtiqueta(session, nombre);
                txtId.setText(String.valueOf(etiqueta.getId()));
                txtNombre.setText(nombre);
                cargarJuegos(nombre);

                this.btnModificar.setDisable(false);
                this.btnBorrar.setDisable(false);
                this.tblJuegos.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    private void onBtnAceptar(ActionEvent event) {

    }

    @FXML
    private void onBtnCancelar(ActionEvent event) {

    }

    @FXML
    private void onBtnVolver(ActionEvent event) throws IOException {
        if (Main.ventanaMenu) {
            SceneManager.showVentana(event, "menu", 300, 325);
        } else if (Main.ventanaJuegos) {
            Main.ventanaMenu = true;
            SceneManager.showVentana(event, "juegos", 700, 475);
        }
    }

    private void cargarEtiquetas() {
        lvEtiquetas.getItems().clear();
        try {
            this.etiquetas = etiquetaDAO.obtenerEtiquetas(session);
            ArrayList<String> nombres = new ArrayList<>();

            for (Etiqueta etiqueta : this.etiquetas) {
                nombres.add(etiqueta.getNombre());
            }
            lvEtiquetas.setItems(FXCollections.observableArrayList(nombres));
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    private void cargarJuegos(String nombre) {
        try {
            tblJuegos.getItems().clear();
            List<Juego> juegos = juegoDAO.cargarJuegosPorEtiqueta(session, nombre);
            ObservableList<Juego> datosJuegos = tblJuegos.getItems();
            datosJuegos.addAll(juegos);

            colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
            colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
            colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
            colPegi.setCellValueFactory(cellData -> cellData.getValue().pegiProperty());

            tblJuegos.setItems(datosJuegos);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    private void vaciarCampos() {
        this.txtId.setText(null);
        this.txtNombre.setText(null);
        cargarJuegos("");

        this.btnModificar.setDisable(true);
        this.btnBorrar.setDisable(true);
        this.tblJuegos.setDisable(true);
    }
}
