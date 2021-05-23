module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires net.bytebuddy;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    opens Controller to javafx.fxml;

    exports Main;
    exports POJO;
    opens Main to javafx.fxml;
}
