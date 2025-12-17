package com.example.practicahibernaterelacionnan.Controller;

import com.example.practicahibernaterelacionnan.DAO.EmpleadoDAO;
import com.example.practicahibernaterelacionnan.Main;
import com.example.practicahibernaterelacionnan.Util.AlertUtils;
import com.example.practicahibernaterelacionnan.Util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void onBtnLogin(ActionEvent actionEvent) {
        cambiarVentana("hola");
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

    private void cambiarVentana(String correo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("menu.fxml"));

            Parent root = fxmlLoader.load();

            MenuController controlador = fxmlLoader.getController();

            Main.correo = correo;

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Menú principal");
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controlador.closeWindow());

            Stage myStage = (Stage) this.btnLogin.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
