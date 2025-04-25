package org.example.projectjavafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import org.example.projectjavafx.Models.Admin;
import org.example.projectjavafx.Services.AdminService;

public class LoginController implements Initializable {

        @FXML
        private Pane button_loginpage;

        @FXML
        private Hyperlink link_toSignIn;

        @FXML
        private TextField tf_email;

        @FXML
        private PasswordField tf_password;

        @FXML
        private Button btn_loginAdmin;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {}

        @FXML
        public void goToSignIn(ActionEvent actionEvent) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) link_toSignIn.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void showAlert(Alert.AlertType type, String title, String content) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }

        @FXML
         private void LoginAdmin(MouseEvent event) throws SQLException {
        String email = tf_email.getText();
        String password = tf_password.getText();
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs vides !", "Veuillez remplir tous les champs.");
            return;
        }
        try {
            AdminService adminService = new AdminService();
            ResultSet resultSet = adminService.loginAdmin(email, password);
            if (resultSet.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardAdmins.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            }else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Les données saisies sont incorrectes!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la tentative de connexion : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
