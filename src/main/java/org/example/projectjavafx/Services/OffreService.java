package org.example.projectjavafx.Services;

import org.example.projectjavafx.Models.Offre;
import org.example.projectjavafx.Utils.MyConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService {
    private static Connection connection;

    public OffreService() {
        connection = MyConnection.getInstance().getCnx();
    }

    // Méthode pour ajouter une offre
    public void ajouter(Offre offre) throws SQLException {
        String sql = "INSERT INTO offres (offre, contenu,produit_id) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, offre.getOffre());
        preparedStatement.setString(2, offre.getContenu());
        preparedStatement.setLong(3, offre.getProduit_Id());
        preparedStatement.executeUpdate();
    }

    // Méthode pour mettre à jour une offre
    public void modifier(Offre offre) throws SQLException {
        String sql = "UPDATE offres SET offre = ?, contenu = ?, produit_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, offre.getOffre());
        preparedStatement.setString(2, offre.getContenu());
        preparedStatement.setLong(3, offre.getProduit_Id());
        preparedStatement.setLong(4, offre.getId());
        preparedStatement.executeUpdate();
    }

    // Méthode pour supprimer une offre
    public void supprimer(Offre offre) throws SQLException {
        String sql = "DELETE FROM offres WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, offre.getId());
        preparedStatement.executeUpdate();
    }



    // Méthode pour afficher toutes les offres
    public List<Offre> afficher() throws SQLException {
        String sql = "SELECT * FROM offres";
        List<Offre> offres = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Offre offre = new Offre();
                offre.setId(rs.getLong("id"));
                offre.setOffre(rs.getString("offre"));
                offre.setContenu(rs.getString("contenu"));
                offre.setProduit_Id(rs.getLong("produit_Id"));
                offres.add(offre);
            }
        }
        return offres;
    }
    public List<Offre> recuperer() throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String sql = "SELECT * FROM offres";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Offre offre = new Offre();
            offre.setId(rs.getLong("id"));
            offre.setOffre(rs.getString("offre"));
            offre.setContenu(rs.getString("contenu"));
            offre.setProduit_Id(rs.getLong("produit_Id"));
            offres.add(offre);
        }
        return offres;
    }

}
