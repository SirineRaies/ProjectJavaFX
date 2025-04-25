package org.example.projectjavafx.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.projectjavafx.Models.Admin;
import org.example.projectjavafx.Services.AdminService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.projectjavafx.Services.AdminService.encryptPassword;

public class SignUpController  implements Initializable {
        @FXML
        private Pane button_signup;

        @FXML
        private Hyperlink link_toLogin;

        @FXML
        private TextField tf_email;

        @FXML
        private TextField tf_nomcomplet;

        @FXML
        private PasswordField tf_password;

    private final AdminService adminService = new AdminService();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        }

        @FXML
        private void addadmin(MouseEvent event) {
            String name = tf_nomcomplet.getText();
            String email = tf_email.getText();
            String password = tf_password.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() ) {
                showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Veuillez remplir tous les champs.");
                return;
            }

            if (!isEmailValid(email)) {
                showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Veuillez saisir une adresse e-mail valide.");
                return;
            }

            if (!isValidPassword(password)) {
                showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Veuillez saisir un mot de passe valide (8 caractères minimum et au moins un chiffre ).");
                return;
            }

            try {
                String encryptedPassword = encryptPassword(password);
                Admin admin = new Admin(0, name, email, encryptedPassword);
                adminService.ajouter(admin);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean isValidPassword(String password) {
            String regex = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }

        //Email validation methode
        private boolean isEmailValid(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }


        @FXML
        private void goToSignIn(ActionEvent event) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) link_toLogin.getScene().getWindow();
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
    }

