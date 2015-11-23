package com.example.payslip;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PayslipWriter {

    public void write(final String filename, final List<Payslip> payslips) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Name");
            writer.append(',');
            writer.append("Start date");
            writer.append(',');
            writer.append("End date");
            writer.append(',');
            writer.append("Gross income");
            writer.append(',');
            writer.append("Income tax");
            writer.append(',');
            writer.append("Net income");
            writer.append(',');
            writer.append("Super");
            writer.append(',');
            writer.append("Error");
            writer.append('\n');

            payslips.forEach(payslip -> {
                try {
                    writer.append(payslip.getName());
                    writer.append(',');
                    writer.append(payslip.getStartDate() != null ? payslip.getStartDate().toString() : "");
                    writer.append(',');
                    writer.append(payslip.getEndDate() != null ? payslip.getEndDate().toString() : "");
                    writer.append(',');
                    writer.append(payslip.getGrossIncome() != null ? payslip.getGrossIncome().toString() : "");
                    writer.append(',');
                    writer.append(payslip.getIncomeTax() != null ? payslip.getIncomeTax().toString() : "");
                    writer.append(',');
                    writer.append(payslip.getNetIncome() != null ? payslip.getNetIncome().toString() : "");
                    writer.append(',');
                    writer.append(payslip.getSuperannuation() != null ? payslip.getSuperannuation().toString() : "");
                    writer.append(',');
                    writer.append(payslip.getError() != null ? payslip.getError().toString() : "");
                } catch (final IOException e) {
                    throw new IllegalStateException("Error occurred when writing to file " + filename, e);
                }
            });
            writer.flush();
        } catch (final IOException e) {
            throw new IllegalStateException("Error occurred when writing to file " + filename, e);
        }
    }

}
