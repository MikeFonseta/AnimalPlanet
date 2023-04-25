package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Entity.Scarico;
import com.mikefonseta.animalplanet.data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class fornitoriController implements Initializable {

    @FXML
    private TableView<Scarico> listaScarichi;
    @FXML
    private ChoiceBox<String> fornitori;
    @FXML
    public TableColumn<Scarico, Integer> id;
    @FXML
    public TableColumn<Scarico, String> fornitore;
    @FXML
    public TableColumn<Scarico, Double> importo;
    @FXML
    public TableColumn<Scarico, String> data_scarico;
    @FXML
    public DatePicker dp;
    @FXML
    public Label lbl_tot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fornitore.setCellValueFactory(new PropertyValueFactory<>("fornitore"));
        importo.setCellValueFactory(new PropertyValueFactory<>("importo"));
        data_scarico.setCellValueFactory(new PropertyValueFactory<>("data_scarico"));
        lbl_tot.setText("");
        dp.setValue(LocalDate.now());
        dp.setShowWeekNumbers(false);

        try {
            data.setFornitori(com.mikefonseta.animalplanet.Database.Scarico.getFornitori());
            searchScarico();
            fornitori.setItems(data.getFornitori());
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 13 (Fornitori)\n", ButtonType.OK);
            e.printStackTrace();
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
        updateTotale();
    }

    private void updateTotale()
    {
        double total = 0.0f;

        for(Scarico s :data.getScarichi()) {
            total += s.getImporto();
        }
        lbl_tot.setText("Totale " + data.makePrecise(total, 2));
    }

    public void searchScarico(){
        try {
            data.setScarichi(com.mikefonseta.animalplanet.Database.Scarico.search(fornitori.getValue(), String.valueOf(dp.getValue())));
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 5", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
        listaScarichi.setItems(data.getScarichi());
        updateTotale();
    }

    public void svuotaListaScarichi(){
        dp.setValue(LocalDate.now());
        fornitori.setValue("");
        lbl_tot.setText("");
        try {
            data.setScarichi(com.mikefonseta.animalplanet.Database.Scarico.getScarichi());
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 6", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
        listaScarichi.setItems(data.getScarichi());
        updateTotale();
    }


    public void add() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addScarico.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 2 (Fornitori)", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }

    public void modify() {
        if(listaScarichi.getSelectionModel().getSelectedIndex() != -1) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/modifyScarico.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                modifyScaricoController modifyScaricoController = fxmlLoader.getController();
                modifyScaricoController.loadScarico(data.getScarichi().get(listaScarichi.getSelectionModel().getSelectedIndex()));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);

                listaScarichi.setItems(data.getScarichi());
            } catch (IOException e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 4 (Fornitori)", ButtonType.OK);
                alert1.setTitle("");
                alert1.setHeaderText("");
                alert1.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nessun elemento selezionato", ButtonType.OK);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

    public void delete()
    {
        if(listaScarichi.getSelectionModel().getSelectedIndex() != -1) {
            ButtonType yes = new ButtonType("SI", ButtonBar.ButtonData.BACK_PREVIOUS);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.NEXT_FORWARD);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sicuro di voler eliminare lo scarico fatto in data "+
                    data.getScarichi().get(listaScarichi.getSelectionModel().getSelectedIndex()).getData_scarico() +"?", yes, no);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();

            if (alert.getResult() == yes) {
                int id = data.getScarichi().get(listaScarichi.getSelectionModel().getSelectedIndex()).getId();
                String fornitore = data.getScarichi().get(listaScarichi.getSelectionModel().getSelectedIndex()).getFornitore();
                String date = data.getScarichi().get(listaScarichi.getSelectionModel().getSelectedIndex()).getData_scarico();
                try {
                    if(com.mikefonseta.animalplanet.Database.Scarico.deleteScarico(id) == 1)
                    {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Scarico ("+fornitore+") del " + date + " eliminato correttamente", ButtonType.OK);
                        alert1.setTitle("");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                    }
                } catch (SQLException e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Eliminazione non riuscita\nCodice errore: 3", ButtonType.OK);
                    alert1.setTitle("");
                    alert1.setHeaderText("");
                    alert1.showAndWait();
                }
            }
            updateTotale();

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nessun elemento selezionato", ButtonType.OK);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

}
