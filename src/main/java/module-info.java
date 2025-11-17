module com.example.practicahibernaterelacionnan {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.practicahibernaterelacionnan to javafx.fxml;
    exports com.example.practicahibernaterelacionnan;
}