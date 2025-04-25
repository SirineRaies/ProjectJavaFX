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
import org.example.projectjavafx.Models.Admin;
import org.example.projectjavafx.Services.AdminService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.example.projectjavafx.Services.AdminService.encryptPassword;

public class DashboardAdminsController  implements Initializable {
        @FXML
        private TableView<Admin> table_admins;
        @FXML
        private TableColumn<Admin, Long> col_id;
        @FXML
        private TableColumn<Admin, String> col_user;
        @FXML
        private TableColumn<Admin, String> col_email;
        @FXML
        private TableColumn<Admin, String> col_mdp;
        @FXML
        private TextField tf_id;
        @FXML
        private TextField tf_nomcomplet;
        @FXML
        private TextField tf_email;
        @FXML
        private TextField tf_mdp;
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

        private final AdminService AdminsService = new AdminService();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_user.setCellValueFactory(new PropertyValueFactory<>("nomcomplet"));
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            col_mdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));

            loadAdminsData();

            table_admins.setOnMouseClicked(this::selectAdmin);
            btn_insert.setOnAction(this::addAdmin);
            btn_update.setOnAction(this::updateAdmin);
            btn_delete.setOnAction(this::deleteAdmin);
            //Exemple de fonction lambda
            btn_Offres.setOnAction(event -> goToPage("/DashboardOffres.fxml"));
            btn_Produit.setOnAction(event -> goToPage("/DashboardProduits.fxml"));
            btn_Commandes.setOnAction(event -> goToPage("/DashboardCommandes.fxml"));
            btn_ratings.setOnAction(event -> goToPage("/DashboardRatings.fxml"));
            btn_userspage.setOnAction(event -> goToPage("/DashboardAdmins.fxml"));
        }

        private void selectAdmin(MouseEvent mouseEvent) {
            Admin selectedAdmin = table_admins.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                tf_id.setText(String.valueOf(selectedAdmin.getId()));
                tf_nomcomplet.setText(String.valueOf(selectedAdmin.getNomcomplet()));
                tf_email.setText(String.valueOf(selectedAdmin.getEmail()));
                tf_mdp.setText(selectedAdmin.getMdp());
            }
        }

        private void goToPage(String fxmlPath) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) table_admins.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void addAdmin(ActionEvent actionEvent) {
            try {
                String nom = tf_nomcomplet.getText();
                String email = tf_email.getText();
                String mdp = tf_mdp.getText();
                String encryptedPassword = encryptPassword(mdp);


                Admin Admin = new Admin(nom, email,encryptedPassword);
                AdminService.ajouter(Admin);
                loadAdminsData();
                showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Admin ajoutée avec succés.");
            } catch (SQLException | NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur d'ajouter une Admin : " + e.getMessage());
            }
        }

        private void updateAdmin(ActionEvent actionEvent) {
            Admin selectedAdmin = table_admins.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                try {
                    String nom = tf_nomcomplet.getText();
                    String email = tf_email.getText();
                    String mdp = tf_mdp.getText();
                    String encryptedPassword = encryptPassword(mdp);

                    selectedAdmin.setNomcomplet(nom);
                    selectedAdmin.setEmail(email);
                    selectedAdmin.setMdp(encryptedPassword);

                    AdminService.modifier(selectedAdmin);
                    loadAdminsData();
                    showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Admin est mis à jour avec succés.");
                } catch (SQLException | NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de mis à jour du Admin: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucun Admin est séléctionnée", "S'il vous plait séléctionner une Admin à modifier.");
            }
        }

        private void deleteAdmin(ActionEvent actionEvent) {
            Admin selectedAdmin = table_admins.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                try {
                    AdminService.supprimer(selectedAdmin);
                    loadAdminsData();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Admin supprimée avec succés .");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la Admin : " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucune Admin n'est sélectionnée", " S'il vous plait séléctionner une Admin à supprimer.");
            }
        }

        private void loadAdminsData() {
            ObservableList<Admin> Admins = FXCollections.observableArrayList();
            try {
                Admins.addAll(AdminService.afficher());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de charger les données des Admins: " + e.getMessage());
            }
            table_admins.setItems(Admins);
        }

        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }

}



