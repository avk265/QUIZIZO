package com.app.quizizo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TeacherRegistration {

    private String generatedOtp; // Store the generated OTP
    private final OTPService otpService; // Create an instance of OTPService

    public TeacherRegistration(Runnable onClose) {
        otpService = new OTPService(); // Initialize the OTPService
        JFrame frame = new JFrame("ONLINE EXAMINATION SYSTEM - Teacher Registration");
        frame.setSize(1100, 800);
        frame.getContentPane().setBackground(new Color(190, 187, 180));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // Adding Image
        ImageIcon icon = new ImageIcon("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\QUIZIZO\\src\\main\\java\\com\\app\\quizizo\\icon.png");
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon ico = new ImageIcon(scaledImage);
        JLabel img = new JLabel(ico);
        img.setBounds(10, 10, 400, 400);
        frame.add(img);

        // Header
        JLabel head = new JLabel("TEACHER REGISTRATION FORM");
        head.setBounds(550, 10, 300, 40);
        head.setFont(new Font("Times New Roman", Font.BOLD, 13));
        frame.add(head);

        // Form Labels and Fields
        String[] labels = {"USERNAME", "FULL NAME", "GENDER", "MOBILE NUMBER", "EMAIL", "PASSWORD", "COURSE", "DEPARTMENT", "DESIGNATION"};
        int yPosition = 100;
        for (String label : labels) {
            JLabel lbl = new JLabel(label);
            lbl.setBounds(350, yPosition, 200, 40);
            lbl.setFont(new Font("Times New Roman", Font.PLAIN, 13));
            frame.add(lbl);
            yPosition += 50;
        }

        JButton sendOtpButton = new JButton("SEND OTP");
        sendOtpButton.setBounds(800, 260, 100, 25);
        sendOtpButton.setBackground(new Color(190, 187, 180));
        sendOtpButton.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        frame.add(sendOtpButton);

        // Fields
        JTextField usernameField = new JTextField();
        usernameField.setBounds(600, 110, 200, 25);
        frame.add(usernameField);

        JTextField fullNameField = new JTextField();
        fullNameField.setBounds(600, 160, 200, 25);
        frame.add(fullNameField);

        // Gender Radio Buttons
        JRadioButton male = new JRadioButton("Male");
        male.setBounds(600, 210, 70, 25);
        male.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        male.setBackground(new Color(190, 187, 180));
        frame.add(male);

        JRadioButton female = new JRadioButton("Female");
        female.setBounds(670, 210, 80, 25);
        female.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        female.setBackground(new Color(190, 187, 180));
        frame.add(female);

        JRadioButton other = new JRadioButton("Other");
        other.setBounds(750, 210, 70, 25);
        other.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        other.setBackground(new Color(190, 187, 180));
        frame.add(other);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);

        JTextField mobileField = new JTextField();
        mobileField.setBounds(600, 260, 200, 25);
        frame.add(mobileField);

        JTextField otpField = new JTextField();
        otpField.setBounds(910, 260, 100, 25);
        addPlaceholderText(otpField, "OTP");
        frame.add(otpField);

        JTextField emailField = new JTextField();
        emailField.setBounds(600, 310, 200, 25);
        frame.add(emailField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(600, 360, 200, 25);
        frame.add(passwordField);

        JTextField courseField = new JTextField();
        courseField.setBounds(600, 410, 200, 25);
        frame.add(courseField);

        // Add department and designation fields
        JTextField departmentField = new JTextField();
        departmentField.setBounds(600, 460, 200, 25);
        frame.add(departmentField);

        JTextField designationField = new JTextField();
        designationField.setBounds(600, 510, 200, 25);
        frame.add(designationField);

        // OTP and Verification
        sendOtpButton.addActionListener(event -> {
            String phoneNumber = mobileField.getText();
            if (!phoneNumber.isEmpty() && isValidMobileNumber(phoneNumber)) {
                generatedOtp = generateOtp();
                boolean success = otpService.sendOtp(phoneNumber, generatedOtp);
                JOptionPane.showMessageDialog(frame, success ? "OTP sent successfully!" : "Failed to send OTP.");
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid phone number.");
            }
        });

        JButton verifyOtpButton = new JButton("VERIFY OTP");
        verifyOtpButton.setBounds(800, 310, 150, 25);
        verifyOtpButton.setBackground(new Color(190, 187, 180));
        verifyOtpButton.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        frame.add(verifyOtpButton);

        // Confirmation Checkbox
        Checkbox c = new Checkbox("All the details given are correct.");
        c.setBounds(500, 550, 500, 30);
        c.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        frame.add(c);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(650, 590, 100, 30);
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setBorder(null);
        submitButton.setEnabled(false);
        frame.add(submitButton);

        verifyOtpButton.addActionListener(e -> {
            String userInputOtp = otpField.getText();
            if (otpService.verifyOtp(mobileField.getText(), userInputOtp)) {
                JOptionPane.showMessageDialog(frame, "OTP verified successfully!");
                submitButton.setEnabled(true);

                // Check all fields when OTP is verified
                if (checkAllFields(usernameField, fullNameField, mobileField, emailField, passwordField, courseField, genderGroup, c)) {
                    submitButton.setEnabled(true); // Enable the submit button if all fields are valid
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid OTP. Please try again.");
            }
        });


        // Database Submission
        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            String fullName = fullNameField.getText();
            String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Other";
            String mobileNumber = mobileField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String course = courseField.getText();
            String department = departmentField.getText();
            String designation = designationField.getText();

            // Validation before submitting
            StringBuilder errorMessage = new StringBuilder();
            if (checkUsernameExists(username)) {
                errorMessage.append("Username already exists. Please choose a different username.\n");
            }
            if (!isValidMobileNumber(mobileNumber)) {
                errorMessage.append("Mobile number must be exactly 10 digits.\n");
            }
            if (!isValidEmail(email)) {
                errorMessage.append("Email must end with @gmail.com, @tkmce.ac.in, or @outlook.com.\n");
            }

            if (errorMessage.length() > 0) {
                JOptionPane.showMessageDialog(frame, errorMessage.toString());
                return; // Stop submission if there are validation errors
            }

            try {
                saveToDatabase(username, fullName, gender, mobileNumber, email, password, course, department, designation);
                JOptionPane.showMessageDialog(frame, "Registration successful!");
                frame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error saving to database: " + ex.getMessage());
            }
        });


        frame.setVisible(true);
    }

    private void addPlaceholderText(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private boolean checkAllFields(JTextField usernameField, JTextField fullNameField, JTextField mobileField,
                                   JTextField emailField, JPasswordField passwordField, JTextField courseField,
                                   ButtonGroup genderGroup, Checkbox c) {
        boolean allValid = !usernameField.getText().isEmpty() &&
                !fullNameField.getText().isEmpty() &&
                !mobileField.getText().isEmpty() &&
                !emailField.getText().isEmpty() &&
                passwordField.getPassword().length > 0 &&
                !courseField.getText().isEmpty() &&
                (genderGroup.getSelection() != null) &&
                c.getState();

        // Optionally provide feedback
        if (!allValid) {
            // Show a message or highlight the invalid fields
            JOptionPane.showMessageDialog(null, "Please ensure all fields are filled correctly.");
        }

        return allValid; // Return true if all fields are valid, false otherwise
    }


    private boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com") || email.endsWith("@tkmce.ac.in") || email.endsWith("@outlook.com");
    }

    private String generateOtp() {
        int otp = (int)(Math.random() * 9000) + 1000; // Generates a random 4-digit OTP
        return String.valueOf(otp);
    }

    private boolean checkUsernameExists(String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:Mysql://localhost:3306/mydb", "root", "kiran");
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM teacher_registration WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if username exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if no exception occurs and username does not exist
    }

    private void saveToDatabase(String username, String fullName, String gender, String mobileNumber, String email,
                                String password, String course, String department, String designation) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:Mysql://localhost:3306/mydb", "root", "kiran")) {
            // Create table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS teacher_records(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(255) NOT NULL UNIQUE," +
                    "full_name VARCHAR(255) NOT NULL," +
                    "gender VARCHAR(50)," +
                    "mobile_number VARCHAR(15)," +
                    "email VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "course VARCHAR(255)," +
                    "department VARCHAR(255)," +
                    "designation VARCHAR(255)" +
                    ")";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
            }

            // Prepare SQL for insertion
            String sql = "INSERT INTO teacher_records (username, full_name, gender, mobile_number, email, password, course, department, designation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, mobileNumber);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, password); // Consider hashing the password
                preparedStatement.setString(7, course);
                preparedStatement.setString(8, department);
                preparedStatement.setString(9, designation);
                preparedStatement.executeUpdate();
            }
        }
    }

}
