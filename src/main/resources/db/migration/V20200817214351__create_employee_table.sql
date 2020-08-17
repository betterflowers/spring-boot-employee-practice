drop table if exists employee;

CREATE TABLE EMPLOYEE
(
    EMPLOYEE_ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    AGE INT,
    GENDER VARCHAR(20) DEFAULT NULL,
    COMPANY_ID INT NOT NULL
);