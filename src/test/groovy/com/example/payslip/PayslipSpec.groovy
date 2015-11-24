package com.example.payslip

import java.time.LocalDate

import spock.lang.Specification

class PayslipSpec extends Specification {

    Payslip payslip

    def setup() {
    }

    def cleanup() {
    }

    def "test net income"() {
        when:
        payslip = new Payslip('name', new LocalDate(2015, 11, 10), new LocalDate(2015, 11, 10), grossIncome,
            incomeTax, 100, null)

        then:
        netIncome == payslip.netIncome

        where:
        grossIncome << [1000, null, 1000]
        incomeTax << [10, 10, null]
        netIncome << [990, null, null]
    }

    def "test to string"() {
        when:
        payslip = new Payslip('name', new LocalDate(2015, 11, 10), new LocalDate(2015, 11, 10), 10000,
            1000, 100, 'error')

        then:
        payslip.toString() == 'name,2015-11-10,2015-11-10,10000,1000,9000,100,error'
    }

}
