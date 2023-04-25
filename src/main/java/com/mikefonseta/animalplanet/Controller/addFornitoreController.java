package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Database.Product;
import com.mikefonseta.animalplanet.Database.Scarico;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class addFornitoreController {

    @FXML
    private TextField nome;

    public void addFornitore(){
        if(!nome.getText().isBlank() && !nome.getText().isEmpty())
        {
            try {
                if(Scarico.addFornitore(nome.getText()) == 1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fornitore '"+nome.getText() + "' aggiunto!", ButtonType.OK);
                    alert.setTitle("");
                    alert.setHeaderText("");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Stage stage = (Stage) nome.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (SQLException e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 11", ButtonType.OK);
                alert1.setTitle("");
                alert1.setHeaderText("");
                alert1.showAndWait();
            }
        }
    }

}
