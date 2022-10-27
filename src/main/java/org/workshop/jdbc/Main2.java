package org.workshop.jdbc;

import java.sql.*;

public class Main2 {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Brak sterownika");
            e.printStackTrace();
            System.exit(1);
        }

        Connection conn;
        String strUrl = "jdbc:mysql://localhost:3306/testdb";
        java.util.Properties props = new java.util.Properties();
        props.put("user", "root");
        props.put("password", "root");
        try {
            conn = DriverManager.getConnection(strUrl, props);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select * from movies");
            while (result.next()) {
                System.out.println(result.getInt("id"));
                System.out.println(result.getString("title"));
                System.out.println(result.getString("genre"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
