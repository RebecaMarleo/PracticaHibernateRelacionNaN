package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.EmpleadoDAO;
import com.example.practicahibernaterelacionnan.Util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
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
        SceneManager.showVentana(event, "menu", 300, 325);
//        try {
//            String correo = this.txtCorreo.getText();
//            String pass = this.txtPass.getText();
//            if (correo.isEmpty() || pass.isEmpty()) {
//                AlertUtils.Alerts("ERROR", "Error", "", "Hay campos vacíos").showAndWait();
//            } else {
//                String sha256hex1 = DigestUtils.sha256Hex(pass);
//
//                boolean loginCorrecto = empleadoDAO.comprobarCredenciales(correo, sha256hex1);
//
//                if (loginCorrecto) {
//                    cambiarVentana(correo);
//                } else {
//                    AlertUtils.Alerts("ERROR", "Usuario no encontrado", "", "No existe ningún paciente con los datos introducidos").showAndWait();
//                }
//            }
//        } catch (Exception e) {
//            AlertUtils.Alerts("ERROR", "Error", "", "Se ha producido un error").showAndWait();
//        }
    }
}
