module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    opens org.example to javafx.fxml;

    exports org.example;
}
