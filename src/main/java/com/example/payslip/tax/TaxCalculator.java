package com.example.payslip.tax;

import java.util.List;

/**
 * Tax calculator, calculates tax based on financial year and taxable income.
 * @author ericwu
 *
 */
public class TaxCalculator {

    private final List<FinancialYearIndividualTaxRate> taxRates;

    public TaxCalculator(List<FinancialYearIndividualTaxRate> taxRates) {
        this.taxRates = taxRates;
    }

    public Float calc(int year, int taxable) {
        Float tax = null;
        TaxRate rate = null;
        for (final FinancialYearIndividualTaxRate taxRate : taxRates) {
            if (taxRate.accept(year)) {
                rate = taxRate;
                break;
            }
        }
        if (rate != null) {
            tax = rate.apply(taxable);
        } else {
            throw new IllegalStateException("Failed to find corresponding tax rates for " + year + ".");
        }
        return tax;
    }

}
