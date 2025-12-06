package com.example;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UtilityTypeRepository utilityTypeRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TariffRepository tariffRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (employeeRepository.count() == 0) {
            loadSampleData();
            System.out.println("Sample data loaded successfully!");
        }
    }

    private void loadSampleData() {
        // Create utility types
        UtilityType electricity = new Electricity();
        electricity.setUtilityName("Electricity");
        ((Electricity) electricity).setUnitType("kWh");
        utilityTypeRepository.save(electricity);

        UtilityType water = new Water();
        water.setUtilityName("Water");
        ((Water) water).setUnitType("m³");
        utilityTypeRepository.save(water);

        UtilityType gas = new Gas();
        gas.setUtilityName("Gas");
        ((Gas) gas).setUnitType("units");
        utilityTypeRepository.save(gas);

        // Create employees
        Employee admin = new Admin();
        admin.setFullName("Admin User");
        admin.setNic("123456789V");
        admin.setRole(Employee.EmployeeRole.ADMIN);
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setContactNo("0771234567");
        admin.setDateJoined(LocalDate.of(2025, 1, 1));
        employeeRepository.save(admin);

        Employee meterReader = new MeterReader();
        meterReader.setFullName("Meter Reader");
        meterReader.setNic("987654321V");
        meterReader.setRole(Employee.EmployeeRole.METER_READER);
        meterReader.setUsername("meterreader");
        meterReader.setPassword("reader");
        meterReader.setContactNo("0772345678");
        meterReader.setDateJoined(LocalDate.of(2025, 1, 1));
        employeeRepository.save(meterReader);

        Employee cashier = new Cashier();
        cashier.setFullName("Cashier User");
        cashier.setNic("456789123V");
        cashier.setRole(Employee.EmployeeRole.CASHIER);
        cashier.setUsername("cashier");
        cashier.setPassword("cash");
        cashier.setContactNo("0773456789");
        cashier.setDateJoined(LocalDate.of(2025, 1, 1));
        employeeRepository.save(cashier);

        Employee manager = new Manager();
        manager.setFullName("Manager User");
        manager.setNic("789123456V");
        manager.setRole(Employee.EmployeeRole.MANAGER);
        manager.setUsername("manager");
        manager.setPassword("mgr");
        manager.setContactNo("0774567890");
        manager.setDateJoined(LocalDate.of(2025, 1, 1));
        employeeRepository.save(manager);

        // Create customers
        Customer household = new Household();
        household.setFullName("John Doe");
        household.setNic("111111111V");
        household.setCustomerType(Customer.CustomerType.HOUSEHOLD);
        household.setContactNo("0771111111");
        household.setEmail("john@example.com");
        household.setCity("Colombo");
        household.setDistrict("Colombo");
        household.setStreet("Main Street");
        household.setGender(Customer.Gender.MALE);
        household.setDob(LocalDate.of(1990, 1, 1));
        ((Household) household).setFamilySize(4);
        customerRepository.save(household);

        Customer business = new Business();
        business.setFullName("ABC Company");
        business.setNic("222222222V");
        business.setCustomerType(Customer.CustomerType.BUSINESS);
        business.setContactNo("0772222222");
        business.setEmail("contact@abc.com");
        business.setCity("Colombo");
        business.setDistrict("Colombo");
        business.setStreet("Business Ave");
        ((Business) business).setBrNumber("BR123456");
        ((Business) business).setBusinessName("ABC Company Ltd");
        customerRepository.save(business);

        // Create meters
        Meter meter1 = new Meter();
        meter1.setMeterNumber("M001");
        meter1.setStatus(Meter.MeterStatus.ACTIVE);
        meter1.setInstallationDate(LocalDate.of(2025, 1, 1));
        meter1.setConnectionDate(LocalDate.of(2025, 1, 1));
        meter1.setCustomer(household);
        meter1.setUtilityType(electricity);
        meterRepository.save(meter1);

        Meter meter2 = new Meter();
        meter2.setMeterNumber("M002");
        meter2.setStatus(Meter.MeterStatus.ACTIVE);
        meter2.setInstallationDate(LocalDate.of(2025, 1, 1));
        meter2.setConnectionDate(LocalDate.of(2025, 1, 1));
        meter2.setCustomer(business);
        meter2.setUtilityType(water);
        meterRepository.save(meter2);

        // Additional sample customers (8 more to reach 10 total)
        Customer household2 = new Household();
        household2.setFullName("Priya Fernando");
        household2.setNic("333333333V");
        household2.setCustomerType(Customer.CustomerType.HOUSEHOLD);
        household2.setContactNo("0773333333");
        household2.setEmail("priya@email.com");
        household2.setCity("Kandy");
        household2.setDistrict("Kandy");
        household2.setStreet("Temple Road");
        household2.setGender(Customer.Gender.FEMALE);
        household2.setDob(LocalDate.of(1985, 5, 15));
        ((Household) household2).setFamilySize(3);
        customerRepository.save(household2);

        Customer household3 = new Household();
        household3.setFullName("Ravi Kumar");
        household3.setNic("444444444V");
        household3.setCustomerType(Customer.CustomerType.HOUSEHOLD);
        household3.setContactNo("0774444444");
        household3.setEmail("ravi@email.com");
        household3.setCity("Galle");
        household3.setDistrict("Galle");
        household3.setStreet("Beach Road");
        household3.setGender(Customer.Gender.MALE);
        household3.setDob(LocalDate.of(1978, 8, 20));
        ((Household) household3).setFamilySize(5);
        customerRepository.save(household3);

        Customer household4 = new Household();
        household4.setFullName("Nimali Silva");
        household4.setNic("555555555V");
        household4.setCustomerType(Customer.CustomerType.HOUSEHOLD);
        household4.setContactNo("0775555555");
        household4.setEmail("nimali@email.com");
        household4.setCity("Negombo");
        household4.setDistrict("Gampaha");
        household4.setStreet("Main Street");
        household4.setGender(Customer.Gender.FEMALE);
        household4.setDob(LocalDate.of(1992, 3, 10));
        ((Household) household4).setFamilySize(2);
        customerRepository.save(household4);

        Customer household5 = new Household();
        household5.setFullName("Saman Perera");
        household5.setNic("666666666V");
        household5.setCustomerType(Customer.CustomerType.HOUSEHOLD);
        household5.setContactNo("0776666666");
        household5.setEmail("saman@email.com");
        household5.setCity("Jaffna");
        household5.setDistrict("Jaffna");
        household5.setStreet("Hospital Road");
        household5.setGender(Customer.Gender.MALE);
        household5.setDob(LocalDate.of(1980, 12, 5));
        ((Household) household5).setFamilySize(4);
        customerRepository.save(household5);

        Customer household6 = new Household();
        household6.setFullName("Kumari Jayasinghe");
        household6.setNic("777777777V");
        household6.setCustomerType(Customer.CustomerType.HOUSEHOLD);
        household6.setContactNo("0777777777");
        household6.setEmail("kumari@email.com");
        household6.setCity("Matara");
        household6.setDistrict("Matara");
        household6.setStreet("Station Road");
        household6.setGender(Customer.Gender.FEMALE);
        household6.setDob(LocalDate.of(1988, 7, 25));
        ((Household) household6).setFamilySize(3);
        customerRepository.save(household6);

        Customer household7 = new Household();
        household7.setFullName("Chaminda Bandara");
        household7.setNic("888888888V");
        household7.setCustomerType(Customer.CustomerType.HOUSEHOLD);
        household7.setContactNo("0778888888");
        household7.setEmail("chaminda@email.com");
        household7.setCity("Anuradhapura");
        household7.setDistrict("Anuradhapura");
        household7.setStreet("New Town");
        household7.setGender(Customer.Gender.MALE);
        household7.setDob(LocalDate.of(1975, 11, 30));
        ((Household) household7).setFamilySize(6);
        customerRepository.save(household7);

        Customer business2 = new Business();
        business2.setFullName("XYZ Traders");
        business2.setNic("999999999V");
        business2.setCustomerType(Customer.CustomerType.BUSINESS);
        business2.setContactNo("0779999999");
        business2.setEmail("info@xyz.com");
        business2.setCity("Colombo");
        business2.setDistrict("Colombo");
        business2.setStreet("Fort Road");
        ((Business) business2).setBrNumber("BR789012");
        ((Business) business2).setBusinessName("XYZ Traders Pvt Ltd");
        customerRepository.save(business2);

        Customer business3 = new Business();
        business3.setFullName("Sunshine Restaurant");
        business3.setNic("101010101V");
        business3.setCustomerType(Customer.CustomerType.BUSINESS);
        business3.setContactNo("0771010101");
        business3.setEmail("manager@sunshine.com");
        business3.setCity("Kandy");
        business3.setDistrict("Kandy");
        business3.setStreet("Lake Road");
        ((Business) business3).setBrNumber("BR345678");
        ((Business) business3).setBusinessName("Sunshine Restaurant");
        customerRepository.save(business3);

        // Additional meters (8 more to reach 10 total)
        Meter meter3 = new Meter();
        meter3.setMeterNumber("M003");
        meter3.setStatus(Meter.MeterStatus.ACTIVE);
        meter3.setInstallationDate(LocalDate.of(2025, 2, 1));
        meter3.setConnectionDate(LocalDate.of(2025, 2, 1));
        meter3.setCustomer(household2);
        meter3.setUtilityType(electricity);
        meterRepository.save(meter3);

        Meter meter4 = new Meter();
        meter4.setMeterNumber("M004");
        meter4.setStatus(Meter.MeterStatus.ACTIVE);
        meter4.setInstallationDate(LocalDate.of(2025, 2, 15));
        meter4.setConnectionDate(LocalDate.of(2025, 2, 15));
        meter4.setCustomer(household3);
        meter4.setUtilityType(gas);
        meterRepository.save(meter4);

        Meter meter5 = new Meter();
        meter5.setMeterNumber("M005");
        meter5.setStatus(Meter.MeterStatus.ACTIVE);
        meter5.setInstallationDate(LocalDate.of(2025, 3, 1));
        meter5.setConnectionDate(LocalDate.of(2025, 3, 1));
        meter5.setCustomer(household4);
        meter5.setUtilityType(water);
        meterRepository.save(meter5);

        Meter meter6 = new Meter();
        meter6.setMeterNumber("M006");
        meter6.setStatus(Meter.MeterStatus.ACTIVE);
        meter6.setInstallationDate(LocalDate.of(2025, 3, 10));
        meter6.setConnectionDate(LocalDate.of(2025, 3, 10));
        meter6.setCustomer(household5);
        meter6.setUtilityType(electricity);
        meterRepository.save(meter6);

        Meter meter7 = new Meter();
        meter7.setMeterNumber("M007");
        meter7.setStatus(Meter.MeterStatus.ACTIVE);
        meter7.setInstallationDate(LocalDate.of(2025, 3, 20));
        meter7.setConnectionDate(LocalDate.of(2025, 3, 20));
        meter7.setCustomer(household6);
        meter7.setUtilityType(water);
        meterRepository.save(meter7);

        Meter meter8 = new Meter();
        meter8.setMeterNumber("M008");
        meter8.setStatus(Meter.MeterStatus.ACTIVE);
        meter8.setInstallationDate(LocalDate.of(2025, 4, 1));
        meter8.setConnectionDate(LocalDate.of(2025, 4, 1));
        meter8.setCustomer(household7);
        meter8.setUtilityType(gas);
        meterRepository.save(meter8);

        Meter meter9 = new Meter();
        meter9.setMeterNumber("M009");
        meter9.setStatus(Meter.MeterStatus.ACTIVE);
        meter9.setInstallationDate(LocalDate.of(2025, 4, 5));
        meter9.setConnectionDate(LocalDate.of(2025, 4, 5));
        meter9.setCustomer(business2);
        meter9.setUtilityType(electricity);
        meterRepository.save(meter9);

        Meter meter10 = new Meter();
        meter10.setMeterNumber("M010");
        meter10.setStatus(Meter.MeterStatus.ACTIVE);
        meter10.setInstallationDate(LocalDate.of(2025, 4, 10));
        meter10.setConnectionDate(LocalDate.of(2025, 4, 10));
        meter10.setCustomer(business3);
        meter10.setUtilityType(water);
        meterRepository.save(meter10);

        // Additional meters (5 more to reach 15 total)
        Meter meter11 = new Meter();
        meter11.setMeterNumber("M011");
        meter11.setStatus(Meter.MeterStatus.ACTIVE);
        meter11.setInstallationDate(LocalDate.of(2025, 4, 15));
        meter11.setConnectionDate(LocalDate.of(2025, 4, 15));
        meter11.setCustomer(household);
        meter11.setUtilityType(gas);
        meterRepository.save(meter11);

        Meter meter12 = new Meter();
        meter12.setMeterNumber("M012");
        meter12.setStatus(Meter.MeterStatus.ACTIVE);
        meter12.setInstallationDate(LocalDate.of(2025, 4, 20));
        meter12.setConnectionDate(LocalDate.of(2025, 4, 20));
        meter12.setCustomer(business);
        meter12.setUtilityType(electricity);
        meterRepository.save(meter12);

        Meter meter13 = new Meter();
        meter13.setMeterNumber("M013");
        meter13.setStatus(Meter.MeterStatus.ACTIVE);
        meter13.setInstallationDate(LocalDate.of(2025, 4, 25));
        meter13.setConnectionDate(LocalDate.of(2025, 4, 25));
        meter13.setCustomer(household2);
        meter13.setUtilityType(water);
        meterRepository.save(meter13);

        Meter meter14 = new Meter();
        meter14.setMeterNumber("M014");
        meter14.setStatus(Meter.MeterStatus.ACTIVE);
        meter14.setInstallationDate(LocalDate.of(2025, 5, 1));
        meter14.setConnectionDate(LocalDate.of(2025, 5, 1));
        meter14.setCustomer(household3);
        meter14.setUtilityType(electricity);
        meterRepository.save(meter14);

        Meter meter15 = new Meter();
        meter15.setMeterNumber("M015");
        meter15.setStatus(Meter.MeterStatus.ACTIVE);
        meter15.setInstallationDate(LocalDate.of(2025, 5, 5));
        meter15.setConnectionDate(LocalDate.of(2025, 5, 5));
        meter15.setCustomer(business2);
        meter15.setUtilityType(gas);
        meterRepository.save(meter15);

        // Additional meters (5 more)
        Meter meter16 = new Meter();
        meter16.setMeterNumber("M016");
        meter16.setStatus(Meter.MeterStatus.ACTIVE);
        meter16.setInstallationDate(LocalDate.of(2025, 5, 10));
        meter16.setConnectionDate(LocalDate.of(2025, 5, 10));
        meter16.setCustomer(household4);
        meter16.setUtilityType(electricity);
        meterRepository.save(meter16);

        Meter meter17 = new Meter();
        meter17.setMeterNumber("M017");
        meter17.setStatus(Meter.MeterStatus.ACTIVE);
        meter17.setInstallationDate(LocalDate.of(2025, 5, 15));
        meter17.setConnectionDate(LocalDate.of(2025, 5, 15));
        meter17.setCustomer(household5);
        meter17.setUtilityType(water);
        meterRepository.save(meter17);

        Meter meter18 = new Meter();
        meter18.setMeterNumber("M018");
        meter18.setStatus(Meter.MeterStatus.ACTIVE);
        meter18.setInstallationDate(LocalDate.of(2025, 5, 20));
        meter18.setConnectionDate(LocalDate.of(2025, 5, 20));
        meter18.setCustomer(business3);
        meter18.setUtilityType(gas);
        meterRepository.save(meter18);

        Meter meter19 = new Meter();
        meter19.setMeterNumber("M019");
        meter19.setStatus(Meter.MeterStatus.ACTIVE);
        meter19.setInstallationDate(LocalDate.of(2025, 5, 25));
        meter19.setConnectionDate(LocalDate.of(2025, 5, 25));
        meter19.setCustomer(household6);
        meter19.setUtilityType(electricity);
        meterRepository.save(meter19);

        Meter meter20 = new Meter();
        meter20.setMeterNumber("M020");
        meter20.setStatus(Meter.MeterStatus.ACTIVE);
        meter20.setInstallationDate(LocalDate.of(2025, 6, 1));
        meter20.setConnectionDate(LocalDate.of(2025, 6, 1));
        meter20.setCustomer(household7);
        meter20.setUtilityType(water);
        meterRepository.save(meter20);

        // Create tariffs
        Tariff electricityTariff = new Tariff();
        electricityTariff.setUtilityType(electricity);
        electricityTariff.setEffectiveFrom(LocalDate.of(2025, 1, 1));
        electricityTariff.setMinUnit(0.0);
        electricityTariff.setMaxUnit(100.0);
        electricityTariff.setRatePerUnit(10.0);
        tariffRepository.save(electricityTariff);

        Tariff waterTariff = new Tariff();
        waterTariff.setUtilityType(water);
        waterTariff.setEffectiveFrom(LocalDate.of(2025, 1, 1));
        waterTariff.setMinUnit(0.0);
        waterTariff.setMaxUnit(50.0);
        waterTariff.setRatePerUnit(5.0);
        tariffRepository.save(waterTariff);

        Tariff gasTariff = new Tariff();
        gasTariff.setUtilityType(gas);
        gasTariff.setEffectiveFrom(LocalDate.of(2025, 1, 1));
        gasTariff.setMinUnit(0.0);
        gasTariff.setMaxUnit(200.0);
        gasTariff.setRatePerUnit(8.0);
        tariffRepository.save(gasTariff);

        // Create readings and bills for some meters
        for (int i = 1; i <= 10; i++) {
            Meter meter = meterRepository.findById((long) i).orElse(null);
            if (meter != null) {
                Reading reading = new Reading();
                reading.setMeter(meter);
                reading.setEmployee(meterReader);
                reading.setCurrentReading(100.0 + i * 10);
                reading.setPreviousReading(90.0 + i * 10);
                reading.setReadingDate(LocalDate.now().minusDays(i));
                reading.setReadingMonth(java.time.YearMonth.now().minusMonths(i));
                reading.setUnitsConsumed(10.0);
                readingRepository.save(reading);

                Bill bill = new Bill();
                bill.setMeter(meter);
                bill.setReading(reading);
                bill.setBillDate(LocalDate.now().minusDays(i));
                bill.setDueDate(LocalDate.now().minusDays(i).plusDays(30));
                bill.setStatus(BillStatus.UNPAID);
                billRepository.save(bill);

                // Add payment for some bills
                if (i % 3 == 0) {
                    Payment payment = new Payment();
                    payment.setBill(bill);
                    payment.setCashier(cashier);
                    payment.setAmountPaid(100.0 + i * 5);
                    payment.setPaymentDate(LocalDate.now().minusDays(i - 1));
                    payment.setMethod("Cash");
                    payment.setReceiptNo("RCP" + i);
                    paymentRepository.save(payment);

                    bill.setStatus(BillStatus.PAID);
                    billRepository.save(bill);
                }
            }
        }

        // Create sample reports
        Report report1 = new Report();
        report1.setManager(manager);
        report1.setGeneratedDate(LocalDate.now());
        report1.setReportName("Monthly Usage Report");
        report1.setSummary("Summary of monthly utility usage.");
        reportRepository.save(report1);

        Report report2 = new Report();
        report2.setManager(admin);
        report2.setGeneratedDate(LocalDate.now().minusDays(7));
        report2.setReportName("Payment Collection Report");
        report2.setSummary("Report on payments collected this month.");
        reportRepository.save(report2);
    }
}