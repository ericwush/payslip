package com.example.payslip

import java.time.LocalDate

import spock.lang.Specification

class EmployeeDetailsLoaderSpec extends Specification {

    EmployeeDetailsLoader employeeDetailsLoader

    def setup() {
        employeeDetailsLoader = Spy()
    }

    def cleanup() {
    }

    def "test get annual salary"() {
        expect:
        annualSalary == employeeDetailsLoader.getAnnualSalary(field)

        where:
        field << ['100', '10.0', 'text']
        annualSalary << [100, null, null]
    }

    def "test get super rate"() {
        expect:
        superRate == employeeDetailsLoader.getSuperRate(field)

        where:
        field << ['100', '10.0', 'text']
        superRate << [1.0F, 0.1F, null]
    }

    def "test get pay period type"() {
        expect:
        payPeriodType == employeeDetailsLoader.getPayPeriodType(field)

        where:
        field << ['MONTH', 'month', 'text']
        payPeriodType << [PayPeriodType.MONTH, null, null]
    }

    def "test get start date"() {
        expect:
        startDate == employeeDetailsLoader.getStartDate(field)

        where:
        field << ['1/4/2015', '01/04/2015', '1/4/15', 'text']
        startDate << [null, new LocalDate(2015, 4, 1), null, null]
    }

}
