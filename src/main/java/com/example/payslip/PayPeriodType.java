package com.example.payslip;

/**
 * Enum that represents pay period type.
 * @author ericwu
 *
 */
public enum PayPeriodType {

    MONTH(12);

    private final int divisor;

    private PayPeriodType(int divisor) {
        this.divisor = divisor;

    }

    public int getDivisor() {
        return divisor;
    }

}
