package com.example.payslip.tax;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TaxRateLoader {

    private final static String PATH = "tax/";

    private final ObjectMapper mapper;

    public TaxRateLoader(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<String> getTaxRateFilenames(final String path) {
        List<String> filenames;
        try {
            filenames = IOUtils.readLines(TaxRateLoader.class.getClassLoader()
                    .getResourceAsStream(path), Charsets.UTF_8);
        } catch (final Exception e) {
            filenames = null;
        }
        return filenames;
    }

    public FinancialYearIndividualTaxRate mapTaxRateFile(final InputStream inputStream) {
        FinancialYearIndividualTaxRate taxRate;
        try {
            taxRate = mapper.readValue(inputStream, FinancialYearIndividualTaxRate.class);
        } catch (final Exception e) {
            taxRate = null;
        }
        return taxRate;
    }

    public List<FinancialYearIndividualTaxRate> load() {
        List<FinancialYearIndividualTaxRate> taxRates = null;
        final List<String> filenames = getTaxRateFilenames(PATH);
        if (filenames != null) {
            taxRates = new ArrayList<>();
            for (final String filename : filenames) {
                taxRates.add(
                        mapTaxRateFile(TaxRateLoader.class.getClassLoader().getResourceAsStream(PATH + filename)));
            }
        }
        return taxRates;
    }

}
