package com.example.payslip;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.example.payslip.tax.TaxCalculator;
import com.example.payslip.tax.TaxRateLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PayslipGenerator {

    private final EmployeeDetailsLoader employeeDetailsLoader;
    private final PayslipBuilder payslipBuilder;
    private final PayslipWriter payslipWriter;

    public PayslipGenerator(EmployeeDetailsLoader employeeDetailsLoader, PayslipBuilder payslipBuilder,
            PayslipWriter payslipWriter) {
        this.employeeDetailsLoader = employeeDetailsLoader;
        this.payslipBuilder = payslipBuilder;
        this.payslipWriter = payslipWriter;
    }

    public void generate(String inputFilename, String outputFilename) {
        List<EmployeeDetails> employeeDetailsList = employeeDetailsLoader.load(inputFilename);
        List<Payslip> payslips = new ArrayList<>();
        employeeDetailsList.forEach(employeeDetails -> {
            payslips.add(payslipBuilder.build(employeeDetails));
        });
        payslipWriter.write(outputFilename, payslips);
    }

    public static void main(String[] args) {
//        if (args.length < 2) {
//            throw new IllegalArgumentException("Must provide input and output csv filenames.");
//        }
        TaxRateLoader taxRateLoader = new TaxRateLoader(new ObjectMapper());
        EmployeeDetailsLoader employeeDetailsLoader = new EmployeeDetailsLoader();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        PayslipBuilder payslipBuilder = new PayslipBuilder(new DateHelper(), new TaxCalculator(taxRateLoader.load()),
                validator);
        PayslipWriter payslipWriter = new PayslipWriter();
        PayslipGenerator payslipGenerator = new PayslipGenerator(employeeDetailsLoader, payslipBuilder, payslipWriter);
//        payslipGenerator.generate(args[0], args[1]);
        payslipGenerator.generate("input.csv", "output.csv");
    }

}
