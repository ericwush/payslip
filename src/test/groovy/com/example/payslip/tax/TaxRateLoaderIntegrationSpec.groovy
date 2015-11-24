package com.example.payslip.tax

import spock.lang.Specification

import com.fasterxml.jackson.databind.ObjectMapper

class TaxRateLoaderIntegrationSpec extends Specification {

    TaxRateLoader taxRateLoader

    def setup() {
        taxRateLoader = new TaxRateLoader(new ObjectMapper())
    }

    def cleanup() {
    }

    def "test loading tax rates"() {
        when:
        List<FinancialYearIndividualTaxRate> taxRates = taxRateLoader.load()

        then:
        taxRates.size() == 2
        taxRates[0].financialYear == 2012
        taxRates[0].taxRates.size() == 5
        taxRates[0].taxRates[0].minIncome == 0
        taxRates[0].taxRates[0].maxIncome == 18200
        taxRates[0].taxRates[0].baseTax == 0
        taxRates[0].taxRates[0].baseIncome == 0
        taxRates[0].taxRates[0].rate == 0f
    }

    def "test tax rate filenames not found"() {
        expect:
        taxRateLoader.getTaxRateFilenames('notfound') == null
    }

}
