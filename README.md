# JagadeeshTestFramework

Automation Test Suite for the [OrangeHRM](https://opensource-demo.orangehrmlive.com/) application using **Java**, **TestNG**, **Selenium WebDriver**, and **Log4j2**.

---

## 📌 Overview

This project implements a modular, maintainable automation framework using the TestNG testing framework. It uses Page Object Model (POM) design patterns for efficient UI interaction and follows industry best practices.

---

## 🛠 Tech Stack

- **Java 8**
- **TestNG**
- **Selenium WebDriver**
- **Maven** (build tool)
- **Log4j2** (for logging)
- **POM Design Pattern**
- **Eclipse/IntelliJ**

---

## 📁 Project Structure

JagadeeshTestFramework/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── base/ # Base classes (e.g., WebDriver setup)
│ │ │ ├── pages/ # Page classes for UI interactions (POM)
│ │ │ └── utils/ # Reusable utility/helper methods
│ │ └── resources/
│ │ └── log4j2.xml # Log4j2 configuration
│
├── src/
│ ├── test/
│ │ ├── java/
│ │ │ └── tests/ # TestNG test classes
│ │ └── resources/ # Test data or config (if any)
│
├── logs/ # Application logs
│ └── test.log
│
├── target/ # Maven compiled classes and reports
│ └── surefire-reports/ # TestNG report output
│
├── test-output/ # TestNG HTML reports
├── testng.xml # TestNG suite config
├── pom.xml # Maven project file
└── README.md # Project readme



---

## 🚀 How to Run

### ✅ Prerequisites

- Java 1.8+
- Maven installed and added to `PATH`
- Chrome or other browser installed
- IDE (Eclipse/IntelliJ)

### 🧪 Run from IDE

1. Open the project in Eclipse or IntelliJ
2. Right-click `testng.xml` → Run as → TestNG Suite

### 🧪 Run from CLI

```bash
mvn clean test

📋 Features Covered
Login and Logout functionality

Dashboard validations

Log capture using Log4j2

Browser launching and teardown via BaseTest

Modular test code using Page Object Model (POM)

📄 Logs & Reports
Logs: Saved in logs/test.log

Test Reports: Generated under:

test-output/ (HTML reports from TestNG)

target/surefire-reports/ (XML and console output)

📎 To Do
Integrate Extent Reports for enhanced test results

Add data-driven testing using Excel or JSON

Include CI/CD integration (e.g., GitHub Actions or Jenkins)

✍️ Author
Jagadeesh Bhaskar Parimi
🔗 LinkedIn ->https://www.linkedin.com/in/jagadeesh-bhaskar-p-352373180/

📜 License
This project is open-source and available under the MIT License.


---

Let me know if you’d like:
- Badges (e.g., build passing, license)
- GitHub Actions CI setup
- ExtentReports integration

Happy testing 🚀
