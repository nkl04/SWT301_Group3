CREATE DATABASE SwellStore_DB; -- Sử dụng cơ sở dữ liệu mới tạo 
USE SwellStore_DB;

-- Tạo bảng customer
CREATE TABLE customer (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    customer_type NVARCHAR(20) CHECK (customer_type IN ('REGULAR', 'NON_REGULAR')) NOT NULL
);

-- Tạo bảng employee
CREATE TABLE employee (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    employee_type NVARCHAR(20) CHECK (employee_type IN ('NON_SALARIED', 'SALARIED')) NOT NULL
);

-- Tạo bảng item
CREATE TABLE item (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255) NOT NULL,
    item_type NVARCHAR(20) CHECK (item_type IN ('STANDARD', 'BONUS', 'OTHER')) NOT NULL,
    price FLOAT NOT NULL
);



-- Thêm dữ liệu vào bảng customer
INSERT INTO customer (name, customer_type)
VALUES
    ('Trang', 'REGULAR'),
    ('Dung', 'NON_REGULAR');

-- Thêm dữ liệu vào bảng employee  
INSERT INTO employee (name, employee_type)
VALUES
    ('Van', 'NON_SALARIED'),
    ('Phu', 'SALARIED');

-- Thêm dữ liệu vào bảng item
INSERT INTO item (name, item_type, price)
VALUES
    ('Other Item', 'OTHER', 10000),
    ('Luxury Other Item', 'OTHER', 10001),
    ('Bonus Item', 'BONUS', 1001),
    ('Luxury Bonus Item', 'BONUS', 1000),
    ('Standard Item', 'STANDARD', 1850);
