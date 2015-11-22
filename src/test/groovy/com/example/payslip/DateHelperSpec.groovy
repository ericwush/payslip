package com.example.payslip

import java.time.LocalDate

import spock.lang.Specification

class DateHelperSpec extends Specification {

    DateHelper dateHelper

    def setup() {
        dateHelper = Spy()
    }

    def cleanup() {
    }

    def "test end date calculation"() {
        when:
        LocalDate startDate = LocalDate.of(2015, 11, 22)
        LocalDate endDate = dateHelper.calcEndDate(startDate, PayPeriodType.MONTH)

        then:
        endDate == LocalDate.of(2015, 11, 30)
    }

    def "test financial year"() {
        expect:
        dateHelper.findFinancialYear(date) == financialYear

        where:
        date << [LocalDate.of(2015, 11, 22), LocalDate.of(2015, 1, 22)]
        financialYear << [2015, 2014]
    }

}