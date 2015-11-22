package com.example.payslip.tax;

/**
 * Tax rate model for individuals.
 * @author ericwu
 *
 */
public class IndividualTaxRate implements TaxRate {

    private Integer minIncome;
    private Integer maxIncome;
    private Integer baseTax;
    private Integer baseIncome;
    private Float rate;

    public boolean accept(final Integer taxable) {
        boolean accepted = false;
        if ((minIncome == null || taxable.compareTo(minIncome) >= 0)
                && (maxIncome == null || taxable.compareTo(maxIncome) <= 0)) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public Integer apply(final Integer taxable) {
        return Math.round(baseTax.intValue() + (taxable.intValue() - baseIncome.intValue()) * rate);
    }

    public Integer getMinIncome() {
        return minIncome;
    }

    public void setMinIncome(final Integer minIncome) {
        this.minIncome = minIncome;
    }

    public Integer getMaxIncome() {
        return maxIncome;
    }

    public void setMaxIncome(final Integer maxIncome) {
        this.maxIncome = maxIncome;
    }

    public Integer getBaseTax() {
        return baseTax;
    }

    public void setBaseTax(final Integer baseTax) {
        this.baseTax = baseTax;
    }

    public Integer getBaseIncome() {
        return baseIncome;
    }

    public void setBaseIncome(final Integer baseIncome) {
        this.baseIncome = baseIncome;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(final Float rate) {
        this.rate = rate;
    }

}
