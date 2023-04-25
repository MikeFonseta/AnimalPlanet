package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Database.Scarico;
import com.mikefonseta.animalplanet.data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class addScaricoController implements Initializable {

    @FXML
    private ChoiceBox<String> fornitore;
    @FXML
    private TextField importo;
    @FXML
    public DatePicker date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            data.setFornitori(Scarico.getFornitori());
            fornitore.setItems(data.getFornitori());
            fornitore.setValue("Nessuno");
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 9", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
        date.setValue(LocalDate.now());
    }

    public void addNewScarico(){
        try {
            if(Scarico.addScarico(fornitore.getValue(), Double.parseDouble(importo.getText()),date.getValue().toString())==1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,  "Scarico aggiunto!", ButtonType.OK);
                alert.setTitle("");
                alert.setHeaderText("");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) fornitore.getScene().getWindow();
                    stage.close();
                }
            }
            Stage stage = (Stage) fornitore.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante la connessione al database", ButtonType.OK);
            alert.setTitle("");
            alert.setHeaderText("");
            System.out.println(e.getMessage());
        }
    }

    public void addFornitore(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addFornitore.fxml"));
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

    public void deleteFornitore(){
        if(!fornitore.getValue().equals("Nessuna")) {
            ButtonType yes = new ButtonType("SI", ButtonBar.ButtonData.BACK_PREVIOUS);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.NEXT_FORWARD);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sicuro di voler eliminare " +
                    fornitore.getValue() + "? \n Ogni scarico con questo fornitore avranno come fornitore 'Nessuno'", yes, no);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();

            if (alert.getResult() == yes) {

                try {
                    if (Scarico.deleteFornitore(fornitore.getValue()) == 1) {
                        for (com.mikefonseta.animalplanet.Entity.Scarico p :data.getScarichi()){
                            if(p.getFornitore().equals(fornitore.getValue())){
                                data.getScarichi().set(data.getScarichi().indexOf(p),
                                        new com.mikefonseta.animalplanet.Entity.Scarico(p.getId(),"Nessuno",p.getImporto(),p.getData_scarico()));
                            }
                        }
                        String deleted = fornitore.getValue();
                        data.getFornitori().remove(fornitore.getValue());
                        fornitore.setValue("Nessuno");
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, deleted + " eliminato correttamente", ButtonType.OK);
                        alert1.setTitle("");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                    }else{
                        Stage stage = (Stage) fornitore.getScene().getWindow();
                        stage.close();
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Errore durante la connessione al database", ButtonType.OK);
                        alert1.setTitle("");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                    }
                } catch (SQLException e) {
                    Stage stage = (Stage) fornitore.getScene().getWindow();
                    stage.close();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Errore durante la connessione al database", ButtonType.OK);
                    alert1.setTitle("");
                    alert1.setHeaderText("");
                    alert1.showAndWait();
                }
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.WARNING, "Impossibile eliminare il fornitore 'Nessuno'", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }

}
