-- SQL Server Setup Script for Utility Management System
-- Run this script in SQL Server Management Studio or Azure Data Studio

-- Create database
CREATE DATABASE utility_management;
GO

-- Use the database
USE utility_management;
GO

-- Create login if needed (optional - adjust as per your security requirements)
-- CREATE LOGIN utility_user WITH PASSWORD = 'pass123';
-- GO

-- Create user and grant permissions (optional)
-- CREATE USER utility_user FOR LOGIN utility_user;
-- GO
-- ALTER ROLE db_owner ADD MEMBER utility_user;
-- GO

-- =====================================================================================
-- TABLE CREATION SCRIPT (Optional - Hibernate will create these automatically)
-- =====================================================================================

-- Note: These tables will be created automatically by the Spring Boot application
-- when you run it with spring.jpa.hibernate.ddl-auto=create-drop
-- You can run this script manually if you prefer to create tables yourself

-- Customer base table
CREATE TABLE customer (
    customer_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    city NVARCHAR(255),
    contact_no NVARCHAR(255),
    customer_type NVARCHAR(50) NOT NULL CHECK (customer_type IN ('HOUSEHOLD', 'BUSINESS', 'GOVERNMENT')),
    district NVARCHAR(255),
    dob DATE,
    email NVARCHAR(255),
    full_name NVARCHAR(255) NOT NULL,
    gender NVARCHAR(50) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    nic NVARCHAR(255) UNIQUE,
    street NVARCHAR(255)
);

-- Employee base table
CREATE TABLE employee (
    employee_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    contact_no NVARCHAR(255),
    date_joined DATE NOT NULL,
    full_name NVARCHAR(255) NOT NULL,
    nic NVARCHAR(255) UNIQUE,
    password NVARCHAR(255) NOT NULL,
    role NVARCHAR(50) NOT NULL CHECK (role IN ('ADMIN', 'METER_READER', 'CASHIER', 'MANAGER')),
    username NVARCHAR(255) NOT NULL UNIQUE
);

-- Utility Type base table
CREATE TABLE utility_type (
    utility_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    utility_name NVARCHAR(255) NOT NULL
);

-- Meter table
CREATE TABLE meter (
    meter_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    connection_date DATE,
    installation_date DATE,
    meter_number NVARCHAR(255) NOT NULL UNIQUE,
    status NVARCHAR(50) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'MAINTENANCE', 'DISCONNECTED')),
    customer_id BIGINT NOT NULL,
    utility_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (utility_id) REFERENCES utility_type(utility_id)
);

-- Reading table
CREATE TABLE reading (
    reading_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    current_reading FLOAT NOT NULL,
    previous_reading FLOAT,
    reading_date DATE NOT NULL,
    reading_month VARBINARY(255) NOT NULL,
    units_consumed FLOAT,
    employee_id BIGINT NOT NULL,
    meter_id BIGINT NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
    FOREIGN KEY (meter_id) REFERENCES meter(meter_id)
);

-- Bill table
CREATE TABLE bill (
    bill_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    bill_date DATE,
    due_date DATE,
    status NVARCHAR(50) CHECK (status IN ('PAID', 'UNPAID', 'OVERDUE')),
    meter_id BIGINT,
    reading_id BIGINT UNIQUE,
    FOREIGN KEY (meter_id) REFERENCES meter(meter_id),
    FOREIGN KEY (reading_id) REFERENCES reading(reading_id)
);

-- Payment table
CREATE TABLE payment (
    payment_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    amount_paid FLOAT,
    payment_date DATE,
    method NVARCHAR(255),
    receipt_no NVARCHAR(255),
    bill_id BIGINT,
    employee_id BIGINT,
    FOREIGN KEY (bill_id) REFERENCES bill(bill_id),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

-- Tariff table
CREATE TABLE tariff (
    tariff_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    effective_from DATE,
    max_unit FLOAT,
    min_unit FLOAT,
    rate_per_unit FLOAT,
    utility_type_id BIGINT,
    FOREIGN KEY (utility_type_id) REFERENCES utility_type(utility_id)
);

-- Report table
CREATE TABLE report (
    report_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    generated_date DATE,
    report_name NVARCHAR(255),
    summary NVARCHAR(MAX),
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

-- Inheritance tables (subclass tables)
CREATE TABLE household (
    customer_id BIGINT PRIMARY KEY,
    family_size INT,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE business (
    customer_id BIGINT PRIMARY KEY,
    br_number NVARCHAR(255) UNIQUE,
    business_name NVARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE government_office (
    customer_id BIGINT PRIMARY KEY,
    office_code NVARCHAR(255) UNIQUE,
    office_name NVARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE admin (
    employee_id BIGINT PRIMARY KEY,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE meter_reader (
    employee_id BIGINT PRIMARY KEY,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE cashier (
    employee_id BIGINT PRIMARY KEY,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE manager (
    employee_id BIGINT PRIMARY KEY,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE electricity (
    utility_id BIGINT PRIMARY KEY,
    unit_type NVARCHAR(255),
    FOREIGN KEY (utility_id) REFERENCES utility_type(utility_id)
);

CREATE TABLE water (
    utility_id BIGINT PRIMARY KEY,
    unit_type NVARCHAR(255),
    FOREIGN KEY (utility_id) REFERENCES utility_type(utility_id)
);

CREATE TABLE gas (
    utility_id BIGINT PRIMARY KEY,
    unit_type NVARCHAR(255),
    FOREIGN KEY (utility_id) REFERENCES utility_type(utility_id)
);

-- =====================================================================================
-- SAMPLE DATA INSERTION (Optional)
-- =====================================================================================

-- Insert sample utility types
INSERT INTO utility_type (utility_name) VALUES ('Electricity');
INSERT INTO utility_type (utility_name) VALUES ('Water');
INSERT INTO utility_type (utility_name) VALUES ('Gas');

-- Insert sample employees
INSERT INTO employee (full_name, nic, role, username, password, contact_no, date_joined)
VALUES ('Admin User', '123456789V', 'ADMIN', 'admin', 'admin', '0771234567', '2025-01-01');

INSERT INTO employee (full_name, nic, role, username, password, contact_no, date_joined)
VALUES ('Meter Reader', '987654321V', 'METER_READER', 'meterreader', 'reader', '0772345678', '2025-01-01');

INSERT INTO employee (full_name, nic, role, username, password, contact_no, date_joined)
VALUES ('Cashier User', '456789123V', 'CASHIER', 'cashier', 'cash', '0773456789', '2025-01-01');

INSERT INTO employee (full_name, nic, role, username, password, contact_no, date_joined)
VALUES ('Manager User', '789123456V', 'MANAGER', 'manager', 'mgr', '0774567890', '2025-01-01');

-- Insert admin, meter_reader, cashier, manager records
INSERT INTO admin (employee_id) VALUES (1);
INSERT INTO meter_reader (employee_id) VALUES (2);
INSERT INTO cashier (employee_id) VALUES (3);
INSERT INTO manager (employee_id) VALUES (4);

-- Insert electricity, water, gas records
INSERT INTO electricity (utility_id, unit_type) VALUES (1, 'kWh');
INSERT INTO water (utility_id, unit_type) VALUES (2, 'm³');
INSERT INTO gas (utility_id, unit_type) VALUES (3, 'units');

GO

-- =====================================================================================
-- TRIGGERS
-- =====================================================================================

-- Trigger 1: Auto-update bill status when payment is added
CREATE TRIGGER trg_UpdateBillStatusOnPayment
ON payment
AFTER INSERT
AS
BEGIN
    UPDATE bill
    SET status = 'PAID'
    WHERE bill_id IN (
        SELECT DISTINCT i.bill_id
        FROM inserted i
        WHERE i.bill_id = bill.bill_id
        AND bill.amount <= (
            SELECT SUM(p.amount)
            FROM payment p
            WHERE p.bill_id = bill.bill_id
        )
    );
END;
GO

-- Trigger 2: Auto-update meter status when reading is added
CREATE TRIGGER trg_UpdateMeterStatusOnReading
ON reading
AFTER INSERT
AS
BEGIN
    UPDATE meter
    SET status = 'ACTIVE'
    WHERE meter_id IN (
        SELECT DISTINCT i.meter_id
        FROM inserted i
        WHERE i.meter_id = meter.meter_id
    );
END;
GO

-- =====================================================================================
-- USER DEFINED FUNCTIONS
-- =====================================================================================

-- UDF 1: Calculate monthly bill amount
CREATE FUNCTION fn_CalculateBillAmount (
    @usage DECIMAL(10,2),
    @tariff_rate DECIMAL(10,2)
)
RETURNS DECIMAL(10,2)
AS
BEGIN
    RETURN @usage * @tariff_rate;
END;
GO

-- UDF 2: Calculate late payment fee
CREATE FUNCTION fn_CalculateLateFee (
    @due_date DATE,
    @payment_date DATE,
    @bill_amount DECIMAL(10,2),
    @late_fee_rate DECIMAL(5,2) = 0.02 -- 2% per day
)
RETURNS DECIMAL(10,2)
AS
BEGIN
    IF @payment_date > @due_date
        RETURN DATEDIFF(DAY, @due_date, @payment_date) * @bill_amount * @late_fee_rate;
    RETURN 0;
END;
GO

-- =====================================================================================
-- VIEWS
-- =====================================================================================

-- View 1: Summary of unpaid bills
CREATE VIEW vw_UnpaidBillsSummary AS
SELECT
    c.full_name AS customer_name,
    c.contact_no,
    b.bill_id,
    b.amount,
    b.due_date,
    b.status,
    DATEDIFF(DAY, b.due_date, GETDATE()) AS days_overdue
FROM bill b
JOIN meter m ON b.meter_id = m.meter_id
JOIN customer c ON m.customer_id = c.customer_id
WHERE b.status != 'PAID';
GO

-- View 2: Monthly revenue report
CREATE VIEW vw_MonthlyRevenueReport AS
SELECT
    YEAR(p.payment_date) AS year,
    MONTH(p.payment_date) AS month,
    SUM(p.amount) AS total_revenue,
    COUNT(p.payment_id) AS payment_count
FROM payment p
GROUP BY YEAR(p.payment_date), MONTH(p.payment_date);
GO

-- =====================================================================================
-- STORED PROCEDURES
-- =====================================================================================

-- SP 1: Generate bill for a customer
CREATE PROCEDURE sp_GenerateBillForCustomer
    @customer_id BIGINT,
    @meter_id BIGINT,
    @usage DECIMAL(10,2),
    @tariff_rate DECIMAL(10,2)
AS
BEGIN
    DECLARE @bill_amount DECIMAL(10,2);
    SET @bill_amount = dbo.fn_CalculateBillAmount(@usage, @tariff_rate);

    INSERT INTO bill (meter_id, amount, due_date, status, bill_date)
    VALUES (@meter_id, @bill_amount, DATEADD(DAY, 30, GETDATE()), 'UNPAID', GETDATE());
END;
GO

-- SP 2: List defaulters (customers with overdue bills)
CREATE PROCEDURE sp_ListDefaulters
AS
BEGIN
    SELECT
        c.customer_id,
        c.full_name,
        c.contact_no,
        COUNT(b.bill_id) AS overdue_bills_count,
        SUM(b.amount) AS total_overdue_amount,
        MAX(DATEDIFF(DAY, b.due_date, GETDATE())) AS max_days_overdue
    FROM customer c
    JOIN meter m ON c.customer_id = m.customer_id
    JOIN bill b ON m.meter_id = b.meter_id
    WHERE b.status != 'PAID' AND b.due_date < GETDATE()
    GROUP BY c.customer_id, c.full_name, c.contact_no
    ORDER BY total_overdue_amount DESC;
END;
GO

PRINT 'Database setup completed successfully!';
GO