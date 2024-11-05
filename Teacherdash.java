package com.app.quizizo;

import javax.swing.*;
import java.awt.*;

public class Teacherdash extends JPanel {
    public static void main(String[] args) {
        new Teacherdash(null);
    }

    public Teacherdash(String username) {
        setLayout(null);
        setBackground(new Color(255, 255, 255));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(190, 187, 180));
        mainPanel.setBounds(0, 0, 800, 800);
        add(mainPanel);

        // Dashboard Icon
        ImageIcon icon = new ImageIcon("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\QUIZIZO\\src\\main\\java\\com\\app\\quizizo\\icon.png");
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel img = new JLabel(new ImageIcon(scaledImage));
        img.setBounds(350, 10, 100, 100);
        mainPanel.add(img);

        // Header
        JLabel head = new JLabel("TEACHER DASHBOARD");
        head.setBounds(330, 100, 300, 40);
        head.setFont(new Font("Times New Roman", Font.BOLD, 13));
        mainPanel.add(head);

        // Centered Image Buttons
        int buttonSize = 100;
        int startX = 150; // Starting x-coordinate for the first button
        int yPosition = 300; // Y-coordinate for button row

        // Create and add the four buttons with messages
        JButton button1 = createImageButton("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\QUIZIZO\\src\\main\\java\\com\\app\\quizizo\\qn.png");
        button1.setBounds(startX, yPosition, buttonSize, buttonSize);
        button1.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button 1 action"));

        JButton button2 = createImageButton("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\QUIZIZO\\src\\main\\java\\com\\app\\quizizo\\edit.png");
        button2.setBounds(startX + 150, yPosition, buttonSize, buttonSize);
        button2.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button 2 action"));

        JButton button3 = createImageButton("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\QUIZIZO\\src\\main\\java\\com\\app\\quizizo\\st.png");
        button3.setBounds(startX + 300, yPosition, buttonSize, buttonSize);
        button3.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button 3 action"));

        JButton button4 = createImageButton("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\QUIZIZO\\src\\main\\java\\com\\app\\quizizo\\set.png");
        button4.setBounds(startX + 450, yPosition, buttonSize, buttonSize);
        button4.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button 4 action"));

        ImageIcon user = new ImageIcon("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\QUIZIZO\\src\\main\\java\\com\\app\\quizizo\\user.png");
        Image usersc = user.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        JLabel imgu = new JLabel(new ImageIcon(usersc));
        imgu.setBounds(690, 10, 75, 75);
        mainPanel.add(imgu);
        // Logout Button
        JButton logout = new JButton("Logout");
        logout.setBounds(680, 90, 100, 30);
        logout.setBackground(new Color(190, 187, 180));
        logout.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new Login(frame));
            frame.revalidate();
            frame.repaint();
        });
        mainPanel.add(logout);
        // Add buttons to the main panel
        mainPanel.add(button1);
        mainPanel.add(button2);
        mainPanel.add(button3);
        mainPanel.add(button4);


    }

    // Helper method to create a button with an image icon
    private JButton createImageButton(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setContentAreaFilled(false); // Remove button background
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border

        return button;
    }
}
