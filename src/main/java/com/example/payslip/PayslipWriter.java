package com.example.payslip;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;

public class PayslipWriter {

    public void write(final String filename, final List<Payslip> payslips) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            final String[] headers = "Name,Start date,End date,Gross income,Income tax,Net income,Super,Error"
                    .split(",");
            writer.writeNext(headers);

            payslips.forEach(payslip -> {
                final String[] record = payslip.toString().split(",");
                writer.writeNext(record);
            });
            writer.flush();
        } catch (final IOException e) {
            throw new IllegalStateException("Error occurred when writing to file " + filename, e);
        }
    }

}
