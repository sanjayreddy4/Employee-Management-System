# 👨‍💼 Employee Management System

The **Employee Management System (EMS)** is a web-based application developed using **Java Spring Boot** that helps organizations efficiently manage employee records, departments, leave requests, and payroll information. The system provides separate dashboards for administrators and employees with secure role-based authentication.

---

## 📌 Project Overview

The Employee Management System (EMS) is a web-based application developed using Java and Spring Boot to streamline and centralize employee-related management activities. It enables administrators to manage employee records, departments, payroll information, and leave requests through a structured and secure application.

Employees can securely access the system to view their profile, submit leave requests, track leave status, and access their payroll information. The application uses Spring Security for authentication and role-based access control, Spring Data JPA for database interaction, and MySQL for persistent data storage.

---

## ✨ Key Features

### Admin
- Secure Login
- Dashboard Overview
- Employee Management (Add, Update, Delete)
- Department Management
- Leave Approval & Rejection
- Payroll Management

### Employee
- Secure Login
- Personal Dashboard
- View Profile
- Apply for Leave
- View Leave Status
- View Payroll Details

---

## ⚙️ How It Works

1. Users log in securely using Spring Security authentication.
2. The system verifies user roles (Admin or Employee).
3. Based on the role, the appropriate dashboard is displayed.
4. Admins can manage employees, departments, payroll, and leave records.
5. Employees can update their profile, request leave, and view salary information.
6. All employee data is stored and retrieved from a MySQL database using Spring Data JPA.

---

## 🛠 Technologies Used

### Backend
- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA

### Frontend
- Thymeleaf
- HTML5
- CSS3
- JavaScript

### Database
- MySQL

### Build Tool
- Maven

### IDE
- Spring Tool Suite (STS) / Eclipse

---

## 🏗 Architecture

```
Frontend (HTML, CSS, Bootstrap, Thymeleaf)
                │
                ▼
Spring MVC Controllers
                │
                ▼
Service Layer (Business Logic)
                │
                ▼
Spring Data JPA
                │
                ▼
MySQL Database
```

---

## 🔒 Security

The application uses **Spring Security** to provide:

- Secure authentication
- Role-based authorization
- Protected URLs
- Session management
- Secure login and logout

---

## 🚀 Why This Project?
This project demonstrates practical web application development using Java and Spring Boot, with a focus on employee management, database integration, security, and role-based access.

It provides hands-on implementation of:

- CRUD Operations
- Authentication & Authorization
- MVC Architecture
- Database Integration
- Layered Architecture
- Role-Based Access Control
- Server-Side Web Application Development
- Responsive User Interface

---
