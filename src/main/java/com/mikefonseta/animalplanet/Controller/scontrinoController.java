package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.data;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class scontrinoController implements Initializable {

    //AddScontrino
    @FXML
    public Label subtotale,totale;
    @FXML
    public TextField scontoTF;
    private float totaleFinale;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subtotale.setText(data.getTotaleScontrino() + " €");
        totale.setText(data.getTotaleScontrino() + " €");
    }

    public void sconto()
    {
        if(scontoTF.getText() != null && !scontoTF.getText().isEmpty() && !scontoTF.getText().isBlank()) {
            float sconto = Float.parseFloat(scontoTF.getText());
            totaleFinale = data.getTotaleScontrino() - sconto;
            totale.setText(totaleFinale + " €");
        }else if(scontoTF.getText() != null && scontoTF.getText().isEmpty() && scontoTF.getText().isBlank()){
            float sconto = 0;
            totaleFinale = data.getTotaleScontrino() - sconto;
            totale.setText(totaleFinale + " €");
        }
    }

}
