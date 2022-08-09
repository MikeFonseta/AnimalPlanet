package com.mikefonseta.animalplanet.Database;

import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Product {

    public static ObservableList<Prodotto> getProducts() throws SQLException {

        ObservableList<Prodotto> products = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Prodotto";

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            products.add(new Prodotto(rs.getInt("id"), rs.getString("nome"),
                    rs.getString("categoria"), rs.getFloat("prezzo_di_acquisto"),
                    rs.getFloat("prezzo_di_vendita"), rs.getBoolean("sfuso")));
        }

        rs.close();
        statement.close();
        conn.close();

        return products;
    }

    public static int addProduct(String nome, String categoria, float prezzoDiAcquisto, float prezzoDiVendita, boolean sfuso) throws SQLException {

        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();
        result = st.executeUpdate("INSERT INTO Prodotto(nome,categoria,prezzo_di_acquisto,prezzo_di_vendita,sfuso) VALUES ('"+nome+"', '"+categoria+"', "+prezzoDiAcquisto+", "+prezzoDiVendita+","+sfuso+")");


        if(result == 1){
            ResultSet rs = connection.prepareStatement("select last_insert_rowid();").executeQuery();
            data.getProdotti().add(new Prodotto(rs.getInt("last_insert_rowid()"),nome,categoria,prezzoDiAcquisto,prezzoDiVendita,sfuso));
        }
        st.close();
        connection.close();
        return result;
    }

    public static int deleteProduct(int id) throws SQLException {

        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();
        result = st.executeUpdate("DELETE FROM Prodotto WHERE id="+id);

        data.getProdotti().removeIf(e -> e.getId() == id);

        st.close();
        connection.close();
        return result;
    }

    public static ObservableList<String> getCategorie() throws SQLException {

        ObservableList<String> categorie = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Categoria";

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) { categorie.add(rs.getString("nome_categoria")); }

        rs.close();
        statement.close();
        conn.close();
        data.setCategorie(categorie);
        return categorie;

    }
    public static int modifyProduct(int id, String nome, String categoria, float prezzoDiAcquisto, float prezzoDiVendita, boolean sfuso) throws SQLException {

        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();
        result = st.executeUpdate("UPDATE Prodotto SET nome='"+nome+"'," +
                "categoria='"+categoria+"'," +
                "prezzo_di_acquisto="+prezzoDiAcquisto + "," +
                "prezzo_di_vendita="+prezzoDiVendita + "," +
                "sfuso="+sfuso + " " +
                "WHERE id="+id);

        data.getProdotti().set(data.getProdotti().indexOf(data.getModifyProduct()), new Prodotto(id,nome,categoria,prezzoDiAcquisto,prezzoDiVendita,sfuso));

        st.close();
        connection.close();
        return result;
    }

    public static int addCategoria(String nome) throws SQLException {

        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();
        result = st.executeUpdate("INSERT INTO Categoria(nome_categoria) VALUES ('"+nome+"')");


        data.getCategorie().add(nome);
        st.close();
        connection.close();
        return result;
    }

    public static int deleteCategoria(String nome) throws SQLException {
        Connection connection = null;
        Statement st = null;
        int result = 0;

        connection = DBConnection.getInstance().getConnection();
        st = connection.createStatement();

        result = st.executeUpdate("UPDATE Prodotto SET categoria='Nessuna' WHERE categoria='"+nome+"'");
        if(result > 0) {
            result = st.executeUpdate("DELETE FROM Categoria WHERE nome_categoria='" + nome + "'");
        }

        st.close();
        connection.close();
        return result;
    }

    public static ObservableList<Prodotto> search(String nome, String categoria) throws SQLException {
        ObservableList<Prodotto> products = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Prodotto";

        if(!nome.isEmpty() && !nome.isBlank()) {
            sql += " WHERE nome LIKE '%" + nome + "%' OR " + "'" + nome + "%' OR " + "'%" + nome + "'";
            if (!categoria.isEmpty() && !categoria.isBlank()) {
                sql += " AND categoria='" + categoria + "'";
            }
        }else{
            if (!categoria.isEmpty() && !categoria.isBlank()) {
                sql += " WHERE categoria='" + categoria + "'";
            }
        }

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            products.add(new Prodotto(rs.getInt("id"), rs.getString("nome"),
                    rs.getString("categoria"), rs.getFloat("prezzo_di_acquisto"),
                    rs.getFloat("prezzo_di_vendita"), rs.getBoolean("sfuso")));
        }

        rs.close();
        statement.close();
        conn.close();

        return products;
    }
}
