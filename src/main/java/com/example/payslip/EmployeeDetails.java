package com.example.payslip;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * Employee details model.
 * @author ericwu
 *
 */
public class EmployeeDetails {

    @NotNull(message = "Employee name cannot be null.")
    private String name;
    @NotNull(message = "Invalid or null pay period type.")
    private PayPeriodType payPeriodType;
    @NotNull(message = "Invalid or null start date.")
    private LocalDate startDate;
    @NotNull(message = "Invalid or null annual salary.")
    private Integer annualSalary;
    @NotNull(message = "Invalid or null super rate.")
    private Float superRate;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public PayPeriodType getPayPeriodType() {
        return payPeriodType;
    }

    public void setPayPeriodType(final PayPeriodType payPeriodType) {
        this.payPeriodType = payPeriodType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(final Integer annualSalary) {
        this.annualSalary = annualSalary;
    }

    public Float getSuperRate() {
        return superRate;
    }

    public void setSuperRate(final Float superRate) {
        this.superRate = superRate;
    }

}
