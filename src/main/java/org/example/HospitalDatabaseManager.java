package org.example;

import javax.swing.*;
import java.sql.*;
public class HospitalDatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USERNAME = "Lisa";
    private static final String PASSWORD = "5972019mr";
    public void addDoctor(Doctor doctor) {
        try {

            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            createTableIfNotExists(connection);
            System.out.println("ID: " + doctor.getId());
            System.out.println("isDoctorsExists: " + isDoctorsExists(connection, doctor.getId()));
            if (doctor != null && doctor.getId() != 0 && !isDoctorsExists(connection, doctor.getId())) {
                insertDoctor(connection, doctor);
                JOptionPane.showMessageDialog(null, "Доктор успешно добавлен!");
            } else {
                JOptionPane.showMessageDialog(null, "Доктор с таким ID уже существует!");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка при добавлении доктора!");
        }
    }

    public void deleteDoctor() {
        try {

            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            createTableIfNotExists(connection);
            JTextField idField = new JTextField(10);
            String idInput = JOptionPane.showInputDialog("Введите ID для удаления!");
            int id = 0;
            if (idInput != null && !idInput.isEmpty()) {
                try {
                    id = Integer.parseInt(idInput);
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ID: " + idInput);
                }
            } else {
                System.out.println("Не введён ID.");
            }

            System.out.println("ID: " + id);
            if (isDoctorsExists(connection, id)) {
                deleteFromDB(connection,id);
                JOptionPane.showMessageDialog(null, "Доктор успешно удалён!");
            } else {
                JOptionPane.showMessageDialog(null, "Доктор с таким ID не найден!");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка при удалении доктора!");
        }
    }

    public void viewData() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            createTableIfNotExists(connection);

            String query = "SELECT * FROM doctors";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            StringBuilder result = new StringBuilder("Список всех докторов:\n");
            while (resultSet.next()) {
                result.append(resultSet.getString("id"))
                        .append(". ")
                        .append(resultSet.getString("last_name"))
                        .append(", ")
                        .append(resultSet.getString("first_name"))
                        .append(", ")
                        .append(resultSet.getString("middle_name"))
                        .append(", ")
                        .append(resultSet.getDate("dateBirth"))
                        .append(", ")
                        .append(resultSet.getString("position"))
                        .append(", ")
                        .append(resultSet.getString("specialization"))
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, result.toString());

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка при просмотре данных!");
        }
    }

    private void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS doctors ("
                + "id INT PRIMARY KEY,"
                + "last_name VARCHAR(255) NOT NULL,"
                + "first_name VARCHAR(255) NOT NULL,"
                + "middle_name VARCHAR(255),"
                + "dateBirth DATE,"
                + "position VARCHAR(255),"
                + "specialization VARCHAR(255))";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    private boolean isDoctorsExists(Connection connection, int id) throws SQLException {
        String checkIfExistsQuery = "SELECT id FROM doctors WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkIfExistsQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ;
                return resultSet.next();
            }
        }
    }

    private void insertDoctor(Connection connection, Doctor doctor) throws SQLException {
        String insertQuery = "INSERT INTO doctors (id, last_name, first_name, middle_name, dateBirth,  position, specialization) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, doctor.getId());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getFirstName());
            preparedStatement.setString(4, doctor.getMiddleName());
            preparedStatement.setDate(5, new java.sql.Date(doctor.getBirthDate().getTime()));
            preparedStatement.setString(6, doctor.getPosition());
            preparedStatement.setString(7, doctor.getSpecialization());
            preparedStatement.executeUpdate();
        }
    }


    private void deleteFromDB(Connection connection, int id) throws SQLException {
        String insertQuery = "DELETE FROM doctors WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

}
