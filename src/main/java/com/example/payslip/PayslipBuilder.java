package com.example.payslip;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.example.payslip.tax.TaxCalculator;

/**
 * Build payslip based on employee details and tax calculator.
 * @author ericwu
 *
 */
public class PayslipBuilder {

    private final DateHelper dateHelper;
    private final TaxCalculator taxCalculator;
    private final Validator validator;

    public PayslipBuilder(final DateHelper dateHelper, final TaxCalculator taxCalculator, final Validator validator) {
        if (dateHelper == null) {
            throw new IllegalArgumentException("DateHelper cannot be null");
        }
        if (taxCalculator == null) {
            throw new IllegalArgumentException("TaxCalculator cannot be null");
        }
        if (validator == null) {
            throw new IllegalArgumentException("Validator cannot be null");
        }
        this.validator = validator;
        this.dateHelper = dateHelper;
        this.taxCalculator = taxCalculator;
    }

    public LocalDate endDate(final EmployeeDetails employeeDetails) {
        return dateHelper.calcEndDate(employeeDetails.getStartDate(), employeeDetails.getPayPeriodType());
    }

    public Integer grossIncome(final EmployeeDetails employeeDetails) {
        return Math.round(employeeDetails.getAnnualSalary().intValue() / employeeDetails.getPayPeriodType().getDivisor());
    }

    public Integer incomeTax(final EmployeeDetails employeeDetails) {
        return Math.round(taxCalculator.calc(dateHelper.findFinancialYear(employeeDetails.getStartDate()),
                employeeDetails.getAnnualSalary()) / employeeDetails.getPayPeriodType().getDivisor());
    }

    public Integer superannuation(final EmployeeDetails employeeDetails) {
        return Math.round(grossIncome(employeeDetails) * employeeDetails.getSuperRate());
    }

    public Payslip build(final EmployeeDetails employeeDetails) {
        Payslip payslip = null;
        try {
            final Set<ConstraintViolation<EmployeeDetails>> violations = validator.validate(employeeDetails);
            if (violations != null && violations.size() > 0) {
                final StringBuilder error = new StringBuilder();
                for (final ConstraintViolation<EmployeeDetails> violation : violations) {
                    error.append(violation.getMessage()).append(" ");
                }
                throw new IllegalArgumentException(error.toString());
            }
            payslip = new Payslip(employeeDetails.getName(), employeeDetails.getStartDate(), endDate(employeeDetails),
                    grossIncome(employeeDetails), incomeTax(employeeDetails), superannuation(employeeDetails), null);
        } catch (final Exception e) {
            payslip = new Payslip(employeeDetails.getName(), employeeDetails.getStartDate(), null,
                    null, null, null, e.getMessage());
        }
        return payslip;
    }

}
