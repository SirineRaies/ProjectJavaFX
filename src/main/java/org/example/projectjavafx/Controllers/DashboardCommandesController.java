package org.example.projectjavafx.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projectjavafx.Models.Commande;
import org.example.projectjavafx.Services.CommandeService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardCommandesController implements Initializable {
        @FXML
        private TableView<Commande> table_commandes;
        @FXML
        private TableColumn<Commande, Long> col_id;
        @FXML
        private TableColumn<Commande, Long> col_user;
        @FXML
        private TableColumn<Commande, Long> col_prodID;
        @FXML
        private TableColumn<Commande, String> col_adresse;
        @FXML
        private TableColumn<Commande, String> col_telephone;
        @FXML
        private TextField tf_id;
        @FXML
        private TextField tf_userId;
        @FXML
        private TextField tf_prodID;
        @FXML
        private TextField tf_adresse;
        @FXML
        private TextField tf_tel;
        @FXML
        private Button btn_insert;
        @FXML
        private Button  btn_update;
        @FXML
        private Button  btn_delete;
        @FXML
        private Button btn_Offres;
        @FXML
        private Button btn_Produit;
        @FXML
        private Button btn_Commandes;
        @FXML
        private Button btn_ratings;
        @FXML
        private Button btn_userspage;

        private final CommandeService commandesService = new CommandeService();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_user.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
            col_prodID.setCellValueFactory(new PropertyValueFactory<>("produit_ID"));
            col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            col_telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

            loadCommandesData();

            table_commandes.setOnMouseClicked(this::selectCommande);
            btn_insert.setOnAction(this::addCommande);
            btn_update.setOnAction(this::updateCommande);
            btn_delete.setOnAction(this::deleteCommande);
            //Exemple de fonction lambda
            btn_Offres.setOnAction(event -> goToPage("/DashboardOffres.fxml"));
            btn_Produit.setOnAction(event -> goToPage("/DashboardProduits.fxml"));
            btn_Commandes.setOnAction(event -> goToPage("/DashboardCommandes.fxml"));
            btn_ratings.setOnAction(event -> goToPage("/DashboardRatings.fxml"));
            btn_userspage.setOnAction(event -> goToPage("/DashboardAdmins.fxml"));
        }

        private void goToPage(String fxmlPath) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) table_commandes.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void selectCommande(MouseEvent mouseEvent) {
            Commande selectedCommande = table_commandes.getSelectionModel().getSelectedItem();
            if (selectedCommande != null) {
                tf_id.setText(String.valueOf(selectedCommande.getId()));
                tf_userId.setText(String.valueOf(selectedCommande.getUser_ID()));
                tf_prodID.setText(String.valueOf(selectedCommande.getProduit_ID()));
                tf_adresse.setText(selectedCommande.getAdresse());
                tf_tel.setText(selectedCommande.getTelephone());
            }
        }

        private void addCommande(ActionEvent actionEvent) {
            try {
                String adresse = tf_adresse.getText();
                long userId = Long.parseLong(tf_userId.getText());
                long prodID = Long.parseLong(tf_prodID.getText());
                String telephone = tf_tel.getText();


                Commande commande = new Commande(adresse, telephone,prodID,userId);
                CommandeService.ajouter(commande);
                loadCommandesData();
                showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Commande ajoutée avec succés.");
            } catch (SQLException | NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur d'ajouter une commande : " + e.getMessage());
            }
        }

        private void updateCommande(ActionEvent actionEvent) {
            Commande selectedCommande = table_commandes.getSelectionModel().getSelectedItem();
            if (selectedCommande != null) {
                try {
                    String adresse = tf_adresse.getText();
                    long userId = Long.parseLong(tf_userId.getText());
                    long prodID = Long.parseLong(tf_prodID.getText());
                    String telephone = tf_tel.getText();

                    selectedCommande.setAdresse(adresse);
                    selectedCommande.setUser_ID(userId);
                    selectedCommande.setProduit_ID(prodID);
                    selectedCommande.setTelephone(telephone);

                    CommandeService.modifier(selectedCommande);
                    loadCommandesData();
                    showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Commande est mise à jour avec succés.");
                } catch (SQLException | NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de mis à jour du Commande: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucun Commande est séléctionnée", "S'il vous plait séléctionner une commande à modifier.");
            }
        }

        private void deleteCommande(ActionEvent actionEvent) {
            Commande selectedCommande = table_commandes.getSelectionModel().getSelectedItem();
            if (selectedCommande != null) {
                try {
                    CommandeService.supprimer(selectedCommande);
                    loadCommandesData();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Commande supprimée avec succés .");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la commande : " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucune commande n'est sélectionnée", " S'il vous plait séléctionner une commande à supprimer.");
            }
        }

        private void loadCommandesData() {
            ObservableList<Commande> commandes = FXCollections.observableArrayList();
            try {
                commandes.addAll(CommandeService.afficher());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de charger les données des commandes: " + e.getMessage());
            }
            table_commandes.setItems(commandes);
        }

        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }


}

