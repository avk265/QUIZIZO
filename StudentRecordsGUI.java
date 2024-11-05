package com.app.quizizo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentRecordsGUI extends JPanel {

    private JComboBox<String> classDropdown;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentRecordsGUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 800));

        // Dropdown for class selection
        JPanel topPanel = new JPanel();
        classDropdown = new JComboBox<>(new String[]{"R3B", "R3A", "R2A", "R2B"}); // Add classes as needed
        JButton loadButton = new JButton("Load Student Records");
        topPanel.add(new JLabel("Select Class:"));
        topPanel.add(classDropdown);
        topPanel.add(loadButton);

        // Table to display student records
        String[] columnNames = {"Name", "Roll Number", "Mobile", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);

        // Add components to the panel
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Load button action
        loadButton.addActionListener(e -> loadStudentRecords());

    }

    private void loadStudentRecords() {
        String selectedClass = (String) classDropdown.getSelectedItem();
        List<String[]> studentRecords = StudentRecordsFetcher.fetchStudentRecords(selectedClass);

        // Clear the table before loading new data
        tableModel.setRowCount(0);

        // Populate the table model with student records
        for (String[] record : studentRecords) {
            tableModel.addRow(record);
        }
    }

    public static void showStudentRecordsGUI() {
        JFrame frame = new JFrame("Student Records");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new StudentRecordsGUI());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
