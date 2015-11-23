package com.example.payslip.tax

import spock.lang.Specification

class FinancialYearIndividualTaxRateSpec extends Specification {

    FinancialYearIndividualTaxRate taxRate
    IndividualTaxRate taxRate1
    IndividualTaxRate taxRate2
    IndividualTaxRate taxRate3

    def setup() {
        taxRate1 = Mock()
        taxRate2 = Mock()
        taxRate3 = Mock()
    }

    def cleanup() {
    }

    def "test whether tax rate year is accepted"() {
        setup:
        taxRate = Spy()
        taxRate.financialYear = financialYear
        taxRate.taxRates = [taxRate1]

        when:
        boolean accepted = taxRate.accept(year)

        then:
        accepted == output

        where:
        financialYear << [2015, 2014]
        year << [2015, 2015]
        output << [true, false]
    }

    def "test applying tax rate"() {
        setup:
        IndividualTaxRate[] taxRates = [taxRate1, taxRate2, taxRate3]
        taxRate = Spy()
        taxRate.financialYear = 2015
        taxRate.taxRates = taxRates
        taxRate2.accept(_) >> taxRate2
        taxRate2.apply(_) >> output

        when:
        def tax = taxRate.apply(income)

        then:
        1 * taxRate1.accept(_)
        0 * taxRate3.accept(_)
        tax == output

        where:
        income = Integer.valueOf(new Random().nextInt(100))
        output = Integer.valueOf(new Random().nextInt(50))
    }

    def "test no tax rate accepted for income"() {
        setup:
        IndividualTaxRate[] taxRates = [taxRate1, taxRate2, taxRate3]
        taxRate = Spy()
        taxRate.financialYear = financialYear
        taxRate.taxRates = taxRates

        when:
        def tax = taxRate.apply(income)

        then:
        1 * taxRate1.accept(_)
        1 * taxRate2.accept(_)
        1 * taxRate3.accept(_)
        def e = thrown(IllegalStateException)
        e.message == message

        where:
        income = Integer.valueOf(new Random().nextInt(100))
        financialYear = 2015
        message = "Failed to find corresponding tax rate in ${financialYear}."
    }

}
