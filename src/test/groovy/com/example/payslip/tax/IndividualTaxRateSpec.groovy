package com.example.payslip.tax

import spock.lang.Specification

class IndividualTaxRateSpec extends Specification {

    IndividualTaxRate taxRate

    def setup() {
        taxRate = Spy()
    }

    def cleanup() {
    }

    def "test whether tax rate is accepted"() {
        when:
        taxRate.minIncome = minIncome
        taxRate.maxIncome = maxIncome
        boolean accepted = taxRate.accept(income)

        then:
        accepted == output

        where:
        minIncome << [null, 10, 100, null, null, 10, 101, null]
        maxIncome << [null, null, null, 101, 100, 200, null, 99]
        income << [100, 100, 100, 100, 100, 100, 100, 100]
        output << [true, true, true, true, true, true, false, false]
    }

    def "test applying tax rate"() {
        when:
        taxRate.baseIncome = baseIncome
        taxRate.baseTax = baseTax
        taxRate.rate = rate

        then:
        output == taxRate.apply(income)

        where:
        income << [150, 150, 150]
        baseIncome << [100, 0, 0]
        baseTax << [50, 0, 10]
        rate << [0.5, 0.3, 0.325]
        output << [75, 45, 59]
    }

}
