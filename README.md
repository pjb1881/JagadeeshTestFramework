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


GIT COMMANDS CHEAT SHEET

1. Initialize a Local Git Repo
   git init

2. Add a Remote Repository (GitHub)
   git remote add origin https://github.com/username/repo-name.git

3. Check Repo Status
   git status

4. Stage Files
   git add .          # Add all files
   git add      # Add a specific file

5. Commit Changes
   git commit -m "Your commit message"

6. Create a New Branch
   git branch 

7. Switch to a Branch
   git checkout 
   git checkout -b    # Create and switch

8. Push Code to GitHub
   git push -u origin 
   git push                        # After the first time

9. Pull Code from Remote
   git pull origin 

10. List Branches
    git branch        # Local branches
    git branch -r     # Remote branches
    git branch -a     # All branches

11. Merge Branches
    git checkout main
    git merge 

12. Delete a Branch
    git branch -d         # Local
    git push origin --delete   # Remote

13. Clone a Repository
    git clone https://github.com/username/repo-name.git

14. Set Upstream Branch
    git push -u origin 

15. Remove Tracked File but Keep Locally
    git rm --cached 

16. Set Global Git Identity
    git config --global user.name "Your Name"
    git config --global user.email "you@example.com"

