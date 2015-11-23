package com.example.payslip.tax

import spock.lang.Specification

import com.fasterxml.jackson.databind.ObjectMapper

class TaxRateLoaderSpec extends Specification {

    TaxRateLoader taxRateLoader
    ObjectMapper mapper
    InputStream inputStream

    def setup() {
        mapper = Mock()
        inputStream = Mock()
        taxRateLoader = Spy(constructorArgs: [mapper])
    }

    def cleanup() {
    }

    def "test mapper called for mapping json to object"() {
        when:
        taxRateLoader.mapTaxRateFile(inputStream)

        then:
        1 * mapper.readValue(inputStream, FinancialYearIndividualTaxRate.class)
    }

    def "test mapper cannot map json to object"() {
        setup:
        mapper.readValue(inputStream, FinancialYearIndividualTaxRate.class) >> {throw new Exception()}

        when:
        def taxRate = taxRateLoader.mapTaxRateFile(inputStream)

        then:
        taxRate == null
    }

}
