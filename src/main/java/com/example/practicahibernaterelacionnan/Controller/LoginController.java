package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.EmpleadoDAO;
import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Util.AlertUtils;
import com.example.practicahibernaterelacionnan.Util.HibernateUtil;
import com.example.practicahibernaterelacionnan.Util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();
        session = HibernateUtil.getSession();
        btnLogin.setDefaultButton(true);
    }

    SessionFactory factory;
    Session session;

    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPass;

    @FXML
    public void initialize() {
        btnLogin.setDefaultButton(true);
    }

    @FXML
    public void onBtnLogin(ActionEvent event) throws IOException {
        try {
            String correo = this.txtCorreo.getText();
            String pass = this.txtPass.getText();
            if (correo.isEmpty() || pass.isEmpty()) {
                AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
            } else {
                String sha256hex1 = DigestUtils.sha256Hex(pass);

                boolean loginCorrecto = empleadoDAO.comprobarCredenciales(session, correo, sha256hex1);

                if (loginCorrecto) {
                    Main.empleadoLogin = empleadoDAO.obtenerEmpleado(session, correo);
                    SceneManager.showVentana(event, "menu", 300, 325);
                } else {
                    AlertUtils.Alerts("ERROR", "Usuario no encontrado", "", "No existe ningún empleado con los datos introducidos").showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
        }
    }
}
