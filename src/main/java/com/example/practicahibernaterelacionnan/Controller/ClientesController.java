package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.ClienteDAO;
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

        Main.ventanaClientes = false;

        if (Main.ventanaTransacciones) {
            Main.ventanaMenu = false;
        }

        cargarClientes();

        // si viene desde una pantalla donde se ha seleccionado un cliente se precargan los datos del cliente elegido
        if (Main.cliente != null) {
            int idCliente = Main.cliente.getId();
            this.txtId.setText(String.valueOf(idCliente));
            this.txtCorreo.setText(Main.cliente.getCorreo());
            this.txtNombre.setText(String.valueOf(Main.cliente.getNombre()));

            cargarTransacciones(idCliente);

            this.btnModificar.setDisable(false);
            this.btnBorrar.setDisable(false);

            tblClientes.getSelectionModel().select(Main.cliente);
        }
        // si había una transacción seleccionada se vuelve a cargar
        if (Main.transaccion != null) {
            int idTransaccion = Main.transaccion.getId();
            Transaccion transaccion = transaccionDAO.obtenerTransaccion(session, idTransaccion);
            this.txtEmpleado.setText(transaccion.getEmpleado().getNombre());
            this.txtTipo.setText(transaccion.getTipo());
            this.txtTotal.setText(String.valueOf(transaccion.getTotal()));

            cargarJuegos(idTransaccion, transaccion.getTipo());

            lvTransacciones.getSelectionModel().select(Main.transaccion.getId());
        }
    }

    SessionFactory factory;
    Session session;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final TransaccionDAO transaccionDAO = new TransaccionDAO();
    private final Juego_TransaccionDAO juego_transaccionDAO = new Juego_TransaccionDAO();

    private ArrayList<Transaccion> transacciones = new ArrayList<>();

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnGestionar;

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
    private TableColumn<Juego_Transaccion, Number> colJueCantidad;

    @FXML
    private TableColumn<Juego_Transaccion, Number> colJueId;

    @FXML
    private TableColumn<Juego_Transaccion, String> colJuePegi;

    @FXML
    private TableColumn<Juego_Transaccion, Double> colJuePrecio;

    @FXML
    private TableColumn<Juego_Transaccion, String> colJueTitulo;

    @FXML
    private ListView<String> lvTransacciones;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableView<Juego_Transaccion> tblJuegos;

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

                                if (Main.cliente != null) {
                                    // comprueba si el cliente que se va a modificar es el mismo que el último que se había seleccionado y si coinciden actualiza la selección
                                    if (Main.cliente.equals(cliente)) {
                                        Main.cliente = cliente;
                                    }
                                }
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
                    if (Main.cliente != null) {
                        // comprueba si el cliente que se va a borrar es el misma que el último que se había seleccionado y si coinciden vacía la selección
                        if (Main.cliente.equals(cliente)) {
                            Main.cliente = null;
                        }
                    }
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
        } catch (NumberFormatException nfe) {
            // si seleccionas una fila que no tenga valor provocará un error al intentar parsearlo
            // no hay que hacer ninguna acción cuando esto pasa pero tampoco es correcto que salte un error
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnGestionar(ActionEvent event) throws IOException {
        Main.ventanaClientes = true;
        try {
            int idTransaccion = Integer.parseInt(lvTransacciones.getSelectionModel().getSelectedItem());
            Main.transaccion = transaccionDAO.obtenerTransaccion(session, idTransaccion);
        } catch (Exception e) {}
        try {
            Main.cliente = tblClientes.getSelectionModel().getSelectedItem();
        } catch (Exception e) {}
        SceneManager.showVentana(event, "transacciones", 1000, 575);
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
            if (idTransaccion != null) {
                List<Juego_Transaccion> juegos = juego_transaccionDAO.obtenerJuegosPorTransaccion(session, idTransaccion);
                ObservableList<Juego_Transaccion> datosJuegos = tblJuegos.getItems();
                datosJuegos.addAll(juegos);

                colJueId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                colJueTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
                if (tipo.equals("Venta")) {
                    colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
                } else {
                    colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioCompraProperty());
                }
                colJuePegi.setCellValueFactory(cellData -> cellData.getValue().pegiProperty());
                colJueCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());

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
