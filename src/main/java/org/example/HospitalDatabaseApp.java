package org.example;

import javax.swing.*;
public class HospitalDatabaseApp extends JFrame{
    private DoctorForm doctorForm;
    private HospitalDatabaseManager databaseManager;

    public HospitalDatabaseApp() {
        setTitle("Hospital Database App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        doctorForm = new DoctorForm(this);
        databaseManager = new HospitalDatabaseManager();

        // Добавление панели на форму
        add(doctorForm.getPanel());

        setVisible(true);
    }

    public HospitalDatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HospitalDatabaseApp();
            }
        });
    }
}
