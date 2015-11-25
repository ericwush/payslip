package com.example.payslip

import java.time.LocalDate

import org.apache.commons.io.FileUtils

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
        List<String> lines = FileUtils.readLines(new File('src/test/resources/output.csv'))

        then:
        lines.size() == 2
        lines[0] == '"Name","Start date","End date","Gross income","Income tax","Net income","Super","Error"'
        lines[1] == '"Foo","2015-11-25","2015-11-25","1000","100","900","10","error"'

        cleanup:
        FileUtils.deleteQuietly(new File('src/test/resources/output.csv'))
    }

}
