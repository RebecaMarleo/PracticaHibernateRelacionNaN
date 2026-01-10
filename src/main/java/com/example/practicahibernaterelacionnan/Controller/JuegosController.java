package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.EtiquetaDAO;
import com.example.practicahibernaterelacionnan.DAO.JuegoDAO;
import com.example.practicahibernaterelacionnan.DAO.PegiDAO;
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

public class JuegosController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> listaPegi = pegiDAO.obtenerListaPegi();
        this.cboxPegi.setItems(FXCollections.observableArrayList(listaPegi));

        factory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();

        Main.ventanaJuegos = false;
        if (Main.ventanaTransacciones) {
            Main.ventanaMenu = false;
        }

        cargarJuegos();
        cargarEtiquetas(null);
    }

    SessionFactory factory;
    Session session;

    private final JuegoDAO juegoDAO = new JuegoDAO();
    private final PegiDAO pegiDAO = new PegiDAO();
    private final EtiquetaDAO etiquetaDAO = new EtiquetaDAO();

    private ArrayList<Etiqueta> etiquetas = new ArrayList<>();
    private ArrayList<Etiqueta> etiquetasGlobales = new ArrayList<>();
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> nombresGlobales = new ArrayList<>();

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
    private Button btnVolver;

    @FXML
    private ComboBox<String> cboxPegi;

    @FXML
    private TableColumn<Juego, Number> colId;

    @FXML
    private TableColumn<Juego, String> colPegi;

    @FXML
    private TableColumn<Juego, Double> colPrecio;

    @FXML
    private TableColumn<Juego, String> colTitulo;

    @FXML
    private ListView<String> lvEtiquetasGlobales;

    @FXML
    private ListView<String> lvEtiquetasDelJuego;

    @FXML
    private TableView<Juego> tblJuegos;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPrecioCompra;

    @FXML
    private TextField txtPrecioVenta;

    @FXML
    private TextField txtTitulo;

    @FXML
    void onBtnCrear(ActionEvent event) {
        try {
            String titulo = this.txtTitulo.getText();
            // comprueba si ya existe un juego con el mismo titulo
            boolean juegoExiste = juegoDAO.comprobarJuego(session, titulo);
            // si hay un juego con el titulo proporcionado se muestra un aviso
            if (juegoExiste) {
                AlertUtils.Alerts("ERROR", "Error", "", "Ya existe un juego con ese título").showAndWait();
            } else {
                String precioCompraTxt = this.txtPrecioCompra.getText();
                String precioVentaTxt = this.txtPrecioVenta.getText();
                String pegi = this.cboxPegi.getValue();
                if (titulo.isEmpty() || precioCompraTxt.isEmpty() || precioVentaTxt.isEmpty() || pegi == null) {
                    AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
                } else {
                    // controla que los precios sean números
                    try {
                        double precioCompra = Double.parseDouble(this.txtPrecioCompra.getText());
                        double precioVenta = Double.parseDouble(this.txtPrecioVenta.getText());
                        // comprueba que el precio sea positivo
                        if (precioCompra > 0 && precioVenta > 0) {
                            Juego juego = new Juego(titulo, precioVenta, precioCompra, pegi);
                            // se asocian al juego las etiquetas seleccionadas
                            for (Etiqueta etiqueta : etiquetas) {
                                juego.addEtiqueta(etiqueta);
                            }
                            juegoDAO.nuevoJuego(session, juego);
                            // si todo se ejecuta correctamente recargan juegos y etiquetas y vacía los campos
                            cargarJuegos();
                            cargarEtiquetas(null);
                            vaciarCampos();
                        } else {
                            AlertUtils.Alerts("ERROR", "Error", "", "Los precios no pueden ser iguales o inferiores a 0").showAndWait();
                        }
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                        AlertUtils.Alerts("ERROR", "Error", "", "Los precios deben ser números").showAndWait();
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
            Juego juego = tblJuegos.getSelectionModel().getSelectedItem();
            if (juego == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ningún juego").showAndWait();
            } else {
                String titulo = this.txtTitulo.getText();
                // comprueba si ya existe un juego con el mismo titulo
                boolean juegoExiste = juegoDAO.comprobarJuegoModificar(session, titulo, juego.getId());
                // si hay un juego con el titulo proporcionado se muestra un aviso
                if (juegoExiste) {
                    AlertUtils.Alerts("ERROR", "Error", "", "Ya existe un juego con ese título").showAndWait();
                } else {
                    String precioCompraTxt = this.txtPrecioCompra.getText();
                    String precioVentaTxt = this.txtPrecioVenta.getText();
                    String pegi = this.cboxPegi.getValue();
                    // controla que los precios sean números
                    try {
                        double precioCompra = Double.parseDouble(this.txtPrecioCompra.getText());
                        double precioVenta = Double.parseDouble(this.txtPrecioVenta.getText());
                        // comprueba que el precio sea positivo
                        if (precioCompra > 0 && precioVenta > 0) {
                            if (titulo.isEmpty() || precioCompraTxt.isEmpty() || precioVentaTxt.isEmpty() || pegi == null) {
                                AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
                            } else {
                                Alert alert = AlertUtils.Alerts("CONFIRMATION", "Modificar juego", "", "Se sobreescribirán los datos del juego seleccionado con los nuevos proporcionados. Este cambio no es reversible. ¿Deseas continuar?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    juego.setTitulo(titulo);
                                    juego.setPrecioCompra(precioCompra);
                                    juego.setPrecioVenta(precioVenta);
                                    juego.setPegi(pegi);
                                    // se comprueban las etiquetas asociadas al juego
                                    int idJuego = juego.getId();
                                    ArrayList<Etiqueta> etiquetasExistentes = etiquetaDAO.obtenerEtiquetasPorJuego(session, idJuego);
                                    // si la etiqueta está en la lista de etiquetas pero no estaba asociada al juego anteriormente se crea la asociación
                                    for (Etiqueta etiqueta : etiquetas) {
                                        boolean existia = false;
                                        // recorre las etiquetas que estaban anteriormente asociadas al juego
                                        for (Etiqueta etiquetaExistente : etiquetasExistentes) {
                                            // si encuentra la etiqueta la marca como que ya existía
                                            if (etiqueta == etiquetaExistente) {
                                                existia = true;
                                            }
                                        }
                                        if (!existia) {
                                            juego.addEtiqueta(etiqueta);
                                        }
                                    }
                                    // si la etiqueta estaba asociada al juego pero ya no está en la lista de etiquetas se borra la asociación
                                    for (Etiqueta etiquetaExistente : etiquetasExistentes) {
                                        boolean seMantiene = false;
                                        // recorre las etiquetas que se encuentran seleccionadas actualmente
                                        for (Etiqueta etiqueta : etiquetas) {
                                            // si encuentra la etiqueta en la lista actual quiere decir que esa etiqueta se mantiene
                                            if (etiquetaExistente == etiqueta) {
                                                seMantiene = true;
                                            }
                                        }
                                        // si la etiqueta no está marcada como que se mantiene quiere decir que hay que quitarla
                                        if (!seMantiene) {
                                            juego.removeEtiqueta(etiquetaExistente);
                                        }
                                    }
                                    // cualquier otra etiqueta se ignora
                                    // alternativamente también se podrían eliminar todas las etiquetas asociadas ese juego y volver a crearlas solo con las que estén ahora seleccionadas
                                    // considero que este segundo método es peor porque si se creasen ids para cada asociación quedarían muchos huecos vacíos entre medias
                                    // además existiría el riesgo de llegar al límite de ids generables demasiado rápido (sé que es muy alto pero cuántos menos ids se creen innecesariamente mejor)
                                    juegoDAO.modificarJuego(session, juego);
                                }
                            }
                            cargarJuegos();
                            cargarEtiquetas(null);
                            vaciarCampos();
                        } else {
                            AlertUtils.Alerts("ERROR", "Error", "", "Los precios no pueden ser iguales o inferiores a 0").showAndWait();
                        }
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                        AlertUtils.Alerts("ERROR", "Error", "", "Los precios deben ser números").showAndWait();
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
            Juego juego = tblJuegos.getSelectionModel().getSelectedItem();
            if (juego == null) {
                AlertUtils.Alerts("ERROR", "Error", "", "No has seleccionado ningún juego").showAndWait();
            } else {
                Alert alert = AlertUtils.Alerts("CONFIRMATION", "Borrar juego", "", "Se va a borrar el juego. Este cambio no es reversible. ¿Deseas continuar?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    juegoDAO.borrarJuego(session, juego);
                }
                cargarJuegos();
                cargarEtiquetas(null);
                vaciarCampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onFilaSeleccionadaTablaJuegos(MouseEvent event) {
        try {
            Juego juego = tblJuegos.getSelectionModel().getSelectedItem();
            if (juego != null) {
                int idJuego = juego.getId();
                this.txtId.setText(String.valueOf(idJuego));
                this.txtTitulo.setText(juego.getTitulo());
                this.txtPrecioCompra.setText(String.valueOf(juego.getPrecioCompra()));
                this.txtPrecioVenta.setText(String.valueOf(juego.getPrecioVenta()));
                this.cboxPegi.setValue(juego.getPegi());

                cargarEtiquetas(idJuego);

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
            String nombre = lvEtiquetasGlobales.getSelectionModel().getSelectedItem();
            if (nombre != null) {
                // si no exite ya una etiqueta con ese nombre
                boolean etiquetaExiste = false;
                for (Etiqueta etiqueta : etiquetas) {
                    if (etiqueta.getNombre() == nombre) {
                        etiquetaExiste = true;
                    }
                }
                if (!etiquetaExiste) {
                    Juego juego = tblJuegos.getSelectionModel().getSelectedItem();
                    // si se ha seleccionado un juego se busca con el id
                    if (juego != null) {
                        int idJuego = juego.getId();
                    }
                    nombres = new ArrayList<>();
                    nombresGlobales = new ArrayList<>();

                    for (Etiqueta etiqueta : etiquetas) {
                        nombres.add(etiqueta.getNombre());
                    }

                    // se añade la nueva etiqueta
                    Etiqueta nuevaEtiqueta = etiquetaDAO.obtenerEtiqueta(session, nombre);
                    etiquetas.add(nuevaEtiqueta);
                    nombres.add(nombre);

                    lvEtiquetasDelJuego.setItems(FXCollections.observableArrayList(nombres));

                    // se elimina de las etiquetas globales la nueva etiqueta seleccionada
                    for (Etiqueta etiqueta : this.etiquetasGlobales) {
                        nombresGlobales.add(etiqueta.getNombre());
                    }
                    etiquetasGlobales.remove(nuevaEtiqueta);
                    nombresGlobales.remove(nombre);
                    lvEtiquetasGlobales.setItems(FXCollections.observableArrayList(nombresGlobales));
                }
            } else {
                AlertUtils.Alerts("INFORMATION", "Selecciona una etiqueta", "", "Selecciona una etiqueta de la lista de \"Etiquetas globales\" para añadirla al juego").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnQuitar(ActionEvent event) {
        try {
            String nombre = lvEtiquetasDelJuego.getSelectionModel().getSelectedItem();
            if (nombre != null) {
                Juego juego = tblJuegos.getSelectionModel().getSelectedItem();
                // si se ha seleccionado un juego se busca con el id
                if (juego != null) {
                    int idJuego = juego.getId();
                }
                nombres = new ArrayList<>();

                for (Etiqueta etiqueta : etiquetas) {
                    nombres.add(etiqueta.getNombre());
                }

                // se añade la nueva etiqueta
                Etiqueta nuevaEtiqueta = etiquetaDAO.obtenerEtiqueta(session, nombre);
                etiquetas.remove(nuevaEtiqueta);
                nombres.remove(nombre);

                lvEtiquetasDelJuego.setItems(FXCollections.observableArrayList(nombres));

                // se devuelve a las etiquetas globales la etiqueta seleccionada
                for (Etiqueta etiqueta : this.etiquetasGlobales) {
                    nombresGlobales.add(etiqueta.getNombre());
                }
                etiquetasGlobales.add(nuevaEtiqueta);
                nombresGlobales.add(nombre);
                lvEtiquetasGlobales.setItems(FXCollections.observableArrayList(nombresGlobales));
            } else {
                AlertUtils.Alerts("INFORMATION", "Selecciona una etiqueta", "", "Selecciona una etiqueta de la lista de \"Etiquetas\" para quitársela al juego").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    @FXML
    void onBtnGestionar(ActionEvent event) throws IOException {
        Main.ventanaJuegos = true;
        SceneManager.showVentana(event, "etiquetas", 700, 475);
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

    private void cargarJuegos() {
        try {
            tblJuegos.getItems().clear();
            List<Juego> juegos = juegoDAO.obtenerJuegos(session);
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

    private void cargarEtiquetas(Integer idJuego) {
        lvEtiquetasDelJuego.getItems().clear();
        try {
            // si se ha proporcionado un id para el juego seleccionado
            if (idJuego != null) {
                // las etiquetas que tiene el juego
                this.etiquetas = etiquetaDAO.obtenerEtiquetasPorJuego(session, idJuego);
                nombres = new ArrayList<>();

                for (Etiqueta etiqueta : this.etiquetas) {
                    nombres.add(etiqueta.getNombre());
                }
                lvEtiquetasDelJuego.setItems(FXCollections.observableArrayList(nombres));
                // las etiquetas que no tiene el juego
                this.etiquetasGlobales = etiquetaDAO.obtenerEtiquetasPorJuegoRestantes(session, idJuego);
                nombresGlobales = new ArrayList<>();

                for (Etiqueta etiqueta : this.etiquetasGlobales) {
                    nombresGlobales.add(etiqueta.getNombre());
                }
                lvEtiquetasGlobales.setItems(FXCollections.observableArrayList(nombresGlobales));
            } else {
                // si el id es nulo se cargan todas las etiquetas pero solo en la lista de etiquetas globales
                this.etiquetasGlobales = etiquetaDAO.obtenerEtiquetas(session);
                nombresGlobales = new ArrayList<>();

                for (Etiqueta etiqueta : this.etiquetasGlobales) {
                    nombresGlobales.add(etiqueta.getNombre());
                }
                lvEtiquetasGlobales.setItems(FXCollections.observableArrayList(nombresGlobales));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }

    public void vaciarCampos() {
        this.txtId.setText("");
        this.txtTitulo.setText("");
        this.txtPrecioCompra.setText("");
        this.txtPrecioVenta.setText("");
        this.cboxPegi.setValue(null);

        this.btnModificar.setDisable(true);
        this.btnBorrar.setDisable(true);
    }
}
