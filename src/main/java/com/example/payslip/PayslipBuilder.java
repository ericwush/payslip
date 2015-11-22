package com.example.payslip;

import java.time.LocalDate;

import com.example.payslip.tax.TaxCalculator;

/**
 * Build payslip based on employee details and tax calculator.
 * @author ericwu
 *
 */
public class PayslipBuilder {

    private final EmployeeDetails employeeDetails;
    private final DateHelper dateHelper;
    private final TaxCalculator taxCalculator;
    private LocalDate endDate;
    private Integer grossIncome;
    private Integer incomeTax;
    private Integer netIncome;
    private Integer superannuation;
    private String error;

    public PayslipBuilder(final EmployeeDetails employeeDetails, final DateHelper dateHelper,
            final TaxCalculator taxCalculator) {
        if (employeeDetails == null) {
            throw new IllegalArgumentException("EmployeeDetails cannot be null");
        }
        if (dateHelper == null) {
            throw new IllegalArgumentException("DateHelper cannot be null");
        }
        if (taxCalculator == null) {
            throw new IllegalArgumentException("TaxCalculator cannot be null");
        }
        this.employeeDetails = employeeDetails;
        this.dateHelper = dateHelper;
        this.taxCalculator = taxCalculator;
    }

    public PayslipBuilder endDate() {
        endDate = dateHelper.calcEndDate(employeeDetails.getStartDate(), employeeDetails.getPayPeriodType());
        return this;
    }

    public PayslipBuilder grossIncome() {
        if (employeeDetails.getPayPeriodType().getDivisor() != 0) {
            grossIncome = Math.round(employeeDetails.getAnnualSalary().intValue()
                    / employeeDetails.getPayPeriodType().getDivisor());
        }
        return this;
    }

    public PayslipBuilder incomeTax() {
        incomeTax = Math.round(taxCalculator.calc(dateHelper.findFinancialYear(employeeDetails.getStartDate()),
                employeeDetails.getAnnualSalary()) / employeeDetails.getPayPeriodType().getDivisor());
        return this;
    }

    public PayslipBuilder netIncome() {
        netIncome = getGrossIncome().intValue() - getIncomeTax().intValue();
        return this;
    }

    public PayslipBuilder superannuation() {
        superannuation = Math.round(getGrossIncome() * employeeDetails.getSuperRate());
        return this;
    }

    public Payslip build() {
        try {
            endDate().grossIncome().incomeTax().netIncome().superannuation();
        } catch (Exception e) {
            error = e.getMessage();
        }
        return new Payslip(employeeDetails.getName(), employeeDetails.getStartDate(), getEndDate(), getGrossIncome(),
                getIncomeTax(), getNetIncome(), getSuperannuation(), getError());
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getGrossIncome() {
        return grossIncome;
    }

    public Integer getIncomeTax() {
        return incomeTax;
    }

    public Integer getNetIncome() {
        return netIncome;
    }

    public Integer getSuperannuation() {
        return superannuation;
    }

    public String getError() {
        return error;
    }

}
