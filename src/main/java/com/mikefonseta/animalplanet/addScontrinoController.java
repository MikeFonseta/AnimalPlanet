package com.mikefonseta.animalplanet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class addScontrinoController implements Initializable {

    //AddScontrino
    @FXML
    public Label subtotale,totale;
    @FXML
    public TextField scontoTF;

    private float totaleFinale;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subtotale.setText(data.getTotaleIntScontrino() + " €");
        totale.setText(data.getTotaleIntScontrino() + " €");
    }

    public void sconto()
    {
        if(scontoTF.getText() != null && !scontoTF.getText().isEmpty() && !scontoTF.getText().isBlank()) {
            float sconto = Float.parseFloat(scontoTF.getText());
            totaleFinale = data.getTotaleIntScontrino() - sconto;
            totale.setText(totaleFinale + " €");
        }else if(scontoTF.getText() != null && scontoTF.getText().isEmpty() && scontoTF.getText().isBlank()){
            float sconto = 0;
            totaleFinale = data.getTotaleIntScontrino() - sconto;
            totale.setText(totaleFinale + " €");
        }
    }

    public void close(){
        Stage stage = (Stage) totale.getScene().getWindow();
        stage.close();
    }
}
