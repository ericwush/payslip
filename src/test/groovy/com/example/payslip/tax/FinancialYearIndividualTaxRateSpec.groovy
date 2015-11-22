package com.example.payslip.tax

import java.time.Year

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
        when:
        taxRate = Spy()
        taxRate.financialYear = financialYear
        taxRate.taxRates = [taxRate1]
        boolean accepted = taxRate.accept(year)

        then:
        accepted == output

        where:
        financialYear << [Year.of(2015), Year.of(2014)]
        year << [Year.of(2015), Year.of(2015)]
        output << [true, false]
    }

    def "test applying tax rate"() {
        when:
        IndividualTaxRate[] taxRates = [taxRate1, taxRate2, taxRate3]
        taxRate = Spy()
        taxRate.financialYear = Year.of(2015)
        taxRate.taxRates = taxRates
        taxRate2.accept(_) >> taxRate2
        taxRate2.apply(_) >> output
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
        when:
        IndividualTaxRate[] taxRates = [taxRate1, taxRate2, taxRate3]
        taxRate = Spy()
        taxRate.financialYear = Year.of(2015)
        taxRate.taxRates = taxRates
        def tax = taxRate.apply(income)

        then:
        1 * taxRate1.accept(_)
        1 * taxRate2.accept(_)
        1 * taxRate3.accept(_)
        tax == null

        where:
        income = Integer.valueOf(new Random().nextInt(100))
    }

}
