package com.mikefonseta.animalplanet.Database;

import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.Entity.ProdottoListaScontrino;
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

        ObservableList<com.mikefonseta.animalplanet.Entity.Scontrino> scontrini = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Scontrino";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            scontrini.add(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),
                    rs.getFloat("sconto"), rs.getFloat("totale")));
        }

        for (Scontrino s : scontrini) {
            System.out.println(s.getId_scontrino()+ " " +s.getCreazione_ordine());
        }
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
        result = st.executeUpdate("INSERT INTO Scontrino(creazione_ordine,sconto,totale) VALUES (STRFTIME('%H:%M %d/%m/%Y', DATETIME('now')),"+sconto+","+totale+")");


        if(result == 1){
            ResultSet rs = connection.prepareStatement("select last_insert_rowid();").executeQuery();
            for(ProdottoListaScontrino p: data.getListaProdottiScontrino())
            {
                st.executeUpdate("INSERT INTO CompScontrino VALUES('"+p.getNome_scontrino()+"','"
                        +p.getCategoria()+"',"+p.getNum_pezzi()+","+p.getPrezzo_singolo()+","
                        + p.isSfuso()+","+rs.getInt("last_insert_rowid()")+")");
            }
            data.setListaProdottiScontrino(null);
        }
        st.close();
        connection.close();
        return result;
    }

    public static void getSingoloScontrino(int id) throws SQLException {

        String sql = "SELECT * FROM CompScontrino WHERE id_scontrino = "+ id;
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            System.out.println(rs.getString("nome_prodotto")+ " " + rs.getString("prezzo") + " "+
                    rs.getString("categoria") + " " + rs.getInt("num_pezzi") + " "
                    + rs.getString("id_scontrino"));
        }
        rs.close();
        statement.close();
        conn.close();

    }
}
