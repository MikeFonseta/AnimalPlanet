package com.mikefonseta.animalplanet.Database;

import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.Entity.ProdottoListaScontrino;
import com.mikefonseta.animalplanet.Entity.ProdottoSingoloScontrino;
import com.mikefonseta.animalplanet.Entity.Scontrino;
import com.mikefonseta.animalplanet.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Receipt {

    public static ObservableList<Scontrino> getTodayScontrini() throws SQLException {

        ObservableList<Scontrino> scontrini = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Scontrino WHERE substr(creazione_ordine,7) = STRFTIME('%d/%m/%Y', DATETIME('now','localtime'))";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            scontrini.add(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),
                    rs.getFloat("sconto"), rs.getFloat("totale")));
        }

        data.setTodayScontrini(scontrini);
        rs.close();
        statement.close();
        conn.close();

        return scontrini;
    }

    public static int addScontrino(float sconto, float totale) throws SQLException{
        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();
        result = st.executeUpdate("INSERT INTO Scontrino(creazione_ordine,sconto,totale) VALUES (STRFTIME('%H:%M %d/%m/%Y', DATETIME('now','localtime')),"+sconto+","+totale+")");


        if(result == 1){
            ResultSet rs = connection.prepareStatement("select last_insert_rowid();").executeQuery();
            for(ProdottoListaScontrino p: data.getListaProdottiScontrino())
            {
                result = st.executeUpdate("INSERT INTO CompScontrino VALUES('"+p.getNome_scontrino()+"','"
                        +p.getCategoria()+"',"+p.getNum_pezzi()+","+p.getPrezzo_singolo()+","
                        + p.isSfuso()+","+rs.getInt("last_insert_rowid()")+")");
            }
        }

        st.close();
        connection.close();
        return result;
    }

    public static int deleteScontrino(int id) throws SQLException{
        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();
        result = st.executeUpdate("DELETE FROM Scontrino WHERE id_scontrino="+ id);

        data.getTodayScontrini().removeIf(e -> e.getId_scontrinoS() == id);

        st.close();
        connection.close();
        return result;
    }

    public static ObservableList<ProdottoSingoloScontrino> getSingoloScontrino(int id) throws SQLException {

        ObservableList<ProdottoSingoloScontrino> singoloScontrino = FXCollections.observableArrayList();

        String sql = "SELECT * FROM CompScontrino WHERE id_scontrino = "+ id;
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            singoloScontrino.add(new ProdottoSingoloScontrino(rs.getString("nome_prodotto"), rs.getString("categoria")
                    ,rs.getInt("num_pezzi")
                    ,rs.getFloat("prezzo")
                    ,rs.getBoolean("sfuso")
                    ,rs.getInt("id_scontrino")));
        }

        rs.close();
        statement.close();
        conn.close();

        return singoloScontrino;

    }

    public static int update(Scontrino scontrino, float sconto) throws SQLException {

        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();
        result = st.executeUpdate("UPDATE Scontrino SET sconto="+sconto+",totale=" + (scontrino.getTotaleS()+scontrino.getScontoS()-sconto) +  " WHERE id_scontrino="+scontrino.getId_scontrinoS());

        data.getTodayScontrini().set(data.getTodayScontrini().indexOf(scontrino), new Scontrino(scontrino.getId_scontrinoS(),scontrino.getCreazione_ordineS(),sconto,(scontrino.getTotaleS()+scontrino.getScontoS()-sconto)));

        st.close();
        connection.close();
        return result;
    }
}
