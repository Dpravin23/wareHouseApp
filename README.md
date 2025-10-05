# 🏬 Warehouse Management System

A Spring Boot-based Warehouse Management System (WMS) that enables efficient inventory control, product tracking, and CRUD operations for warehouse entities.

---

## 📦 Features

- ✅ Add, view, update, and delete warehouse items
- 📋 Manage product catalog 
- 🚚 Track Part ,GRN and User from employeee and Admin seprately.
- 🧾 Maintain supplier and customer records
- 🔐 Secure access with Spring Security 

---

## 🧰 Tech Stack

| Layer        | Technology            |
|--------------|------------------------|
| Backend      | Spring Boot, Spring Data JPA |
| Database     | MySQL / PostgreSQL     |
| Frontend     | Thymeleaf (optional) |
| Security     | Spring Security (optional) |
| Build Tool   | Maven  |

---

## 🗃️ Entity Overview

### Part
- `id`: Long
- `name`: String
- `height`: String
-  `Length`: String
-  `widht`: String
- `quantity`: Integer
- `location`: String

### Supplier
- `id`: Long
- `name`: String
- `contact`: String
- `email`: String

### Shipment
- `id`: Long
- `type`: enable /disable
- `shipments`: List<shipmenttype>

---

