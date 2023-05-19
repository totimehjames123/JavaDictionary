package com.GitPlus.Dictionary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class RegisteredUsers extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JButton deleteButton;
    private JButton searchButton;

    public RegisteredUsers() throws SQLException {
        // Create a connection to your database using JDBC
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_ambassadors_db", "root", "");

        // Execute a SELECT statement to retrieve the data you want from your table
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT username, email, phoneNumber, gender FROM signup");

        // Store the data in a Vector object
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        while (rs.next()) {
            Vector<String> row = new Vector<String>();
            row.add(rs.getString("username"));
            row.add(rs.getString("email"));
            row.add(rs.getString("phoneNumber"));
            row.add(rs.getString("gender"));
            data.add(row);
        }

        // Create a JTable object with the data stored in the Vector
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Username");
        columnNames.add("Email");
        columnNames.add("Phone Number");
        columnNames.add("Gender");
        table = new JTable(data, columnNames);

        // Add a JScrollPane to your JTable object so that it can be scrolled if there are too many rows to fit on the screen
        scrollPane = new JScrollPane(table);

        // Add a "Delete" button to your frame and add an ActionListener to it that will delete the selected row(s) from your table and database
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    ((DefaultTableModel)table.getModel()).removeRow(selectedRows[i]);
                }
            }
        });

        // Add a "Search" button to your frame and add an ActionListener to it that will search through your table for rows that match the search criteria
        searchButton = new JButton("Search");

        // Set up your JFrame with all of these components
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(deleteButton, BorderLayout.SOUTH);
    }
}


