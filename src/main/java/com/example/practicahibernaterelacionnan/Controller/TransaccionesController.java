package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.*;
import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Modelo.*;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TransaccionesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cboxTransaccion.setItems(FXCollections.observableArrayList("Compra", "Venta"));
        this.cboxTransaccion.setValue("Venta");

        factory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();

        ArrayList<Cliente> arrayClientes = clienteDAO.obtenerClientes(session);
        ArrayList<String> listaClientes = new ArrayList<>();
        for (Cliente cliente : arrayClientes) {
            listaClientes.add(cliente.getCorreo());
        }
        this.cboxCliente.setItems(FXCollections.observableArrayList(listaClientes));

        if (Main.ventanaTransOVentanaEtiq != null) {
            if (Main.ventanaTransOVentanaEtiq.isEmpty()) {
                Main.ventanaTransacciones = false;
            }
        }

        if (Main.ventanaClientes || Main.ventanaJuegos) {
            Main.ventanaMenu = false;
        } else {
            Main.ventanaMenu = true;
        }

        this.txtEmpleado.setText(Main.empleadoLogin.getNombre());
        cargarTransacciones();
        cargarJuegos(null, "");

        // si viene desde una pantalla donde se ha seleccionado una transacción se precargan los datos de la transacción elegida
        if (Main.transaccion != null) {
            int idTransaccion = Main.transaccion.getId();
            String tipoTransaccion = Main.transaccion.getTipo();
            this.txtId.setText(String.valueOf(idTransaccion));
            this.cboxCliente.setValue(Main.transaccion.getCliente().getCorreo());
            this.txtEmpleado.setText(Main.transaccion.getEmpleado().getNombre());
            this.cboxTransaccion.setValue(tipoTransaccion);

            cargarJuegos(idTransaccion, tipoTransaccion);
            // hay que recargar el catalogo tambien por si se ha cambiado el tipo de compra
            cargarJuegos(null, tipoTransaccion);

            this.btnModificar.setDisable(false);
            this.btnBorrar.setDisable(false);

            tblTransacciones.getSelectionModel().select(Main.transaccion);
        }
    }

    SessionFactory factory;
    Session session;

    private final TransaccionDAO transaccionDAO = new TransaccionDAO();
    private final JuegoDAO juegoDAO = new JuegoDAO();
    private final Juego_TransaccionDAO juego_transaccionDAO = new Juego_TransaccionDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    private ArrayList<Juego_Transaccion> juegosSeleccionados = new ArrayList<>();

    private String tipoUltimoJuegoSeleccionado = "";

    @FXML
    private Button btnAnadir;

    @FXML
    private Button btnBorrar;

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
    private ComboBox<String> cboxCliente;

    @FXML
    private ComboBox<String> cboxTransaccion;

    @FXML
    private TableColumn<Juego_Transaccion, Number> colCatId;

    @FXML
    private TableColumn<Juego_Transaccion, String> colCatPegi;

    @FXML
    private TableColumn<Juego_Transaccion, Double> colCatPrecio;

    @FXML
    private TableColumn<Juego_Transaccion, String> colCatTitulo;

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
    private TableColumn<Transaccion, String> colTransCliente;

    @FXML
    private TableColumn<Transaccion, String> colTransEmpleado;

    @FXML
    private TableColumn<Transaccion, Number> colTransId;

    @FXML
    private TableColumn<Transaccion, String> colTransTipo;

    @FXML
    private TableColumn<Transaccion, Double> colTransTotal;

    @FXML
    private TableView<Juego_Transaccion> tblCatalogo;

    @FXML
    private TableView<Juego_Transaccion> tblJuegos;

    @FXML
    private TableView<Transaccion> tblTransacciones;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtEmpleado;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtTotal;

    @FXML
    void onCboxTransaccion(ActionEvent event) {
        Integer idTransaccion;
        // al seleccionar la transacción ya se han cargado los juegos en la variable juegosSeleccionados así que si lo volvemos a llamar aquí reseteamos los juegos que hubiesemos añadido nuevos
        cargarJuegos(null, cboxTransaccion.getValue());

        // actualizar también el total
        ObservableList<Juego_Transaccion> datosJuego_transacciones = tblJuegos.getItems();
        calcularTotal(datosJuego_transacciones);
    }

    @FXML
    void onBtnCrear(ActionEvent event) {
        try {
            String correoCliente = cboxCliente.getValue();
            Empleado empleado = Main.empleadoLogin;
            double total = Double.parseDouble(txtTotal.getText());
            String tipo = cboxTransaccion.getValue();
            if (correoCliente.isEmpty() || empleado.getCorreo().isEmpty() || tipo.isEmpty()) {
                AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
            } else {
                Cliente cliente = clienteDAO.obtenerCliente(session, correoCliente);
                Transaccion transaccion = new Transaccion(cliente, empleado, total, tipo);
                transaccionDAO.nuevaTransaccion(session, transaccion);

                // se asocian a la transaccion los juegos seleccionados
                for (Juego_Transaccion juego_transaccion : juegosSeleccionados) {
                    juego_transaccion.setTransaccion(transaccion);
                    juego_transaccionDAO.nuevoJuego_transaccion(session, juego_transaccion);
                }

                cargarTransacciones();
                vaciarCampos();
            }
        } catch (NumberFormatException nfe) {
            AlertUtils.Alerts("INFORMATION", "Sin juegos", "", "Una transacción debe contener al menos un juego").showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnModificar(ActionEvent event) {
        try {
            Transaccion transaccion = tblTransacciones.getSelectionModel().getSelectedItem();
            if (transaccion == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ninguna transacción").showAndWait();
            } else {
                String correoCliente = cboxCliente.getValue();
                Empleado empleado = Main.empleadoLogin;
                double total = Double.parseDouble(txtTotal.getText());
                String tipo = cboxTransaccion.getValue();
                if (correoCliente.isEmpty() || empleado.getNombre().isEmpty() || tipo.isEmpty()) {
                    AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
                } else {
                    Alert alert = AlertUtils.Alerts("CONFIRMATION", "Modificar transacción", "", "Se sobreescribirán los datos de la transacción seleccionada con los nuevos proporcionados. Este cambio no es reversible. ¿Deseas continuar?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Cliente cliente = clienteDAO.obtenerCliente(session, correoCliente);
                        transaccion.setCliente(cliente);
                        transaccion.setEmpleado(empleado);
                        transaccion.setTotal(total);
                        transaccion.setTipo(tipo);
                        transaccionDAO.modificarTransaccion(session, transaccion);

                        if (Main.transaccion != null) {
                            // comprueba si la transacción que se va a modificar es la misma que la última que se había seleccionado y si coinciden actualiza la selección
                            if (Main.transaccion.equals(transaccion)) {
                                Main.transaccion = transaccion;
                            }
                        }

                        // se comprueban los juegos asociados a la transacción
                        int idTransaccion = transaccion.getId();
                        ArrayList<Juego_Transaccion> juegosExistentes = juego_transaccionDAO.obtenerJuegosPorTransaccion(session, idTransaccion);
                        // si el juego está en la tabla de juegos seleccionados pero no estaba asociado a la transacción anteriormente se crea la asociación
                        for (Juego_Transaccion juego_transaccion : juegosSeleccionados) {
                            boolean existia = false;
                            // recorre los juegos que estaban anteriormente asociados a la transacción
                            for (Juego_Transaccion juego_transaccionExistente : juegosExistentes) {
                                // si encuentra el juego lo marca como que ya existía
                                if (juego_transaccion == juego_transaccionExistente) {
                                    existia = true;
                                }
                            }
                            if (!existia) {
                                juego_transaccion.setTransaccion(transaccion);
                                juego_transaccionDAO.nuevoJuego_transaccion(session, juego_transaccion);
                            }
                        }
                        // si el juego estaba asociada a la transacción pero ya no está en la tabla de juegos seleccionados se borra la asociación
                        for (Juego_Transaccion juego_transaccionExistente : juegosExistentes) {
                            boolean seMantiene = false;
                            // recorre los juegos que se encuentran seleccionados actualmente
                            for (Juego_Transaccion juego_transaccion : juegosSeleccionados) {
                                // si encuentra la etiqueta en la lista actual quiere decir que esa etiqueta se mantiene
                                if (juego_transaccionExistente == juego_transaccion) {
                                    seMantiene = true;
                                }
                            }
                            // si la etiqueta no está marcada como que se mantiene quiere decir que hay que quitarla
                            if (!seMantiene) {
                                juego_transaccionDAO.borrarJuego_transaccion(session, juego_transaccionExistente);
                            }
                        }
                        // cualquier otro juego se ignora
                        // alternativamente también se podrían eliminar todos los juegos asociados a esa transacción y volver a crearlos solo con los que estén ahora seleccionados
                        // considero que este segundo método es peor porque al crearse ids para cada asociación quedan muchos huecos vacíos entre medias
                        // además existiría el riesgo de llegar al límite de ids generables demasiado rápido (sé que es muy alto pero cuántos menos ids se creen innecesariamente mejor)
                    }
                    cargarTransacciones();
                    vaciarCampos();
                }
            }
        } catch (NumberFormatException nfe) {
            AlertUtils.Alerts("INFORMATION", "Sin juegos", "", "Una transacción debe contener al menos un juego").showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnBorrar(ActionEvent event) {
        try {
            Transaccion transaccion = tblTransacciones.getSelectionModel().getSelectedItem();
            if (transaccion == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ninguna transacción").showAndWait();
            } else {
                Alert alert = AlertUtils.Alerts("CONFIRMATION", "Borrar transacción", "", "Se va a borrar la transacción. Esta acción no es reversible. ¿Deseas continuar?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (Main.transaccion != null) {
                        // comprueba si la transacción que se va a borrar es la misma que la última que se había seleccionado y si coinciden vacía la selección
                        if (Main.transaccion.equals(transaccion)) {
                            Main.transaccion = null;
                        }
                    }
                    transaccionDAO.borrarTransaccion(session, transaccion);
                }
                cargarTransacciones();
                vaciarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onFilaSeleccionadaTablaTransacciones(MouseEvent event) {
        try {
            Transaccion transaccion = tblTransacciones.getSelectionModel().getSelectedItem();
            if (transaccion != null) {
                int idTransaccion = transaccion.getId();
                String tipoTransaccion = transaccion.getTipo();
                this.txtId.setText(String.valueOf(idTransaccion));
                this.cboxCliente.setValue(transaccion.getCliente().getCorreo());
                this.txtEmpleado.setText(transaccion.getEmpleado().getNombre());
                this.cboxTransaccion.setValue(tipoTransaccion);

                cargarJuegos(idTransaccion, tipoTransaccion);
                // hay que recargar el catalogo tambien por si se ha cambiado el tipo de compra
                cargarJuegos(null, tipoTransaccion);

                this.btnModificar.setDisable(false);
                this.btnBorrar.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnAnadir(ActionEvent event) {
        try {
            Juego_Transaccion juego = tblCatalogo.getSelectionModel().getSelectedItem();
            if (juego != null) {
                try {
                    int cantidad = Integer.valueOf(txtCantidad.getText());

                    tblJuegos.getItems().clear();

                    // comprobar si ya se ha añadido ese juego a la lista
                    boolean juegoYaSeleccionado = false;
                    for (Juego_Transaccion j : juegosSeleccionados) {
                        if (juego.getJuego().getTitulo().equals(j.getJuego().getTitulo())) {
                            int nuevaCantidad = j.getCantidad() + cantidad;
                            j.setCantidad(nuevaCantidad);
                            juegoYaSeleccionado = true;
                        }
                    }
                    // si no se había añadido aun a la lista original de juegos se añade el nuevo seleccionado
                    if (!juegoYaSeleccionado) {
                        juego.setCantidad(Integer.valueOf(cantidad));
                        juegosSeleccionados.add(juego);
                    }

                    ObservableList<Juego_Transaccion> datosJuegosSeleccionados = tblJuegos.getItems();
                    datosJuegosSeleccionados.addAll(juegosSeleccionados);

                    colJueId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                    colJueTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
                    if (cboxTransaccion.getValue().equals("Compra")) {
                        colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioCompraProperty());
                    } else {
                        colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
                    }
                    colJuePegi.setCellValueFactory(cellData -> cellData.getValue().pegiProperty());
                    colJueCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());

                    tblJuegos.setItems(datosJuegosSeleccionados);

                    // calcula el precio de los juegos seleccionados
                    txtTotal.setText(String.valueOf(calcularTotal(juegosSeleccionados)));

                    tblJuegos.setItems(FXCollections.observableArrayList(juegosSeleccionados));
                } catch (NumberFormatException nfe) {
                    AlertUtils.Alerts("INFORMATION", "Sin cantidad", "", "Selecciona una cantidad de juegos a añadir").showAndWait();
                }
            } else {
                AlertUtils.Alerts("INFORMATION", "Selecciona un juego", "", "Selecciona un juego de la tabla de \"Catálogo de juegos\" para añadirlo a la " + cboxTransaccion.getValue()).showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnQuitar(ActionEvent event) {
        try {
            Juego_Transaccion juego = tblJuegos.getSelectionModel().getSelectedItem();
            if (juego != null) {
                try {
                    int cantidad = Integer.valueOf(txtCantidad.getText());

                    tblJuegos.getItems().clear();
                    // a la lista original de juegos se elimina la cantidad seleccionada del juego
                    int nuevaCantidad = juego.getCantidad() - cantidad;
                    juego.setCantidad(nuevaCantidad);
                    // si la cantidad pasa a ser 0 o inferior se elimina el juego de la lista
                    if (juego.getCantidad() <= 0) {
                        juegosSeleccionados.remove(juego);
                    }
                    ObservableList<Juego_Transaccion> datosJuegosSeleccionados = tblJuegos.getItems();
                    datosJuegosSeleccionados.addAll(juegosSeleccionados);

                    colJueId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                    colJueTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
                    if (cboxTransaccion.getValue().equals("Compra")) {
                        colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioCompraProperty());
                    } else {
                        colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
                    }
                    colJuePegi.setCellValueFactory(cellData -> cellData.getValue().pegiProperty());
                    colJueCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());

                    tblJuegos.setItems(datosJuegosSeleccionados);

                    // calcula el precio de los juegos seleccionados
                    txtTotal.setText(String.valueOf(calcularTotal(juegosSeleccionados)));

                    tblJuegos.setItems(FXCollections.observableArrayList(juegosSeleccionados));
                } catch (NumberFormatException nfe) {
                    AlertUtils.Alerts("INFORMATION", "Sin cantidad", "", "Selecciona una cantidad de juegos a quitar").showAndWait();
                }
            } else {
                AlertUtils.Alerts("INFORMATION", "Selecciona una etiqueta", "", "Selecciona una etiqueta de la lista de \"Etiquetas\" para quitársela al juego").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onFilaSeleccionadaTablaJuegos(MouseEvent event) {
        tipoUltimoJuegoSeleccionado = "Juegos";
    }

    @FXML
    void onFilaSeleccionadaTablaCatalogo(MouseEvent event) {
        tipoUltimoJuegoSeleccionado = "Catalogo";
    }

    @FXML
    void onBtnGestionarCatalogo(ActionEvent event) throws IOException {
        Main.ventanaTransacciones = true;
        Main.ventanaTransOVentanaEtiq = "Transacciones";
        try {
            switch (tipoUltimoJuegoSeleccionado) {
                case "Juegos":
                    Main.juego = tblJuegos.getSelectionModel().getSelectedItem().getJuego();
                    break;
                case "Catalogo":
                    Main.juego = tblCatalogo.getSelectionModel().getSelectedItem().getJuego();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {}
        try {
            Main.transaccion = tblTransacciones.getSelectionModel().getSelectedItem();
        } catch (Exception e) {}
        SceneManager.showVentana(event, "juegos", 700, 475);
    }

    @FXML
    void onBtnGestionarClientes(ActionEvent event) throws IOException {
        Main.ventanaTransacciones = true;
        try {
            String correo = cboxCliente.getValue();
            Main.cliente = clienteDAO.obtenerCliente(session, correo);
        } catch (Exception e) {}
        try {
            Main.transaccion = tblTransacciones.getSelectionModel().getSelectedItem();
        } catch (Exception e) {}
        SceneManager.showVentana(event, "clientes", 900, 475);
    }

    @FXML
    void onBtnVolver(ActionEvent event) throws IOException {
        Main.ventanaTransacciones = false;
        if (Main.ventanaMenu) {
            SceneManager.showVentana(event, "menu", 300, 325);
        } else if (Main.ventanaClientes) {
            Main.ventanaMenu = true;
            SceneManager.showVentana(event, "clientes", 900, 475);
        } else if (Main.ventanaJuegos || Main.ventanaTransOVentanaEtiq.equals("Etiquetas")) {
            Main.ventanaMenu = true;
            SceneManager.showVentana(event, "juegos", 700, 475);
        }
    }

    private void cargarTransacciones() {
        try {
            tblTransacciones.getItems().clear();
            List<Transaccion> transacciones = transaccionDAO.obtenerTransacciones(session);
            ObservableList<Transaccion> datosTransacciones = tblTransacciones.getItems();
            datosTransacciones.addAll(transacciones);

            colTransId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
            colTransCliente.setCellValueFactory(cellData -> cellData.getValue().clienteProperty());
            colTransEmpleado.setCellValueFactory(cellData -> cellData.getValue().empleadoProperty());
            colTransTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
            colTransTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());

            tblTransacciones.setItems(datosTransacciones);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    private void cargarJuegos(Integer idTransaccion, String tipo) {
        try {
            if (idTransaccion == null) {
                tblCatalogo.getItems().clear();
                List<Juego_Transaccion> juegos = juego_transaccionDAO.obtenerJuegos(session);
                ObservableList<Juego_Transaccion> datosJuegos = tblCatalogo.getItems();
                datosJuegos.addAll(juegos);

                colCatId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                colCatTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
                if (tipo.equals("Compra")) {
                    colCatPrecio.setCellValueFactory(cellData -> cellData.getValue().precioCompraProperty());
                } else {
                    colCatPrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
                }
                colCatPegi.setCellValueFactory(cellData -> cellData.getValue().pegiProperty());

                tblCatalogo.setItems(datosJuegos);
            } else {
                // los juegos que se cargarán en la tabla de juegos seleccionados
                juegosSeleccionados = juego_transaccionDAO.obtenerJuegosPorTransaccion(session, idTransaccion);
            }
            tblJuegos.getItems().clear();
            ObservableList<Juego_Transaccion> datosJuegosSeleccionados = tblJuegos.getItems();
            datosJuegosSeleccionados.addAll(juegosSeleccionados);

            colJueId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
            colJueTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
            if (tipo.equals("Compra")) {
                colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioCompraProperty());
            } else {
                colJuePrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
            }
            colJuePegi.setCellValueFactory(cellData -> cellData.getValue().pegiProperty());
            colJueCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());

            tblJuegos.setItems(datosJuegosSeleccionados);

            // calcula el precio de los juegos seleccionados
            txtTotal.setText(String.valueOf(calcularTotal(juegosSeleccionados)));
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    private double calcularTotal(List<Juego_Transaccion> juegosSeleccionados) {
        double total = 0;
        for (Juego_Transaccion juego_transaccion : juegosSeleccionados) {
            if (cboxTransaccion.getValue().equals("Compra")) {
                total = total + juego_transaccion.getJuego().getPrecioCompra() * juego_transaccion.getCantidad();
            } else {
                total = total + juego_transaccion.getJuego().getPrecioVenta() * juego_transaccion.getCantidad();
            }
        }
        BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void vaciarCampos() {
        this.txtId.setText(null);
        this.cboxCliente.setValue(null);
        this.txtEmpleado.setText(Main.empleadoLogin.getNombre());
        this.cboxTransaccion.setValue("Compra");
        juegosSeleccionados.clear();
        cargarJuegos(null, "");

        this.btnModificar.setDisable(true);
        this.btnBorrar.setDisable(true);
    }
}
