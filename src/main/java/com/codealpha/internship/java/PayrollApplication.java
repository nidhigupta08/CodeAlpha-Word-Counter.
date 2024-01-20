package com.codealpha.internship.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

class Employee implements Serializable {
	
    private String name;
    private int hoursWorked;
    private double hourlyRate;

    public Employee(String name, int hoursWorked, double hourlyRate) {
        this.name = name;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public String getName() {
        return name;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }

    public String getFormattedSalary() {
        Locale indiaLocale = new Locale("en", "IN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(indiaLocale);
        return currencyFormatter.format(calculateSalary());
    }
}

class PayrollSystem implements Serializable {
    private ArrayList<Employee> employees;

    public PayrollSystem() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void generatePayroll() {
        System.out.println("Payroll Summary:");
        for (Employee employee : employees) {
            String formattedSalary = employee.getFormattedSalary();
            System.out.println("Employee: " + employee.getName() +
                    ", Hours Worked: " + employee.getHoursWorked() +
                    ", Hourly Rate: ₹" + employee.getHourlyRate() +
                    ", Salary: " + formattedSalary);
        }
    }
}

public class PayrollApplication {
    private JFrame frame;
    private JTextArea payrollTextArea;

    private PayrollSystem payrollSystem;

    public PayrollApplication() {
        frame = new JFrame("Employee Payroll System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        placeComponents(panel);

        payrollSystem = new PayrollSystem();

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Employee Payroll System");
        titleLabel.setBounds(10, 20, 300, 25);
        panel.add(titleLabel);

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setBounds(10, 50, 150, 25);
        panel.add(addEmployeeButton);
 
         
        JButton generatePayrollButton = new JButton("Generate Payroll");
        generatePayrollButton.setBounds(180, 50, 150, 25);
        panel.add(generatePayrollButton);

        payrollTextArea = new JTextArea();
        payrollTextArea.setBounds(10, 80, 450, 250);
        panel.add(payrollTextArea);

        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        generatePayrollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generatePayroll();
            }
        });
    }

    private void addEmployee() {
        boolean validInput = false;
        String name = null;

        while (!validInput) {
            name = JOptionPane.showInputDialog(frame, "Enter employee name:");
            if (name == null) {
                // User pressed cancel, exit the loop
                return;
            }

            if (!name.isEmpty()) {
                validInput = true;
            } else {
                JOptionPane.showMessageDialog(frame, "Employee name cannot be empty. Please enter a name.");
            }
        }

        validInput = false;

        while (!validInput) {
            try {
                String hoursWorkedStr = JOptionPane.showInputDialog(frame, "Enter hours worked:");
                if (hoursWorkedStr == null || hoursWorkedStr.isEmpty()) {
                    throw new NumberFormatException();
                }
                int hoursWorked = Integer.parseInt(hoursWorkedStr);

                String hourlyRateStr = JOptionPane.showInputDialog(frame, "Enter hourly rate (INR):");
                if (hourlyRateStr == null || hourlyRateStr.isEmpty()) {
                    throw new NumberFormatException();
                }
                double hourlyRate = Double.parseDouble(hourlyRateStr);

                Employee employee = new Employee(name, hoursWorked, hourlyRate);
                payrollSystem.addEmployee(employee);

                JOptionPane.showMessageDialog(frame, "Employee added successfully!");
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numeric values for hours worked and hourly rate.");
            }
        }
    }

    private void generatePayroll() {
        if (payrollSystem.getEmployees().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No employees added. Add employees before generating payroll.");
        } else {
            StringBuilder payrollSummary = new StringBuilder("Payroll Summary:\n");
            for (Employee employee : payrollSystem.getEmployees()) {
                String formattedSalary = employee.getFormattedSalary();
                payrollSummary.append("Employee: ").append(employee.getName())
                        .append(", Hours Worked: ").append(employee.getHoursWorked())
                        .append(", Hourly Rate: ₹").append(employee.getHourlyRate())
                        .append(", Salary: ").append(formattedSalary).append("\n");
            }

            payrollTextArea.setText(payrollSummary.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PayrollApplication();
            }
        });
    }
}