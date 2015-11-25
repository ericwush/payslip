package com.example.payslip.tax;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tax rate loader that loads tax rate configuration files and maps into tax rate model.
 * @author ericwu
 *
 */
public class TaxRateLoader {

    private final ObjectMapper mapper;

    public TaxRateLoader(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<FinancialYearIndividualTaxRate> load(final String filename) {
        List<FinancialYearIndividualTaxRate> taxRates;
        try {
            taxRates = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream(filename),
                    new TypeReference<List<FinancialYearIndividualTaxRate>>(){});
        } catch (final Exception e) {
            taxRates = null;
        }
        return taxRates;
    }

}
