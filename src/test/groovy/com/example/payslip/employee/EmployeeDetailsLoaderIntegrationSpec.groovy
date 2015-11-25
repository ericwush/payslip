package com.example.payslip.employee

import java.time.LocalDate

import spock.lang.Specification

import com.example.payslip.PayPeriodType

class EmployeeDetailsLoaderIntegrationSpec extends Specification {

    EmployeeDetailsLoader employeeDetailsLoader

    def setup() {
        employeeDetailsLoader = new EmployeeDetailsLoader()
    }

    def cleanup() {
    }

    def "test loading employee details"() {
        when:
        List<EmployeeDetails> employeeDetails = employeeDetailsLoader.load('input.csv')

        then:
        employeeDetails.size() == 2
        employeeDetails[0].name == 'David Rudd'
        employeeDetails[0].annualSalary == 60050
        employeeDetails[0].superRate == 0.09f
        employeeDetails[0].payPeriodType == PayPeriodType.MONTHLY
        employeeDetails[0].startDate == LocalDate.of(2012, 11, 1)
        employeeDetails[1].name == 'Ryan Chen'
        employeeDetails[1].annualSalary == 120000
        employeeDetails[1].superRate == 0.1f
        employeeDetails[1].payPeriodType == PayPeriodType.MONTHLY
        employeeDetails[1].startDate == LocalDate.of(2012, 11, 1)
    }

    def "test file not found"() {
        when:
        List<EmployeeDetails> employeeDetails = employeeDetailsLoader.load('notfound.csv')

        then:
        def e = thrown(IllegalStateException)
        e.message == 'Failed to read file notfound.csv.'
    }

}
