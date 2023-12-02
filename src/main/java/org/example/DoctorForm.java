package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DoctorForm {
    private JTextField idField, lastNameField, firstNameField, middleNameField, specializationField, dateBirthField, positionField;
    private JButton addButton, viewButton, deleteButton, editButton;
    private HospitalDatabaseApp app;

    public DoctorForm(HospitalDatabaseApp app) {
        this.app = app;

        // Инициализация компонентов GUI
        idField = new JTextField(10);
        lastNameField = new JTextField(20);
        firstNameField = new JTextField(20);
        middleNameField = new JTextField(20);
        dateBirthField = new JTextField(10);
        positionField = new JTextField(20);
        specializationField = new JTextField(30);
        addButton = new JButton("Добавить");
        viewButton = new JButton("Просмотр");
        deleteButton = new JButton("Удалить");


        // Добавление слушателей
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor doctor = getDoctorData();
                if (doctor != null) {
                    app.getDatabaseManager().addDoctor(doctor);
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.getDatabaseManager().viewData();
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.getDatabaseManager().deleteDoctor();
            }
        });

    }

    public JPanel getPanel() {
        // Размещение компонентов на панели
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Фамилия:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Имя:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Отчество:"));
        panel.add(middleNameField);
        panel.add(new JLabel("Дата рождения (ГГГГ-ММ-ДД):"));
        panel.add(dateBirthField);
        panel.add(new JLabel("Должность:"));
        panel.add(positionField);
        panel.add(new JLabel("Специализация:"));
        panel.add(specializationField);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(deleteButton);
        return panel;
    }

    private Doctor getDoctorData() {
        try {
            int id = Integer.parseInt(idField.getText());
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String middleName = middleNameField.getText();
            String dateBirthText = dateBirthField.getText();
            String specialization = specializationField.getText();
            String position = positionField.getText();

            if (!isValidDate(dateBirthText)) {
                JOptionPane.showMessageDialog(null, "Некорректный формат даты (ГГГГ-ММ-ДД)!");
                return null;
            }

            Date dateBirth;
            dateBirth = parseDate(dateBirthText);

            return new Doctor(id,lastName, firstName, middleName,  dateBirth, position, specialization);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка при вводе ID!");
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    private void setDoctorData(Doctor doctor) {

            idField.setText(String.valueOf(doctor.getId()));
            lastNameField.setText(doctor.getLastName());
            firstNameField.setText(doctor.getFirstName());
            middleNameField.setText(doctor.getMiddleName());
            dateBirthField.setText(doctor.getBirthDate().toString());
            specializationField.setText(doctor.getSpecialization());
            positionField.setText(doctor.getPosition());
    }

    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}
