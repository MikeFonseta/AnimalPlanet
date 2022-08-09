package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Database.Product;
import com.mikefonseta.animalplanet.Entity.Prodotto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class modifyProdcutController {

    @FXML
    private TextField nome;
    @FXML
    private ChoiceBox<String> categoria;
    @FXML
    private TextField prezzo_di_acquisto;
    @FXML
    private TextField prezzo_di_vendita;
    @FXML
    private RadioButton sfuso;

    public void loadProduct(Prodotto prodotto){
        data.setModifyProduct(prodotto);
        nome.setText(prodotto.getNome());
        try {
            data.setCategorie(Product.getCategorie());
            categoria.setItems(data.getCategorie());
            categoria.setValue(prodotto.getCategoria());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante la connessione al database", ButtonType.OK);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
        prezzo_di_acquisto.setText(String.valueOf(prodotto.getPrezzoDiAcquisto()));
        prezzo_di_vendita.setText(String.valueOf(prodotto.getPrezzoDiVendita()));
        sfuso.setSelected(prodotto.isSfuso());
    }

    public void modifyProduct(){
        try {
            if(Product.modifyProduct(data.getModifyProduct().getId(), nome.getText(),categoria.getValue(),Float.parseFloat(prezzo_di_acquisto.getText()),Float.parseFloat(prezzo_di_vendita.getText()),sfuso.isSelected())==1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,  "Prodotto aggiornato!", ButtonType.OK);
                alert.setTitle("");
                alert.setHeaderText("");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) nome.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante la connessione al database", ButtonType.OK);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
        data.setModifyProduct(null);
    }

    public void addCategoria(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addCategoria.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {

        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setResizable(false);
    }

    public void deleteCategoria(){
        if(!categoria.getValue().equals("Nessuna")) {
            ButtonType yes = new ButtonType("SI", ButtonBar.ButtonData.BACK_PREVIOUS);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.NEXT_FORWARD);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sicuro di voler eliminare " +
                    categoria.getValue() + "? \n Tutti i prodotti con questa categoria avranno come categoria 'Nessuna'", yes, no);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();

            if (alert.getResult() == yes) {
                try {
                    if (Product.deleteCategoria(categoria.getValue()) == 1) {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, categoria + " eliminata correttamente", ButtonType.OK);
                        alert1.setTitle("");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                    }
                } catch (SQLException e) {
                    Stage stage = (Stage) categoria.getScene().getWindow();
                    stage.close();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Errore durante la connessione al database", ButtonType.OK);
                    alert1.setTitle("");
                    alert1.setHeaderText("");
                    alert1.showAndWait();
                }
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.WARNING, "Impossibile eliminare la categoria 'Nessuna'", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }
}
