module org.example.projectjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.projectjavafx to javafx.fxml;
    exports org.example.projectjavafx;
    exports org.example.projectjavafx.Controllers;
    opens org.example.projectjavafx.Controllers to javafx.fxml;
}