package com.mikefonseta.animalplanet.Database;

import com.mikefonseta.animalplanet.Entity.ProdottoSingoloScontrino;
import com.mikefonseta.animalplanet.Entity.Scontrino;
import com.mikefonseta.animalplanet.Entity.ScontrinoStatistiche;
import com.mikefonseta.animalplanet.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.mikefonseta.animalplanet.data.makePrecise;

public class Statistics {

    public static int dayStatistics(String day) throws SQLException {

        ObservableList<ScontrinoStatistiche> scontrini = FXCollections.observableArrayList();

        if(day == null) { day = "now"; }

        String sql = "SELECT * FROM Scontrino WHERE STRFTIME('%Y-%m-%d', creazione_ordine) = STRFTIME('%Y-%m-%d', DATETIME('"+day+"'))";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int result = 0;
        while (rs.next()) {
            result = 1;
            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),
                    rs.getFloat("sconto"), rs.getFloat("totale")),null));

        }

        data.setDayStatistics(scontrini);
        rs.close();
        statement.close();
        conn.close();

        for(ScontrinoStatistiche s: data.getDayStatistics()){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }

        float totale = 0;
        float totaleAcquisto = 0;
        for(ScontrinoStatistiche s: data.getDayStatistics()){
            totale += s.getScontrino().getTotaleS();
            for(ProdottoSingoloScontrino p: s.getCompScontrino()){
                 totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }
        }

        if(totale != 0) {
            data.setIncassoDay(totale);
            data.setNettoDay(totale - totaleAcquisto);
            data.setRicaricoDay((int) (((totale / totaleAcquisto) - 1) * 100));//
        }else{
            data.setIncassoDay(0);
            data.setNettoDay(0);
            data.setRicaricoDay(0);
        }
        //System.out.println("INCASSO: " + totale + "\nNETTO: " + (totale-totaleAcquisto) + "\nRICARICO: " + (int) (((totale / totaleAcquisto) - 1)*100));
        return result;
    }

    public static int weeklyStatistics(String week) throws SQLException {

        ObservableList<ScontrinoStatistiche> scontrini = FXCollections.observableArrayList();

        if(week == null) { week = "now"; }

        String sql = "SELECT * FROM Scontrino WHERE strftime('%W', creazione_ordine) == strftime('%W', '"+week+"')";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int result = 0;
        while (rs.next()) {
            result = 1;
            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),
                    rs.getFloat("sconto"), rs.getFloat("totale")),null));

        }

        data.setWeeklytStatistics(scontrini);
        rs.close();
        statement.close();
        conn.close();

        for(ScontrinoStatistiche s: data.getWeeklytStatistics()){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }

        float totale = 0;
        float totaleAcquisto = 0;
        for(ScontrinoStatistiche s: data.getWeeklytStatistics()){
            totale += s.getScontrino().getTotaleS();
            for(ProdottoSingoloScontrino p: s.getCompScontrino()){
                totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }
        }

        if(totale != 0) {
            data.setIncassoWeekly(totale);
            data.setNettoWeekly(totale - totaleAcquisto);
            data.setRicaricoWeekly((int) (((totale / totaleAcquisto) - 1) * 100));
        }else{
            data.setIncassoWeekly(0);
            data.setNettoWeekly(0);
            data.setRicaricoWeekly(0);
        }
        //System.out.println("INCASSO: " + totale + "\nNETTO: " + (totale-totaleAcquisto) + "\nRICARICO: " + (int) (((totale / totaleAcquisto) - 1)*100));

        return result;
    }

    public static int monthlyStatistics(String month) throws SQLException {

        ObservableList<ScontrinoStatistiche> scontrini = FXCollections.observableArrayList();

        if(month == null) { month = "now"; }

        String sql = "SELECT * FROM Scontrino WHERE strftime('%m', creazione_ordine) == strftime('%m', '"+month+"')";

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int result = 0;
        while (rs.next()) {
            result = 1;
            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),
                    rs.getFloat("sconto"), rs.getFloat("totale")),null));

        }

        data.setMonthlyStatistics(scontrini);
        rs.close();
        statement.close();
        conn.close();

        for(ScontrinoStatistiche s: data.getMonthlyStatistics()){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }

        float totale = 0;
        float totaleAcquisto = 0;
        for(ScontrinoStatistiche s: data.getMonthlyStatistics()){
            totale += s.getScontrino().getTotaleS();
            for(ProdottoSingoloScontrino p: s.getCompScontrino()){
                totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }
        }
        data.setIncassoMonthly(totale);
        data.setNettoMonthly(totale-totaleAcquisto);
        data.setRicaricoMonthly((int) (((totale / totaleAcquisto) - 1)*100));

        return result;
    }

    public static List<ScontrinoStatistiche> getScontriniStats() throws SQLException {

        ArrayList<ScontrinoStatistiche> scontrini = new ArrayList<>();

        double spese = getSpese();

        String sql = "SELECT id_scontrino,strftime('%Y-%m-%d', creazione_ordine),SUM(sconto),SUM(totale) FROM Scontrino  GROUP BY strftime('%d', creazione_ordine) ORDER BY creazione_ordine ASC ";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("strftime('%Y-%m-%d', creazione_ordine)"),
                    makePrecise(rs.getDouble("SUM(sconto)"),2), makePrecise(rs.getDouble("SUM(totale)"),2)),null));
        }

        for(ScontrinoStatistiche s: scontrini){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }

        float totaleAcquisto = 0;
        for(ScontrinoStatistiche s: scontrini){
            totaleAcquisto = 0;
            for(ProdottoSingoloScontrino p: s.getCompScontrino()){
                totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }
            s.getScontrino().setTotaleS(s.getScontrino().getTotaleS() - totaleAcquisto - spese);
        }

        rs.close();
        statement.close();
        conn.close();

        return scontrini;
    }

    public static double getSpese() throws SQLException {

        double spese = 0;

        String sql = "SELECT * FROM Info";

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            spese = rs.getDouble("spese");
        }
        System.out.println(spese);
        data.setSpese(spese);
        rs.close();
        statement.close();
        conn.close();

        return spese;
    }

    public static int updateSpese(double newValue) throws SQLException {

        int result = 0;
        String sql = "UPDATE Info SET spese="+newValue+" WHERE spese="+data.getSpese()+"";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        result = statement.executeUpdate(sql);

        data.setSpese(newValue);
        statement.close();
        conn.close();

        return result;
    }
}
