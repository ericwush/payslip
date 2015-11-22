package com.example.payslip;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.LocalDate;

/**
 * Date related helper class.
 * @author ericwu
 *
 */
public class DateHelper {

    public LocalDate calcEndDate(final LocalDate startDate, final PayPeriodType payPeriodType) {
        LocalDate endDate = null;
        if (startDate != null && payPeriodType != null) {
            switch (payPeriodType) {
                case MONTH:
                    endDate = startDate.with(lastDayOfMonth());
                    break;
                default:
                    break;
            }
        }
        return endDate;
    }

    public int findFinancialYear(final LocalDate date) {
        int financialYear = date.getYear();
        if (date.getMonth().getValue() >= 1 && date.getMonth().getValue() <= 6) {
            financialYear --;
        }
        return financialYear;
    }

}
