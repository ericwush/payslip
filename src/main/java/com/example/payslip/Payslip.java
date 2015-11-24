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
    private final Integer superannuation;
    private final String error;

    public Payslip(final String name, final LocalDate startDate, final LocalDate endDate, final Integer grossIncome,
            final Integer incomeTax, final Integer superannuation, final String error) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grossIncome = grossIncome;
        this.incomeTax = incomeTax;
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
        Integer netIncome = null;
        if (grossIncome != null && incomeTax != null) {
            netIncome = grossIncome.intValue() - incomeTax.intValue();
        }
        return netIncome;
    }

    public Integer getSuperannuation() {
        return superannuation;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName())
            .append(",")
            .append(getStartDate() != null ? getStartDate().toString() : "")
            .append(",")
            .append(getEndDate() != null ? getEndDate().toString() : "")
            .append(",")
            .append(getGrossIncome() != null ? getGrossIncome().toString() : "")
            .append(",")
            .append(getIncomeTax() != null ? getIncomeTax().toString() : "")
            .append(",")
            .append(getNetIncome() != null ? getNetIncome().toString() : "")
            .append(",")
            .append(getSuperannuation() != null ? getSuperannuation().toString() : "")
            .append(",")
            .append(getError() != null ? getError().toString() : "");
        return sb.toString();
    }

}
