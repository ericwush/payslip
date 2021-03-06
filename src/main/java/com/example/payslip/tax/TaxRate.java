package com.example.payslip.tax;

/**
 * Interface for tax rate to be applied.
 * @author ericwu
 *
 */
public interface TaxRate {

    Float apply(Integer taxable);

}
