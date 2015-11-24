package com.example.payslip.employee

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

import com.example.payslip.employee.EmployeeDetails;

import spock.lang.Specification

class EmployeeDetailsValidatorSpec extends Specification {

    Validator validator

    def setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator()
    }

    def cleanup() {
    }

    void "test not null constraint"() {
        setup:
        EmployeeDetails employeeDetails = new EmployeeDetails()

        when:
        Set<ConstraintViolation> violations = validator.validate(employeeDetails)
        def messages = []
        violations.each {
            messages << it.getMessage()
        }

        then:
        violations.size() == 5
        messages.contains('Employee name cannot be null.')
        messages.contains('Invalid or null pay period type.')
        messages.contains('Invalid or null start date.')
        messages.contains('Invalid or null annual salary.')
        messages.contains('Invalid or null super rate.')
    }

}
