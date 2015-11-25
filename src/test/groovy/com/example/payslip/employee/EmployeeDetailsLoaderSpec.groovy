package com.example.payslip.employee

import java.time.LocalDate

import spock.lang.Specification

import com.example.payslip.PayPeriodType

class EmployeeDetailsLoaderSpec extends Specification {

    EmployeeDetailsLoader employeeDetailsLoader

    def setup() {
        employeeDetailsLoader = Spy()
    }

    def cleanup() {
    }

    def "test get name"() {
        expect:
        name == employeeDetailsLoader.getName(field)

        where:
        field << ['ABC', null, '', ' ABC ']
        name << ['ABC', null, null, 'ABC']
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
        field << ['MONTHLY', 'month', 'text']
        payPeriodType << [PayPeriodType.MONTHLY, null, null]
    }

    def "test get start date"() {
        expect:
        startDate == employeeDetailsLoader.getStartDate(field)

        where:
        field << ['1/4/2015', '01/04/2015', '1/4/15', 'text']
        startDate << [null, LocalDate.of(2015, 4, 1), null, null]
    }

}
