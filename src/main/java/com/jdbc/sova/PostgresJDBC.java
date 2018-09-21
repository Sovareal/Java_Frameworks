package com.jdbc.sova;

import java.sql.*;

public class PostgresJDBC {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:58995/jdbc";

    //  Database credentials
    static final String USERNAME = "postgres";
    static final String PASSWORD = "3351425";

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        CallableStatement callableStatement = null;

        try {
            //Register Driver
            Class.forName(JDBC_DRIVER);

            //Open Connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            if (connection != null) System.out.println("Connection Successful !\n");

            //Execute Query (Statement)
            statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM employees");

            System.out.println("Statement proceeding:");
            while (resultSet1.next()) {
                System.out.println("Query element №" + resultSet1.getRow()
                        + "\t Id in DB" + resultSet1.getInt("id")
                        + "\t" + resultSet1.getString("employee"));
            }

            //Execute Query (Prepared Statement)
            preparedStatement = connection.prepareStatement("INSERT INTO employee_qualitatibe_goals VALUES (?,?,?) ");
            preparedStatement.setInt(1, 6);
            preparedStatement.setString(2, "DAO Pattern");
            preparedStatement.setInt(3, 2);
            ResultSet resultSet2 = preparedStatement.executeQuery();

            //Execute Query (Callable Statement)
            callableStatement = connection.prepareCall("{call totalGoals(?)}");
            callableStatement.setInt(1, 2);
            ResultSet resultSet3 = callableStatement.executeQuery();

            while (resultSet3.next()) {
                System.out.println(resultSet3.getString("goal"));
            }

            resultSet1.close();
            resultSet2.close();
            resultSet3.close();
            statement.close();
            preparedStatement.close();
            callableStatement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
