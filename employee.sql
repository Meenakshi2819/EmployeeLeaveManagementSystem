create database leave_management;
use leave_management;
CREATE TABLE employees (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    department VARCHAR(50)
);
select*from employees;
CREATE TABLE leave_balance (
    balance_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT,
    total_leaves INT DEFAULT 20,
    used_leaves INT DEFAULT 0,
    remaining_leaves INT DEFAULT 20,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
select*from leave_balance;
CREATE TABLE leave_requests (
    leave_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT,
    leave_days INT,
    reason VARCHAR(255),
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
select*from leave_balance;