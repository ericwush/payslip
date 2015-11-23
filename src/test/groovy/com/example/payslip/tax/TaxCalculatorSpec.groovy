package com.example.payslip.tax

import spock.lang.Specification

class TaxCalculatorSpec extends Specification {

    TaxCalculator taxCalculator
    FinancialYearIndividualTaxRate taxRate1
    FinancialYearIndividualTaxRate taxRate2
    FinancialYearIndividualTaxRate taxRate3

    def setup() {
        taxRate1 = Mock()
        taxRate2 = Mock()
        taxRate3 = Mock()
    }

    def cleanup() {
    }

    def "test tax calculation"() {
        setup:
        def taxRates = [taxRate1, taxRate2, taxRate3]
        taxCalculator = Spy(constructorArgs: [taxRates])
        taxRate2.accept(year) >> taxRate2
        taxRate2.apply(taxable) >> output

        when:
        def tax = taxCalculator.calc(year, taxable)

        then:
        1 * taxRate1.accept(_)
        0 * taxRate3.accept(_)
        tax == output

        where:
        year = Integer.valueOf(new Random().nextInt(2015))
        taxable = Integer.valueOf(new Random().nextInt(100))
        output = Integer.valueOf(new Random().nextInt(50))
    }

    def "test no tax rate found for income"() {
        setup:
        def taxRates = [taxRate1, taxRate2, taxRate3]
        taxCalculator = Spy(constructorArgs: [taxRates])

        when:
        def tax = taxCalculator.calc(year, taxable)

        then:
        1 * taxRate1.accept(_)
        1 * taxRate2.accept(_)
        1 * taxRate3.accept(_)
        def e = thrown(IllegalStateException)
        e.message == message

        where:
        year = Integer.valueOf(new Random().nextInt(2015))
        taxable = Integer.valueOf(new Random().nextInt(100))
        message = "Failed to find corresponding tax rates for ${year}."
    }

}
