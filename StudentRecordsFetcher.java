package com.app.quizizo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordsFetcher {

    public static List<String[]> fetchStudentRecords(String className) {
        List<String[]> records = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "kiran";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT username, roll_number, mobile, email FROM student_records WHERE class = ? ORDER BY name";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, className);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String rollNumber = rs.getString("roll_number");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");

                records.add(new String[]{name, rollNumber, mobile, email});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
}
