package com.example.payslip

import java.time.LocalDate

import spock.lang.Specification

class PayslipWriterIntegrationSpec extends Specification {

    PayslipWriter payslipWriter

    def setup() {
        payslipWriter = new PayslipWriter()
    }

    def cleanup() {
    }

    def "test writing payslip"() {
        when:
        Payslip payslip = new Payslip('Foo', LocalDate.of(2015, 11, 25), LocalDate.of(2015, 11, 25),
            1000, 100, 10, 'error')
        payslipWriter.write('src/test/resources/output.csv', [payslip])
        String line1
        String line2
        def inputStreamReader
        try {
            inputStreamReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream('output.csv'))
            line1 = inputStreamReader.readLine()
            line2 = inputStreamReader.readLine()
        } finally {
            inputStreamReader.close()
        }

        then:
        line1 == '"Name","Start date","End date","Gross income","Income tax","Net income","Super","Error"'
        line2 == '"Foo","2015-11-25","2015-11-25","1000","100","900","10","error"'
    }

}
