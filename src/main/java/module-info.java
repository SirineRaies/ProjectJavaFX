module org.example.projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.mail;
    requires org.apache.pdfbox;
    requires java.desktop;

    opens com.example.projectjavafx.Models to javafx.base;
    opens org.example.projectjavafx to javafx.fxml;
    exports org.example.projectjavafx;
    exports org.example.projectjavafx.Controllers;
    opens org.example.projectjavafx.Controllers to javafx.fxml;
}