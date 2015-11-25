package com.example.payslip.employee;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.payslip.PayPeriodType;
import com.opencsv.CSVReader;

/**
 * Employee details loader which loads the given csv file and maps into EmployeeDetails model.
 * @author ericwu
 *
 */
public class EmployeeDetailsLoader {

    public List<EmployeeDetails> load(final String csvFilename) {
        List<EmployeeDetails> employeeDetailsList = null;
        if (csvFilename != null) {
            EmployeeDetails employeeDetails;
            employeeDetailsList = new ArrayList<>();
            boolean header = true;
            try (CSVReader csvReader = new CSVReader(
                    new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(csvFilename)))) {
                String[] fields;
                while ((fields = csvReader.readNext()) != null) {
                    if (header) {
                        header = false;
                        continue;
                    }
                    employeeDetails = new EmployeeDetails();
                    employeeDetails.setName(getName(fields[0]));
                    employeeDetails.setAnnualSalary(getAnnualSalary(fields[1]));
                    employeeDetails.setSuperRate(getSuperRate(fields[2]));
                    employeeDetails.setPayPeriodType(getPayPeriodType(fields[3]));
                    employeeDetails.setStartDate(getStartDate(fields[4]));
                    employeeDetailsList.add(employeeDetails);
                }
            } catch (final Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to read file " + csvFilename + ".");
            }
        }
        return employeeDetailsList;
    }

    public String getName(String field) {
        String name = null;
        if (field != null && field.trim().length() > 0) {
            name = field.trim();
        }
        return name;
    }

    public Integer getAnnualSalary(final String field) {
        Integer annualSalary = null;
        try {
            annualSalary = Integer.valueOf(field);
        } catch (final NumberFormatException e) {
            annualSalary = null;
        }
        return annualSalary;
    }

    public Float getSuperRate(final String field) {
        Float superRate = null;
        try {
            superRate = Float.valueOf(field) / 100;
        } catch (final NumberFormatException e) {
            superRate = null;
        }
        return superRate;
    }

    public PayPeriodType getPayPeriodType(final String field) {
        PayPeriodType payPeriodType = null;
        try {
            payPeriodType = PayPeriodType.valueOf(field);
        } catch (final IllegalArgumentException e) {
            payPeriodType = null;
        }
        return payPeriodType;
    }

    public LocalDate getStartDate(final String field) {
        LocalDate startDate = null;
        try {
            final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            startDate = LocalDate.parse(field, format);
        } catch (final DateTimeParseException e) {
            startDate = null;
        }
        return startDate;
    }

}
