package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Database.Receipt;
import com.mikefonseta.animalplanet.Entity.Scontrino;
import com.mikefonseta.animalplanet.data;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.mikefonseta.animalplanet.data.makePrecise;

public class modifyScontrinoController {

    //AddScontrino
    @FXML
    public Label subtotale,totale;
    @FXML
    public TextField scontoTF;

    private double sconto;
    private double scontoIniziale = 0;
    private Scontrino scontrino = null;

    public void setInfo(Scontrino scontrino){
        this.scontrino = scontrino;
        this.scontoIniziale = scontrino.getScontoS();
        this.sconto = scontrino.getScontoS();
        scontoTF.setText(String.valueOf(makePrecise(this.sconto,2)));
        subtotale.setText(scontrino.getTotaleS() + sconto + " €");
        totale.setText(makePrecise((scontrino.getTotaleS() + scontoIniziale - sconto),2) + " €");
    }

    public void sconto()
    {
        if(scontoTF.getText() != null && !scontoTF.getText().isEmpty() && !scontoTF.getText().isBlank()) {
            sconto = makePrecise(Double.parseDouble(scontoTF.getText()),2);
            totale.setText(makePrecise(scontrino.getTotaleS() + scontoIniziale - sconto,2) + " €");
        }else
        {
            sconto = 0;
            totale.setText(makePrecise(scontrino.getTotaleS() + scontoIniziale - sconto,2) + " €");
        }
    }

    public void updateScontrino(){
        try {
            if(Receipt.update(scontrino, makePrecise(sconto,2))==0){
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 12.1\n", ButtonType.OK);
                alert1.setTitle("");
                alert1.setHeaderText("");
                alert1.showAndWait();
            }
            Stage stage = (Stage) totale.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 12.2\n", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }
}
