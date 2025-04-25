package org.example.projectjavafx.Services;

import org.example.projectjavafx.Models.Admin;
import org.example.projectjavafx.Utils.MyConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {

    private static Connection connection;

    public AdminService() {
        connection = MyConnection.getInstance().getCnx();
    }


    public static void ajouter(Admin admin) throws SQLException {
        String sql = "INSERT INTO admins (nomcomplet, email, mdp) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, admin.getNomcomplet());
        preparedStatement.setString(2, admin.getEmail());
        preparedStatement.setString(3, admin.getMdp());
        preparedStatement.executeUpdate();
    }

    // Methode pour mettre à jour un utilisateur
    public static void modifier(Admin admin) throws SQLException {
        String sql = "UPDATE admins SET nomcomplet = ?, email = ?, mdp = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, admin.getNomcomplet());
        preparedStatement.setString(2, admin.getEmail());
        preparedStatement.setString(3, admin.getMdp());
        preparedStatement.setLong(4, admin.getId());
        preparedStatement.executeUpdate();
    }

    // Methode pour supprimer un utilisateur
    public static void supprimer(Admin admin) throws SQLException {
        String sql = "DELETE FROM admins WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, admin.getId());
        preparedStatement.executeUpdate();
    }

    // Methode pour afficher tous les utilisateurs
    public static List<Admin> afficher() throws SQLException {
        String sql = "SELECT * FROM admins";
        List<Admin> admins = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getLong("id"));
                admin.setNomcomplet(rs.getString("nomcomplet"));
                admin.setEmail(rs.getString("email"));
                admin.setMdp(rs.getString("mdp"));
                admins.add(admin);
            }
        }
        return admins;
    }


    // Methode pour récupérer les utilisateurs ajoutés les 7 derniers jours
    public Map<LocalDate, Integer> getAdminCreationLast7Days() throws SQLException {
        Map<LocalDate, Integer> admins = new HashMap<>();

        String sql = "SELECT DATE(created_at) as creation_date, COUNT(*) as creation_count " +
                "FROM admins " +
                "WHERE created_at >= CURDATE() - INTERVAL 7 DAY " +
                "GROUP BY DATE(created_at)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            LocalDate date = resultSet.getDate("creation_date").toLocalDate();
            int count = resultSet.getInt("creation_count");admins.put(date, count);
        }

        return admins;
    }

    public static String encryptPassword(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet  loginAdmin (String email, String mdp) throws SQLException{
        String query = "SELECT * FROM admins WHERE email = ? AND mdp = ?";
        String encryptedPassword = encryptPassword(mdp);

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2, encryptedPassword);

        return preparedStatement.executeQuery();
    }
}
