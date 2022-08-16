package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Entity.ProdottoListaScontrino;
import com.mikefonseta.animalplanet.data;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addSfusoController  {

    @FXML
    public Label nomeProdottoSfuso,prezzoSingoloProdottoSfuso,totaleProdottoSfuso;
    @FXML
    public TextField numPezziSfuso;
    @FXML
    public Button btn;

    private float prezzoTotaleProdottoSfuso = 0, prezzoTotaleInizialeProdottoSfuso = 0;
    private Label totaleScontrino;

    public void updateInfo(){
        if(data.isModifyProdottoSfuso()){
            prezzoTotaleInizialeProdottoSfuso = data.getProdottoSfuso().getNum_pezzi() * data.getProdottoSfuso().getPrezzo_singolo();
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
            prezzoTotaleProdottoSfuso = data.getProdottoSfuso().getPrezzo_singolo() * 1;
            totaleProdottoSfuso.setText(prezzoTotaleProdottoSfuso + " €");
        }
    }

    public void addToCart(){

        float numPezzi = 1;
        if(numPezziSfuso.getText() != null && !numPezziSfuso.getText().isEmpty() && !numPezziSfuso.getText().isBlank()){
            numPezzi = Float.parseFloat(numPezziSfuso.getText());
        }
        data.getProdottoSfuso().setPrezzo_scontrino(data.getProdottoSfuso().getPrezzo_singolo()*numPezzi);
        data.getProdottoSfuso().setNum_pezzi(numPezzi);
        if(!data.isModifyProdottoSfuso()) {
            data.getListaProdottiScontrino().add(new ProdottoListaScontrino(data.getProdottoSfuso().getId(), data.getProdottoSfuso().getNome_scontrino(), data.getProdottoSfuso().getCategoria(), numPezzi, data.getProdottoSfuso().getPrezzo_singolo(), true));
            //data.setTotaleScontrino(data.getTotaleScontrino() - prezzoTotaleInizialeProdottoSfuso + data.getProdottoSfuso().getPrezzo_singolo() * numPezzi);
        }else{
            data.getListaProdottiScontrino().get(data.getListaProdottiScontrino().indexOf(data.getProdottoSfuso())).setPrezzo_scontrino(data.getProdottoSfuso().getPrezzo_scontrino());
            data.getListaProdottiScontrino().get(data.getListaProdottiScontrino().indexOf(data.getProdottoSfuso())).setNum_pezzi(data.getProdottoSfuso().getNum_pezzi());
            //data.setTotaleScontrino(data.getTotaleScontrino() + data.getProdottoSfuso().getPrezzo_singolo() * numPezzi);
        }
        data.setTotaleScontrino(data.getTotaleScontrino() - prezzoTotaleInizialeProdottoSfuso + data.getProdottoSfuso().getPrezzo_singolo() * numPezzi);
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
