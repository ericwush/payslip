package com.example.payslip;

import java.time.LocalDate;

/**
 * Employee details model.
 * @author ericwu
 *
 */
public class EmployeeDetails {

    private String name;
    private PayPeriodType payPeriodType;
    private LocalDate startDate;
    private Integer annualSalary;
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
