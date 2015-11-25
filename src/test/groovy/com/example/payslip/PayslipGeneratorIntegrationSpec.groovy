package com.example.payslip

import org.apache.commons.io.FileUtils

import spock.lang.Specification

class PayslipGeneratorIntegrationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "test main method arguments"() {
        when:
        String[] args = []
        PayslipGenerator.main(args)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == 'Must provide input, output csv filenames and tax rates filename'
    }

    def "test input file not found"() {
        when:
        PayslipGenerator.main('notfound.csv', 'src/test/resources/output.csv', 'tax.json')

        then:
        def e = thrown(IllegalStateException)
        e.message == 'Failed to read file notfound.csv.'
    }

    def "test successful payslip generation"() {
        when:
        PayslipGenerator.main('input.csv', 'src/test/resources/output.csv', 'tax.json')
        List<String> lines = FileUtils.readLines(new File('src/test/resources/output.csv'))

        then:
        lines.size() == 3
        lines[0] == '"Name","Start date","End date","Gross income","Income tax","Net income","Super","Error"'
        lines[1] == '"David Rudd","2012-11-01","2012-11-30","5004","922","4082","450",""'
        lines[2] == '"Ryan Chen","2012-11-01","2012-11-30","10000","2696","7304","1000",""'

        cleanup:
        FileUtils.deleteQuietly(new File('src/test/resources/output.csv'))
    }

    def "test invalid employee details input"() {
        when:
        PayslipGenerator.main('failed1.csv', 'src/test/resources/output.csv', 'tax.json')
        List<String> lines = FileUtils.readLines(new File('src/test/resources/output.csv'))

        then:
        lines.size() == 3
        lines[0] == '"Name","Start date","End date","Gross income","Income tax","Net income","Super","Error"'
        lines[1] == '"null","2012-11-01","","","","","","Employee name cannot be null."'
        lines[2] == '"Ryan Chen","2012-11-01","","","","","","Invalid or null annual salary."'

        cleanup:
        FileUtils.deleteQuietly(new File('src/test/resources/output.csv'))
    }

    def "test tax rate not found"() {
        when:
        PayslipGenerator.main('failed2.csv', 'src/test/resources/output.csv', 'tax.json')
        List<String> lines = FileUtils.readLines(new File('src/test/resources/output.csv'))

        then:
        lines.size() == 3
        lines[0] == '"Name","Start date","End date","Gross income","Income tax","Net income","Super","Error"'
        lines[1] == '"David Rudd","2010-11-01","","","","","","Failed to find corresponding tax rates for 2010."'
        lines[2] == '"Ryan Chen","2012-01-01","","","","","","Failed to find corresponding tax rates for 2011."'

        cleanup:
        FileUtils.deleteQuietly(new File('src/test/resources/output.csv'))
    }

}
