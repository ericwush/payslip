package com.example.payslip.tax;

import java.util.ArrayList;
import java.util.List;

/**
 * Tax calculator, calculates tax based on financial year and taxable income.
 * @author ericwu
 *
 */
public class TaxCalculator {

    private List<FinancialYearIndividualTaxRate> taxRates;

    public TaxCalculator(FinancialYearIndividualTaxRate... taxRates) {
        if (this.taxRates == null) {
            this.taxRates = new ArrayList<>();
        }
        for (FinancialYearIndividualTaxRate taxRate : taxRates) {
            this.taxRates.add(taxRate);
        }
    }

    public Integer calc(int year, int taxable) {
        Integer tax = null;
        TaxRate rate = null;
        for (final FinancialYearIndividualTaxRate taxRate : taxRates) {
            if (taxRate.accept(year)) {
                rate = taxRate;
                break;
            }
        }
        if (rate != null) {
            tax = rate.apply(taxable);
        }
        return tax;
    }

}
