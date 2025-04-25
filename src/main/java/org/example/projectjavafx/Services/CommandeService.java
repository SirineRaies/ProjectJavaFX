package org.example.projectjavafx.Services;

import org.example.projectjavafx.Models.Commande;
import org.example.projectjavafx.Utils.MyConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandeService{

    private static Connection connection;

    public CommandeService() {
        connection = MyConnection.getInstance().getCnx();
    }

    // Methode pour ajouter une commande
    public static void ajouter(Commande commande) throws SQLException {
        String sql = "INSERT INTO commandes (user_ID, produit_ID, adresse, telephone) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, commande.getUser_ID());
        preparedStatement.setLong(2, commande.getProduit_ID());
        preparedStatement.setString(3, commande.getAdresse());
        preparedStatement.setString(4, commande.getTelephone());
        preparedStatement.executeUpdate();
    }

    // Methode pour mettre à jour une commande
    public static void modifier(Commande commande) throws SQLException {
        String sql = "UPDATE commandes SET user_ID = ?, produit_ID = ?, adresse = ?, telephone = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, commande.getUser_ID());
        preparedStatement.setLong(2, commande.getProduit_ID());
        preparedStatement.setString(3, commande.getAdresse());
        preparedStatement.setString(4, commande.getTelephone());
        preparedStatement.setLong(5, commande.getId());
        preparedStatement.executeUpdate();
    }

    // Methode pour supprimer une commande
    public static void supprimer(Commande commande) throws SQLException {
        String sql = "DELETE FROM commandes WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, commande.getId());
        preparedStatement.executeUpdate();
    }

    // Methode pour afficher toutes les commandes
    public static List<Commande> afficher() throws SQLException {
        String sql = "SELECT * FROM commandes";
        List<Commande> commandes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getLong("id"));
                commande.setUser_ID(rs.getLong("user_ID"));
                commande.setProduit_ID(rs.getLong("produit_ID"));
                commande.setAdresse(rs.getString("adresse"));
                commande.setTelephone(rs.getString("telephone"));
                commandes.add(commande);
            }
        }
        return commandes;
    }


    // Methode pour récupérer les commandes des 7 derniers jours
    public Map<LocalDate, Integer> getCommandeCreationLast7Days() throws SQLException {
        Map<LocalDate, Integer> commandes = new HashMap<>();

        String sql = "SELECT DATE(created_at) as creation_date, COUNT(*) as creation_count " +
                "FROM commandes " +
                "WHERE created_at >= CURDATE() - INTERVAL 7 DAY " +
                "GROUP BY DATE(created_at)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            LocalDate date = resultSet.getDate("creation_date").toLocalDate();
            int count = resultSet.getInt("creation_count");
            commandes.put(date, count);
        }

        // Ensure all days in the last 7 days are represented, even if there were no commandes
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate day = today.minusDays(i);
            commandes.putIfAbsent(day, 0);
        }

        return commandes;
    }
}
