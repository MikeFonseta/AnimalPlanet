package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Database.Receipt;
import com.mikefonseta.animalplanet.data;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
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

    public void doScontrino(){
        try {
            float sconto = 0;
            if(scontoTF.getText() != null && !scontoTF.getText().isEmpty() && !scontoTF.getText().isBlank())
            {
                Receipt.addScontrino(Float.parseFloat(scontoTF.getText()), totaleFinale);
            }else {
                Receipt.addScontrino(sconto, totaleFinale);
            }
            Stage stage = (Stage) totale.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
