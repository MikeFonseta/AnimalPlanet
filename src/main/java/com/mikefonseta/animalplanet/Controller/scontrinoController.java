package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Database.Receipt;
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

public class scontrinoController implements Initializable {

    //AddScontrino
    @FXML
    public Label subtotale,totale;
    @FXML
    public TextField scontoTF;

    private Label totaleMain;
    private double sconto = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subtotale.setText(makePrecise(data.getTotaleScontrino(),2) + " €");
        totale.setText(makePrecise(data.getTotaleScontrino(),2) + " €");
    }

    public void setTotale(Label totaleMain){
        this.totaleMain = totaleMain;
    }

    public void sconto()
    {
        if(scontoTF.getText() != null && !scontoTF.getText().isEmpty() && !scontoTF.getText().isBlank()) {
            sconto = makePrecise(Double.parseDouble(scontoTF.getText()),2);
            totale.setText(makePrecise((data.getTotaleScontrino() - sconto),2) + " €");
        }else
        {
            sconto = 0;
            totale.setText(makePrecise((data.getTotaleScontrino() - sconto),2) + " €");
        }
    }

    public void doScontrino(){
        try {
            if(Receipt.addScontrino(makePrecise(sconto,2), makePrecise(data.getTotaleScontrino()-sconto,2))==0){
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 12.1\n", ButtonType.OK);
                alert1.setTitle("");
                alert1.setHeaderText("");
                alert1.showAndWait();
            }
            data.getListaProdottiScontrino().clear();
            totaleMain.setText("Totale: 0.0€");
            data.setTotaleScontrino(0);
            Stage stage = (Stage) totale.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 12.2\n", ButtonType.OK);
            e.printStackTrace();
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }

}
