module com.simpower.simpower {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires json.simple;

    opens com.simpower.controllers to javafx.fxml;
    exports com.simpower.controllers;
    exports com.simpower;
    opens com.simpower to javafx.fxml;
}