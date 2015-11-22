package com.example.payslip;

import java.time.LocalDate;

/**
 * Immutable payslip model.
 * @author ericwu
 *
 */
public class Payslip {

    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer grossIncome;
    private final Integer incomeTax;
    private final Integer netIncome;
    private final Integer superannuation;
    private final String error;

    public Payslip(final String name, final LocalDate startDate, final LocalDate endDate, final Integer grossIncome,
            final Integer incomeTax, final Integer netIncome, final Integer superannuation, final String error) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
        this.netIncome = netIncome;
        this.superannuation = superannuation;
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
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
