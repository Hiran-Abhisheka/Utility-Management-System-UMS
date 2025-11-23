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

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadSampleData();
        System.out.println("Sample data loaded successfully!");
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
    }
}