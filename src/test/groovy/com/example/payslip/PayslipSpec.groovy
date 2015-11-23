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
        grossIncome = 1000
        incomeTax = 10
        netIncome = 990
    }

}
