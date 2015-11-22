package com.example.payslip.tax;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Individual tax rates for a specific financial year.
 * @author ericwu
 *
 */
public class FinancialYearIndividualTaxRate implements TaxRate {

    private Year financialYear;
    private List<IndividualTaxRate> taxRates = new ArrayList<>();

    public boolean accept(final Year year) {
        boolean accepted = false;
        if (financialYear.equals(year)) {
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
        }
        return tax;
    }

    public Year getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(final Year financialYear) {
        this.financialYear = financialYear;
    }

    public List<IndividualTaxRate> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(final List<IndividualTaxRate> taxRates) {
        this.taxRates = taxRates;
    }

}
