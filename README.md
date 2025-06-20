# JagadeeshTestFramework

Automation Test Suite for the [OrangeHRM](https://opensource-demo.orangehrmlive.com/) application using **Java**, **TestNG**, **Selenium WebDriver**, and **Log4j2**.

---

## 📌 Overview

This project implements a modular, maintainable automation framework using the TestNG testing framework. It uses the Page Object Model (POM) design pattern and follows industry best practices.

---

## 🛠 Tech Stack

- Java 8  
- TestNG  
- Selenium WebDriver  
- Maven  
- Log4j2  
- POM Design Pattern  
- Eclipse / IntelliJ

---

## 📁 Project Structure

JagadeeshTestFramework/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── base/
│ │ │ ├── pages/
│ │ │ └── utils/
│ │ └── resources/
│ │ └── log4j2.xml
│ ├── test/
│ │ ├── java/
│ │ │ └── tests/
│ │ └── resources/
├── logs/
│ └── test.log
├── target/
│ └── surefire-reports/
├── test-output/
├── testng.xml
├── pom.xml
└── README.md


---

## 🚀 How to Run

### From IDE

- Open the project in IntelliJ or Eclipse
- Right-click `testng.xml` → Run as → TestNG Suite

### From Terminal

```bash
mvn clean test

---------------------------->
📋 Features
Login and Logout tests

Dashboard validations

Log capture using Log4j2

POM structure

Clean browser setup and teardown

📄 Reports & Logs
Logs: logs/test.log

Reports:

test-output/ (TestNG HTML)

target/surefire-reports/ (Console & XML)

✍️ Author
Jagadeesh Bhaskar Parimi
LinkedIn

📜 License
Open-sourced under the MIT License

