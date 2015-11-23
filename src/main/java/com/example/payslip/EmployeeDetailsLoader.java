package com.example.payslip;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDetailsLoader {

    public List<EmployeeDetails> load(String csvFilename) {
        List<EmployeeDetails> employeeDetailsList = null;
        if (csvFilename != null) {
            String line = "";
            String cvsSplitBy = ",";
            EmployeeDetails employeeDetails;
            employeeDetailsList = new ArrayList<>();
            try (BufferedReader input = new BufferedReader(
                    new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(csvFilename)))) {
                while ((line = input.readLine()) != null) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    String[] fields = line.split(cvsSplitBy);
                    employeeDetails = new EmployeeDetails();
                    employeeDetails.setName(fields[0]);
                    employeeDetails.setAnnualSalary(getAnnualSalary(fields[1]));
                    employeeDetails.setSuperRate(getSuperRate(fields[2]));
                    employeeDetails.setPayPeriodType(getPayPeriodType(fields[3]));
                    employeeDetails.setStartDate(getStartDate(fields[4]));
                    employeeDetailsList.add(employeeDetails);
                }
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("File " + csvFilename + "not found.");
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read file " + csvFilename + ".");
            }
        }
        return employeeDetailsList;
    }

    public Integer getAnnualSalary(String field) {
        Integer annualSalary = null;
        try {
            annualSalary = Integer.valueOf(field);
        } catch (NumberFormatException e) {
            annualSalary = null;
        }
        return annualSalary;
    }

    public Float getSuperRate(String field) {
        Float superRate = null;
        try {
            superRate = Float.valueOf(field) / 100;
        } catch (NumberFormatException e) {
            superRate = null;
        }
        return superRate;
    }

    public PayPeriodType getPayPeriodType(String field) {
        PayPeriodType payPeriodType = null;
        try {
            payPeriodType = PayPeriodType.valueOf(field);
        } catch (IllegalArgumentException e) {
            payPeriodType = null;
        }
        return payPeriodType;
    }

    public LocalDate getStartDate(String field) {
        LocalDate startDate = null;
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            startDate = LocalDate.parse(field, format);
        } catch (DateTimeParseException e) {
            startDate = null;
        }
        return startDate;
    }

}
