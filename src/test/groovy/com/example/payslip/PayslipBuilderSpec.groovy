package com.example.payslip

import spock.lang.Specification

import com.example.payslip.tax.TaxCalculator

class PayslipBuilderSpec extends Specification {

    PayslipBuilder payslipBuilder
    EmployeeDetails employeeDetails
    DateHelper dateHelper
    TaxCalculator taxCalculator

    def setup() {
        employeeDetails = Mock()
        dateHelper = Mock()
        taxCalculator = Mock()
        payslipBuilder = Spy(constructorArgs: [employeeDetails, dateHelper, taxCalculator])
    }

    def cleanup() {
    }

    def "test constructor"() {
        when:
        payslipBuilder = new PayslipBuilder(employeeDetails1, dateHelper1, taxCalculator1)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == message

        where:
        employeeDetails1 << [null, Mock(EmployeeDetails), Mock(EmployeeDetails)]
        dateHelper1 << [Mock(DateHelper), null, Mock(DateHelper)]
        taxCalculator1 << [Mock(TaxCalculator), Mock(TaxCalculator), null]
        message << ['EmployeeDetails cannot be null', 'DateHelper cannot be null', 'TaxCalculator cannot be null']
    }

    def "test end date"() {
        when:
        payslipBuilder.endDate()

        then:
        1 * employeeDetails.getStartDate()
        1 * employeeDetails.getPayPeriodType()
        1 * dateHelper.calcEndDate(_, _)
    }

    def "test gross income"() {
        when:
        employeeDetails.getPayPeriodType() >> PayPeriodType.MONTH
        employeeDetails.getAnnualSalary() >> annualSalary
        payslipBuilder.grossIncome()

        then:
        payslipBuilder.grossIncome == grossIncome

        where:
        annualSalary << [2401, 2600]
        grossIncome << [200, 216]
    }

    def "test income tax"() {
        when:
        dateHelper.findFinancialYear(_) >> 2015
        taxCalculator.calc(_, _) >> tax
        employeeDetails.getPayPeriodType() >> PayPeriodType.MONTH
        employeeDetails.getAnnualSalary() >> 1000
        payslipBuilder.incomeTax()

        then:
        payslipBuilder.incomeTax == incomeTax

        where:
        tax << [2401, 2600]
        incomeTax << [200, 216]
    }

    def "test net income"() {
        when:
        payslipBuilder.getGrossIncome() >> grossIncome
        payslipBuilder.getIncomeTax() >> incomeTax
        payslipBuilder.netIncome()

        then:
        payslipBuilder.netIncome == netIncome

        where:
        grossIncome = 100
        incomeTax = 25
        netIncome = 75
    }

    def "test superannuation" () {
        when:
        employeeDetails.getSuperRate() >> superRate
        payslipBuilder.getGrossIncome() >> grossIncome
        payslipBuilder.superannuation()

        then:
        payslipBuilder.superannuation == superannuation

        where:
        superRate << [0.9, 0.4]
        grossIncome << [123, 101]
        superannuation << [111, 40]
    }

    def "test payslip build"() {
        when:
        employeeDetails.getPayPeriodType() >> PayPeriodType.MONTH
        employeeDetails.getAnnualSalary() >> 1000
        dateHelper.findFinancialYear(_) >> 2015
        taxCalculator.calc(_, _) >> 100
        employeeDetails.getSuperRate() >> 0.9
        payslipBuilder.build()

        then:
        1 * payslipBuilder.endDate()
        1 * payslipBuilder.grossIncome()
        1 * payslipBuilder.incomeTax()
        1 * payslipBuilder.netIncome()
        1 * payslipBuilder.superannuation()
    }

}
