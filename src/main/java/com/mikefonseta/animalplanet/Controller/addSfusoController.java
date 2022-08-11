package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Entity.ProdottoListaScontrino;
import com.mikefonseta.animalplanet.data;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class addSfusoController  {

    @FXML
    public Label nomeProdottoSfuso,prezzoSingoloProdottoSfuso,totaleProdottoSfuso;
    @FXML
    public TextField numPezziSfuso;
    @FXML
    public Button btn;

    private float prezzoTotaleProdottoSfuso = 0;

    private Label totaleScontrino;

    public void updateInfo(){
        if(data.isModifyProdottoSfuso()){
            numPezziSfuso.setText(String.valueOf(data.getProdottoSfuso().getNum_pezzi()));
            btn.setText("AGGIORNA");
        }
        nomeProdottoSfuso.setText(data.getProdottoSfuso().getNome_scontrino());
        prezzoSingoloProdottoSfuso.setText(data.getProdottoSfuso().getPrezzo_singolo() + " €");
        prezzoTotaleProdottoSfuso = data.getProdottoSfuso().getPrezzo_singolo() * data.getProdottoSfuso().getNum_pezzi();
        totaleProdottoSfuso.setText(prezzoTotaleProdottoSfuso + " €");
    }

    public void updatePrice(){
        if(numPezziSfuso.getText() != null && !numPezziSfuso.getText().isEmpty() && !numPezziSfuso.getText().isBlank()) {
            float numPezzi = Float.parseFloat(numPezziSfuso.getText());
            prezzoTotaleProdottoSfuso = data.getProdottoSfuso().getPrezzo_singolo() * numPezzi;
            totaleProdottoSfuso.setText(prezzoTotaleProdottoSfuso + " €");
        }else{
            prezzoTotaleProdottoSfuso = 0;
            totaleProdottoSfuso.setText(prezzoTotaleProdottoSfuso + " €");
        }
    }

    public void addToCart(){
        if(!data.isModifyProdottoSfuso()) {
            data.getScontrino().add(new ProdottoListaScontrino(data.getProdottoSfuso().getId(), data.getProdottoSfuso().getNome_scontrino(), Float.parseFloat(numPezziSfuso.getText()), data.getProdottoSfuso().getPrezzo_singolo(), true));
        }else{
            data.getScontrino().add(data.getScontrino().indexOf(data.getProdottoSfuso()),new ProdottoListaScontrino(data.getProdottoSfuso().getId(), data.getProdottoSfuso().getNome_scontrino(), Float.parseFloat(numPezziSfuso.getText()), data.getProdottoSfuso().getPrezzo_singolo(), true));
        }
        data.setTotaleScontrino(data.getTotaleScontrino() + prezzoTotaleProdottoSfuso);
        totaleScontrino.setText("Totale: " + data.getTotaleScontrino() + "€");
        Stage stage = (Stage) nomeProdottoSfuso.getScene().getWindow();
        stage.close();

        data.setModifyProdottoSfuso(false);
        data.setProdottoSfuso(null);
    }

    public void setLabel(Label totaleScontrino) {
        this.totaleScontrino = totaleScontrino;
    }

}
