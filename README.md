# Payslip

### How to build
In project directory: 
* MAC
```sh
$ ./gradlew clean build
```
* Windows
```sh
$ gradlew.bat clean build
```
Artifact is built into build/libs folder.

### Input file format
```
Name,AnnualSalary,SuperRate(%),PayPeriodType,StartDate(dd/MM/yyyy)
"David Rudd","60050","9","MONTHLY","01/11/2012"
"Ryan Chen","120000","10","MONTHLY","01/11/2015"
```
 - Headers are mandatory.
 - Currently only support "MONTHLY" for PayPeriodType, case sensitive.

### How to run
* MAC
```
java -cp "payslip.jar:." com.example.payslip.PayslipGenerator [input.csv] [output.csv] [tax.json]
```
* Windows
```
java -cp "payslip.jar;." com.example.payslip.PayslipGenerator [input.csv] [output.csv] [tax.json]
```
Class PayslipGenerator's main method takes three arguments:
* input filename
* output filename
* tax configuration filename
Both input and tax config files must be on classpath, it can be specified by java -cp/-classpath.
In above example they sit in same directory as payslip.jar does.

### Tax rates configuration
Tax rates must be configured in JSON format. Please refer to [tax.json]. It has 2012 and 2015 FYs configured.

### Test coverage
In project directory: 
* MAC
```sh
$ ./gradlew cobertura
```
* Windows
```sh
$ gradlew.bat cobertura
```
Reports are built into build/reports/cobertura.

### Assumptions
- Tax rates are configured by financial years. Financial year goes from 1st July to 30th June next year.
- Tax rates are configured correctly in JSON files. No gaps and overlaps based on income.

[tax.json]: <https://github.com/ericwush/payslip/blob/master/src/main/resources/tax.json>
