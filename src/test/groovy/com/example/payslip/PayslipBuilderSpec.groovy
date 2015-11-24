package com.example.payslip

import static java.util.UUID.randomUUID

import java.time.LocalDate

import javax.validation.ConstraintViolation
import javax.validation.Path
import javax.validation.Validator
import javax.validation.metadata.ConstraintDescriptor

import spock.lang.Specification

import com.example.payslip.employee.EmployeeDetails;
import com.example.payslip.helper.DateHelper;
import com.example.payslip.tax.TaxCalculator

class PayslipBuilderSpec extends Specification {

    PayslipBuilder payslipBuilder
    EmployeeDetails employeeDetails
    DateHelper dateHelper
    TaxCalculator taxCalculator
    Validator validator

    def setup() {
        employeeDetails = Mock()
        dateHelper = Mock()
        taxCalculator = Mock()
        validator = Mock()
        payslipBuilder = Spy(constructorArgs: [dateHelper, taxCalculator, validator])
    }

    def cleanup() {
    }

    def "test constructor"() {
        when:
        payslipBuilder = new PayslipBuilder(dateHelper1, taxCalculator1, validator1)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == message

        where:
        dateHelper1 << [null, Mock(DateHelper), Mock(DateHelper)]
        taxCalculator1 << [Mock(TaxCalculator), null, Mock(TaxCalculator)]
        validator1 << [Mock(Validator), Mock(Validator), null]
        message << ['DateHelper cannot be null', 'TaxCalculator cannot be null', 'Validator cannot be null']
    }

    def "test end date"() {
        when:
        payslipBuilder.endDate(employeeDetails)

        then:
        1 * employeeDetails.getStartDate()
        1 * employeeDetails.getPayPeriodType()
        1 * dateHelper.calcEndDate(_, _)
    }

    def "test gross income"() {
        setup:
        employeeDetails.getPayPeriodType() >> PayPeriodType.MONTH
        employeeDetails.getAnnualSalary() >> annualSalary

        when:
        def output = payslipBuilder.grossIncome(employeeDetails)

        then:
        output == grossIncome

        where:
        annualSalary << [2401, 2600, 200000]
        grossIncome << [200, 217, 16667]
    }

    def "test income tax"() {
        setup:
        dateHelper.findFinancialYear(_) >> 2015
        taxCalculator.calc(_, _) >> tax
        employeeDetails.getPayPeriodType() >> PayPeriodType.MONTH
        employeeDetails.getAnnualSalary() >> 1000

        when:
        def output = payslipBuilder.incomeTax(employeeDetails)

        then:
        output == incomeTax

        where:
        tax << [2401, 2600, 200000]
        incomeTax << [200, 217, 16667]
    }

    def "test superannuation" () {
        setup:
        employeeDetails.getSuperRate() >> superRate
        payslipBuilder.grossIncome(employeeDetails) >> grossIncome

        when:
        def output = payslipBuilder.superannuation(employeeDetails)

        then:
        output == superannuation

        where:
        superRate << [0.9, 0.4, 0.93]
        grossIncome << [123, 101, 1234]
        superannuation << [111, 40, 1148]
    }

    def "test sucessful payslip build"() {
        setup:
        validator.validate(employeeDetails) >> null
        employeeDetails.getName() >> name
        employeeDetails.getStartDate() >> date
        payslipBuilder.endDate(employeeDetails) >> date
        payslipBuilder.grossIncome(employeeDetails) >> grossIncome
        payslipBuilder.incomeTax(employeeDetails) >> incomeTax
        payslipBuilder.superannuation(employeeDetails) >> superannuation

        when:
        def payslip = payslipBuilder.build(employeeDetails)

        then:
        payslip.name == name
        payslip.startDate == date
        payslip.endDate == date
        payslip.grossIncome == grossIncome
        payslip.incomeTax == incomeTax
        payslip.superannuation == superannuation
        payslip.error == null

        where:
        name = randomUUID() as String
        date = new LocalDate(2015, 11, 20)
        grossIncome = new Random().nextInt(10000)
        incomeTax = new Random().nextInt(1000)
        superannuation = new Random().nextInt(500)
    }

    def "test invalid employee details"() {
        setup:
        validator.validate(employeeDetails) >> [new CustomConstraintViolation(), new CustomConstraintViolation()]
        employeeDetails.getName() >> name
        employeeDetails.getStartDate() >> date
        payslipBuilder.endDate(employeeDetails) >> date
        payslipBuilder.grossIncome(employeeDetails) >> grossIncome
        payslipBuilder.incomeTax(employeeDetails) >> incomeTax
        payslipBuilder.superannuation(employeeDetails) >> { throw new Exception(message) }

        when:
        def payslip = payslipBuilder.build(employeeDetails)

        then:
        payslip.name == name
        payslip.startDate == date
        payslip.endDate == null
        payslip.grossIncome == null
        payslip.incomeTax == null
        payslip.superannuation == null
        payslip.error == message

        where:
        message = "some error some error "
        name = randomUUID() as String
        date = new LocalDate(2015, 11, 20)
        grossIncome = new Random().nextInt(10000)
        incomeTax = new Random().nextInt(1000)
        superannuation = new Random().nextInt(500)
    }

    def "test failed payslip build"() {
        setup:
        validator.validate(employeeDetails) >> null
        employeeDetails.getName() >> name
        employeeDetails.getStartDate() >> date
        payslipBuilder.endDate(employeeDetails) >> date
        payslipBuilder.grossIncome(employeeDetails) >> grossIncome
        payslipBuilder.incomeTax(employeeDetails) >> incomeTax
        payslipBuilder.superannuation(employeeDetails) >> { throw new Exception(message) }

        when:
        def payslip = payslipBuilder.build(employeeDetails)

        then:
        payslip.name == name
        payslip.startDate == date
        payslip.endDate == null
        payslip.grossIncome == null
        payslip.incomeTax == null
        payslip.superannuation == null
        payslip.error == message

        where:
        message = randomUUID() as String
        name = randomUUID() as String
        date = new LocalDate(2015, 11, 20)
        grossIncome = new Random().nextInt(10000)
        incomeTax = new Random().nextInt(1000)
        superannuation = new Random().nextInt(500)
    }

    class CustomConstraintViolation implements ConstraintViolation {

        @Override
        String getMessage() {
            return 'some error'
        }

        @Override
        String getMessageTemplate() {
            return null
        }

        @Override
        Object getRootBean() {
            return null
        }

        @Override
        Class getRootBeanClass() {
            return null
        }

        @Override
        Object getLeafBean() {
            return null
        }

        @Override
        Object[] getExecutableParameters() {
            return null
        }

        @Override
        Object getExecutableReturnValue() {
            return null
        }

        @Override
        Path getPropertyPath() {
            return null
        }

        @Override
        Object getInvalidValue() {
            return null
        }

        @Override
        ConstraintDescriptor getConstraintDescriptor() {
            return null
        }

        @Override
        Object unwrap(Class type) {
            return null
        }
    }

}
