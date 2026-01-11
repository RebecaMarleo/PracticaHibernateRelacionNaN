package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.ClienteDAO;
import com.example.practicahibernaterelacionnan.DAO.JuegoDAO;
import com.example.practicahibernaterelacionnan.DAO.Juego_TransaccionDAO;
import com.example.practicahibernaterelacionnan.DAO.TransaccionDAO;
import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Modelo.*;
import com.example.practicahibernaterelacionnan.Util.AlertUtils;
import com.example.practicahibernaterelacionnan.Util.HibernateUtil;
import com.example.practicahibernaterelacionnan.Util.SceneManager;
import com.example.practicahibernaterelacionnan.Util.Validar;
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

public class ClientesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();

        if (Main.ventanaTransacciones) {
            Main.ventanaMenu = false;
        }

        cargarClientes();
    }

    SessionFactory factory;
    Session session;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final TransaccionDAO transaccionDAO = new TransaccionDAO();
    private final JuegoDAO juegoDAO = new JuegoDAO();
    private final Juego_TransaccionDAO juego_transaccionDAO = new Juego_TransaccionDAO();

    private ArrayList<Transaccion> transacciones = new ArrayList<>();

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Cliente, String> colCliCorreo;

    @FXML
    private TableColumn<Cliente, Number> colCliId;

    @FXML
    private TableColumn<Cliente, String> colCliNombre;

    @FXML
    private TableColumn<Juego, Number> colJueId;

    @FXML
    private TableColumn<Juego, String> colJuePegi;

    @FXML
    private TableColumn<Juego, Double> colJuePrecio;

    @FXML
    private TableColumn<Juego, String> colJueTitulo;

    @FXML
    private ListView<String> lvCantidades;

    @FXML
    private ListView<String> lvTransacciones;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableView<Juego> tblJuegos;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtEmpleado;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTipo;

    @FXML
    private TextField txtTotal;

    @FXML
    void onBtnCrear(ActionEvent event) {
        try {
            String correo = this.txtCorreo.getText();
            // comprueba si ya existe un cliente con el mismo correo
            boolean clienteExiste = clienteDAO.comprobarCliente(session, correo);
            // si hay un cliente con el correo proporcionado se muestra un aviso y muestra los datos de ese cliente
            if (clienteExiste) {
                AlertUtils.Alerts("ERROR", "Error", "", "Ya existe un cliente con ese correo").showAndWait();
                Cliente cliente = clienteDAO.obtenerCliente(session, correo);
                tblClientes.getSelectionModel().select(cliente);
                cargarTransacciones(cliente.getId());
            } else {
                String nombre = txtNombre.getText();
                if (correo.isEmpty() || nombre.isEmpty()) {
                    AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
                } else {
                    boolean datosValidos = true;
                    if (!Validar.validarCorreo(correo)) {
                        AlertUtils.Alerts("ERROR", "Error", "", "El correo debe tener el formato correcto").showAndWait();
                        datosValidos = false;
                    }
                    if (!Validar.validarNombre(nombre)) {
                        AlertUtils.Alerts("ERROR", "Error", "", "El nombre no puede contener números ni caracteres especiales").showAndWait();
                        datosValidos = false;
                    }

                    if (datosValidos) {
                        Cliente cliente = new Cliente(correo, nombre);
                        clienteDAO.nuevoCliente(session, cliente);

                        cargarClientes();
                        vaciarCampos();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnModificar(ActionEvent event) {
        try {
            Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
            if (cliente == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ningún cliente").showAndWait();
            } else {
                String correo = this.txtCorreo.getText();
                // comprueba si ya existe un cliente con el mismo correo
                boolean clienteExiste = clienteDAO.comprobarClienteModificar(session, correo, cliente.getId());
                // si hay un cliente con el titulo proporcionado se muestra un aviso y muestra los datos de ese cliente
                if (clienteExiste) {
                    AlertUtils.Alerts("ERROR", "Error", "", "Ya existe un cliente con ese correo").showAndWait();
                    Cliente clienteExistente = clienteDAO.obtenerCliente(session, correo);
                    tblClientes.getSelectionModel().select(clienteExistente);
                    cargarTransacciones(clienteExistente.getId());
                } else {
                    String nombre = txtNombre.getText();
                    if (correo.isEmpty() || nombre.isEmpty()) {
                        AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
                    } else {
                        boolean datosValidos = true;
                        if (!Validar.validarCorreo(correo)) {
                            AlertUtils.Alerts("ERROR", "Error", "", "El correo debe tener el formato correcto").showAndWait();
                            datosValidos = false;
                        }
                        if (!Validar.validarNombre(nombre)) {
                            AlertUtils.Alerts("ERROR", "Error", "", "El nombre no puede contener números ni caracteres especiales").showAndWait();
                            datosValidos = false;
                        }

                        if (datosValidos) {
                            Alert alert = AlertUtils.Alerts("CONFIRMATION", "Modificar cliente", "", "Se sobreescribirán los datos del cliente seleccionado con los nuevos proporcionados. Este cambio no es reversible. ¿Deseas continuar?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                cliente.setCorreo(correo);
                                cliente.setNombre(nombre);
                                clienteDAO.modificarCliente(session, cliente);
                            }
                            cargarClientes();
                            vaciarCampos();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnBorrar(ActionEvent event) {
        try {
            Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
            if (cliente == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ningún cliente").showAndWait();
            } else {
                Alert alert = AlertUtils.Alerts("CONFIRMATION", "Borrar cliente", "", "Se va a borrar el cliente. Esta acción no es reversible. ¿Deseas continuar?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    clienteDAO.borrarCliente(session, cliente);
                }
                cargarClientes();
                vaciarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onFilaSeleccionadaTablaClientes(MouseEvent event) {
        try {
            Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
            if (cliente != null) {
                int idCliente = cliente.getId();
                this.txtId.setText(String.valueOf(idCliente));
                this.txtCorreo.setText(cliente.getCorreo());
                this.txtNombre.setText(String.valueOf(cliente.getNombre()));

                cargarTransacciones(idCliente);

                this.btnModificar.setDisable(false);
                this.btnBorrar.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onFilaSeleccionadaTransacciones(MouseEvent event) {
        try {
            int idTransaccion = Integer.parseInt(lvTransacciones.getSelectionModel().getSelectedItem());
            Transaccion transaccion = transaccionDAO.obtenerTransaccion(session, idTransaccion);
            this.txtEmpleado.setText(transaccion.getEmpleado().getNombre());
            this.txtTipo.setText(transaccion.getTipo());
            this.txtTotal.setText(String.valueOf(transaccion.getTotal()));

            cargarJuegos(idTransaccion, transaccion.getTipo());
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
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

    private void cargarClientes() {
        try {
            tblClientes.getItems().clear();
            List<Cliente> clientes = clienteDAO.obtenerClientes(session);
            ObservableList<Cliente> datosClientes = tblClientes.getItems();
            datosClientes.addAll(clientes);

            colCliId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
            colCliCorreo.setCellValueFactory(cellData -> cellData.getValue().correoProperty());
            colCliNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

            tblClientes.setItems(datosClientes);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    private void cargarTransacciones(Integer idCliente) {
        try {
            lvTransacciones.getItems().clear();
            if (idCliente != null) {
                this.transacciones = transaccionDAO.obtenerTransaccionesPorCliente(session, idCliente);
                ArrayList<String> ids = new ArrayList<>();

                for (Transaccion transaccion : this.transacciones) {
                    ids.add(String.valueOf(transaccion.getId()));
                }
                lvTransacciones.setItems(FXCollections.observableArrayList(ids));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    private void cargarJuegos(Integer idTransaccion, String tipo) {
        try {
            tblJuegos.getItems().clear();
            lvCantidades.getItems().clear();
            if (idTransaccion != null) {
                List<Juego> juegos = juegoDAO.obtenerJuegosPorTransaccion(session, idTransaccion);
                ObservableList<Juego> datosJuegos = tblJuegos.getItems();
                datosJuegos.addAll(juegos);

                colJueId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                colJueTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
                if (tipo.equals("Venta")) {
                    colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
                } else {
                    colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioCompraProperty());
                }
                colJuePegi.setCellValueFactory(cellData -> cellData.getValue().pegiProperty());

                // mostrar también las cantidades de cada juego
                ArrayList<Juego_Transaccion> juego_transacciones = juego_transaccionDAO.obtenerJuegosPorTransaccion(session, idTransaccion);
                ArrayList<String> cantidades = new ArrayList<>();
                for (Juego_Transaccion juego_transaccion : juego_transacciones) {
                    cantidades.add(String.valueOf(juego_transaccion.getCantidad()));
                }
                lvCantidades.setItems(FXCollections.observableArrayList(cantidades));

                tblJuegos.setItems(datosJuegos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    private void vaciarCampos() {
        this.txtId.setText(null);
        this.txtCorreo.setText(null);
        this.txtNombre.setText(null);
        cargarTransacciones(null);
        cargarJuegos(null, "");

        this.btnModificar.setDisable(true);
        this.btnBorrar.setDisable(true);
    }
}
