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

    opens com.simpower to javafx.fxml;
    exports com.simpower;
    exports com.simpower.view.menu;
    opens com.simpower.view.menu to javafx.fxml;
}