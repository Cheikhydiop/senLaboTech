module org.example.demo1 {
    requires javafx.media;
    requires org.fxyz3d.importers;
    requires org.fxyz3d.core;
    requires javafx.fxml;

    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}
