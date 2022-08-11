module com.mikefonseta.animalplanet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.mikefonseta.animalplanet to javafx.fxml;
    exports com.mikefonseta.animalplanet;
    exports com.mikefonseta.animalplanet.Entity;
    opens com.mikefonseta.animalplanet.Entity to javafx.fxml;
    exports com.mikefonseta.animalplanet.Controller;
    opens com.mikefonseta.animalplanet.Controller to javafx.fxml;
}