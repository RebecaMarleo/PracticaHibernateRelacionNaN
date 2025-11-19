package com.example.practicahibernaterelacionnan.Util;

import javafx.scene.control.Alert;

public class AlertUtils {
    public static Alert Alerts(String tipoAlert, String tituloAlert, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.valueOf(tipoAlert.toUpperCase()));
        alert.setTitle(tituloAlert);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
}
