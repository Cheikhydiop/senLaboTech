module org.example.labo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires org.fxyz3d.importers;
    requires javafx.media;
    requires java.logging;
    requires java.desktop;


    opens org.example.labo to javafx.fxml;
    exports org.example.labo;
}