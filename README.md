# Stark Group VMS — Backend (Spring Boot)

Backend REST API service for the **Stark Group Vehicle Management System (VMS)** built with Java 21, Spring Boot 3.3.5, Spring Data JPA, and MySQL.

## Prerequisites

* Java 21 (JDK 21)
* MySQL Server running locally
* Gradle Wrapper (gradlew.bat / gradlew)

## Setup & Environment Configuration

1. Ensure MySQL is running (default port 9001, fallback DB stark_vms_assignment1 on localhost:3306).
2. Set environment variables:
   * PORT (optional, default: 9001)
   * SPRING_DATASOURCE_URL (optional, e.g. jdbc:mysql://localhost:3306/stark_vms_assignment1?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC)
   * SPRING_DATASOURCE_USERNAME (default: root)
   * SPRING_DATASOURCE_PASSWORD (your MySQL password)
   * SPRING_JPA_HIBERNATE_DDL_AUTO (optional, default: update)
3. Run:
   .\gradlew.bat bootRun

## Authentication & Current Limitations

* Hardcoded Login Credentials (Assignment 1 Scope):
  * Admin: admin / admin123
  * User: user / user123
* Token Behavior: Login issues a token, but per-request token validation is not enforced yet.

## Planned Improvements

* Add full JWT token signing/verification and enforce per-request token validation on protected endpoints.
