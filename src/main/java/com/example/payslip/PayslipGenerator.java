package com.example.payslip;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.example.payslip.employee.EmployeeDetails;
import com.example.payslip.employee.EmployeeDetailsLoader;
import com.example.payslip.helper.DateHelper;
import com.example.payslip.tax.TaxCalculator;
import com.example.payslip.tax.TaxRateLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PayslipGenerator {

    private final EmployeeDetailsLoader employeeDetailsLoader;
    private final PayslipBuilder payslipBuilder;
    private final PayslipWriter payslipWriter;

    public PayslipGenerator(final EmployeeDetailsLoader employeeDetailsLoader, final PayslipBuilder payslipBuilder,
            final PayslipWriter payslipWriter) {
        this.employeeDetailsLoader = employeeDetailsLoader;
        this.payslipBuilder = payslipBuilder;
        this.payslipWriter = payslipWriter;
    }

    public void generate(final String inputFilename, final String outputFilename) {
        final List<EmployeeDetails> employeeDetailsList = employeeDetailsLoader.load(inputFilename);
        final List<Payslip> payslips = new ArrayList<>();
        employeeDetailsList.forEach(employeeDetails -> {
            payslips.add(payslipBuilder.build(employeeDetails));
        });
        payslipWriter.write(outputFilename, payslips);
    }

    public static void main(final String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Must provide input and output csv filenames.");
        }
        final TaxRateLoader taxRateLoader = new TaxRateLoader(new ObjectMapper());
        final EmployeeDetailsLoader employeeDetailsLoader = new EmployeeDetailsLoader();
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final PayslipBuilder payslipBuilder = new PayslipBuilder(new DateHelper(), new TaxCalculator(taxRateLoader.load()),
                validator);
        final PayslipWriter payslipWriter = new PayslipWriter();
        final PayslipGenerator payslipGenerator = new PayslipGenerator(employeeDetailsLoader, payslipBuilder, payslipWriter);
        payslipGenerator.generate(args[0], args[1]);
    }

}
