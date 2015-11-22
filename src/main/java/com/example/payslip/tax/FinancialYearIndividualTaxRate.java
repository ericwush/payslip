package com.example.payslip.tax;

import java.util.ArrayList;
import java.util.List;

/**
 * Individual tax rates for a specific financial year.
 * @author ericwu
 *
 */
public class FinancialYearIndividualTaxRate implements TaxRate {

    private int financialYear;
    private List<IndividualTaxRate> taxRates = new ArrayList<>();

    public boolean accept(final int year) {
        boolean accepted = false;
        if (financialYear == year) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public Integer apply(final Integer taxable) {
        Integer tax = null;
        TaxRate rate = null;
        for (final IndividualTaxRate taxRate : taxRates) {
            if (taxRate.accept(taxable)) {
                rate = taxRate;
                break;
            }
        }
        if (rate != null) {
            tax = rate.apply(taxable);
        } else {
            throw new IllegalStateException("Failed to find corresponding tax rate in " + financialYear + ".");
        }
        return tax;
    }

    public int getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(final int financialYear) {
        this.financialYear = financialYear;
    }

    public List<IndividualTaxRate> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(final List<IndividualTaxRate> taxRates) {
        this.taxRates = taxRates;
    }

}
