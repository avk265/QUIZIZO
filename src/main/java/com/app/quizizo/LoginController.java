package com.app.quizizo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class LoginController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private boolean isStudentRegistrationOpen = false;
    private boolean isTeacherRegistrationOpen = false;
    private boolean isAdvisorRegistrationOpen = false;

    // Initialize ComboBox values
    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Student", "Teacher", "Advisor");
    }

    // Handle login button action
    @FXML
    private void handleLogin(ActionEvent event) {
        String selectedRole = roleComboBox.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (checkUsernameExists(username, selectedRole)) {
            if (validateCredentials(username, password, selectedRole)) {
                showMessage("Login successful as " + selectedRole + "!", Color.GREEN);
            } else {
                showMessage("Invalid credentials. Please try again.", Color.RED);
            }
        } else {
            showMessage("Username not found.", Color.RED);
        }
    }

    // Handle forgot password click
    @FXML
    private void handlePasswordReset(MouseEvent event) {
        String username = TextInputDialog(usernameField, "Enter your username:");
        if (username == null || username.trim().isEmpty()) return;

        String selectedRole = roleComboBox.getValue();
        if (checkUsernameExists(username, selectedRole)) {
            String newPassword = TextInputDialog(passwordField, "Enter your new password:");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                if (resetPassword(username, newPassword, selectedRole)) {
                    showMessage("Password reset successful!", Color.GREEN);
                } else {
                    showMessage("Error resetting password. Please try again.", Color.RED);
                }
            }
        } else {
            showMessage("Username not found.", Color.RED);
        }
    }

    // Handle register button action
    @FXML
    private void handleRegister(ActionEvent event) {
        String selectedRole = roleComboBox.getValue();

        // Open respective registration windows
        if (selectedRole.equals("Student") && !isStudentRegistrationOpen) {
            isStudentRegistrationOpen = true;
            new StudentRegistration(() -> isStudentRegistrationOpen = false);
        } else if (selectedRole.equals("Teacher") && !isTeacherRegistrationOpen) {
            isTeacherRegistrationOpen = true;
            new TeacherRegistration(() -> isTeacherRegistrationOpen = false);
        } else if (selectedRole.equals("Advisor") && !isAdvisorRegistrationOpen) {
            isAdvisorRegistrationOpen = true;
            new Registration(() -> isAdvisorRegistrationOpen = false);
        } else {
            showMessage("Registration form for " + selectedRole + " is already open.", Color.RED);
        }
    }

    // Utility Methods
    private void showMessage(String message, Color color) {
        messageLabel.setTextFill(color);
        messageLabel.setText(message);
    }

    private boolean checkUsernameExists(String username, String role) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";
        String query = "SELECT COUNT(*) FROM " + role.toLowerCase() + "_records WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resetPassword(String username, String newPassword, String role) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";
        String query = "UPDATE " + role.toLowerCase() + "_records SET password = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateCredentials(String username, String password, String role) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";
        String query = "SELECT COUNT(*) FROM " + role.toLowerCase() + "_records WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String TextInputDialog(Control source, String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Required");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse(null);
    }
}
