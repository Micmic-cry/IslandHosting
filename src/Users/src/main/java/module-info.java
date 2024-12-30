module com.example.finalalright {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.finalalright to javafx.fxml;
    exports com.example.finalalright;
}