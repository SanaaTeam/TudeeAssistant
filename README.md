# 🤖 The Cute Tudee App  
### Simple. Elegant. Productive.  

**The Cute Tudee App** is a modern, intuitive task management application built for Android using **Kotlin**, **Jetpack Compose**, **Room**, **Jetpack Navigation**, and **Koin**.  
It’s designed to help users efficiently organize their daily routines while offering a delightful, multilingual experience in both **English** and **Arabic**.  

---

## 📋 Table of Contents  
- [Overview](#-overview)  
- [Features](#-features)  
- [Architecture](#-architecture)  
- [Dependencies](#-dependencies)  
- [Getting Started](#-getting-started)  
- [Design System](#-design-system)  
- [Testing](#-testing)
- [License](#-license)  

---

## 🌟 Overview  
**The Cute Tudee App** provides a lightweight, reliable task management experience with a focus on usability and performance.  
It follows **SOLID principles**, **Clean Architecture**, and **modular design**, ensuring scalability and maintainability.  

### 🧩 Core Highlights  
- Local persistence via **Room Database**  
- Seamless navigation with **Jetpack Navigation 2**  
- **Koin** for dependency injection  
- **ViewModel-based** state management  
- **Bilingual support** (English 🇬🇧 & Arabic 🇪🇬)  
- **Dark / Light mode** compatibility  
- **Onboarding flow** for first-time users  
- Full CRUD task management (Create, Read, Update, Delete)  

---

## 🚀 Features  

### 🪄 Onboarding Experience  
- Displays only on first launch  
- Welcomes users and guides them through setup  

### 📊 Home Screen  
- Presents daily task statistics  
- Visual summary by status: *To Do*, *In Progress*, *Done*  

### ✏️ Task Management  
Create and manage tasks effortlessly with:  
- Title, Description  
- Priority & Category  
- Due Date  

### 🔍 Task Details  
- View detailed task info  
- Update task status through progressive stages  

### 🗓️ Task Filtering  
- View all tasks filtered by a specific date  

### 🗂️ Category Management  
- Includes predefined categories (*Work*, *Personal*, *Study*)  
- Add custom categories with device images  
- Edit or remove categories anytime  

### 🌗 Dark & Light Themes  
- Instantly switch between dark and light modes  
- Theme preference stored via **DataStore**  

### 🌍 Localization  
- Automatically detects system language  
- Supports **English** and **Arabic**  

### 📱 Responsive UI  
- Optimized for all screen sizes and orientations  

---

## 🏗️ Architecture  

The app employs a **clean, modular MVVM structure** for maintainability and testability.

### 🔹 ViewModels  
- Each screen is backed by its own ViewModel  
- Handles business logic and UI state  

### 🔹 Task Services  
- Domain-level abstraction over data sources  
- Maps entities between Room and UI models  

### 🔹 Room Database  
- Manages persistent storage  
- Includes DAOs for data access  

### 🔹 Dependency Injection  
- Implemented via **Koin**  
- Promotes decoupled, testable components  

### 🔹 Design Principles  
- Strict adherence to **SOLID**  
- Clean and minimal architecture  
- Avoids unnecessary layers or complexity  

---

## 🛠️ Dependencies  

| Dependency | Purpose |
|-------------|----------|
| **Room** | Local data storage |
| **Jetpack Navigation 2** | Screen navigation |
| **ViewModel / LiveData** | State management |
| **DataStore** | Theme and settings persistence |
| **Koin** | Dependency injection |
| **Material Design 3** | Modern UI components |
| **Glide** *(optional)* | Image loading for categories |
| **JUnit / Espresso** | Unit and UI testing |

---

## ▶️ Getting Started  

### ✅ Prerequisites  
- **Android Studio** Arctic Fox (4.2) or newer  
- **JDK 11+**  
- **Gradle 7.0+**
  
🧪 Testing

Unit Tests: Validate ViewModel logic and data transformations

UI Tests: Verify user flows using Espresso

Test coverage includes task CRUD operations and navigation

📄 License

This project is licensed under the MIT License – see the LICENSE
 file for details.
 
### ⚙️ Installation  
Clone the repository:  
```bash
git clone https://github.com/SanaaTeam/TudeeAssistant.git






