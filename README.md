# SMS Manager App

## ✨ Overview
SMS Manager is an Android application developed in **Kotlin** with **Jetpack Compose** that provides an intuitive interface for managing SMS. It features advanced SMS search, seamless sending and receiving functionality, and local persistence using **Room** with **SQLite**.

The app is built following **Clean Architecture** with **MVVM** design pattern, ensuring maintainability, scalability, and adherence practices such as **SOLID principles**.

---

## 💡 Features

- **Send and Receive SMS**: Seamless sending of SMS with status confirmation and dynamic broadcast receivers for receiving messages.
- **Advanced SMS Search**: Utilize **FTS4** for fast and advanced full-text search of sent and received SMS.
- **SMS Listing**: Dedicated sections to display sent and received SMS.
- **Dark and Light Modes**: Fully supports both light and dark themes.
- **Expandable Long Messages**: Truncates long messages with an expandable view for better readability.
- **Reusable and Customizable Components**: Modular UI components to enhance scalability and reusability.
- **Error Handling**: Robust error management for a smoother user experience.

---

## 🤖 Architecture
The app is structured around **Clean Architecture** with the following layers:

### **1. Data Layer**
- Handles SMS sending, receiving, and local storage.
- Includes:
   - **DAO**, **Entity**, and **Database** classes for managing SMS persistence using **Room**.
   - Configuration for **FTS4** to enable advanced search capabilities.

### **2. Domain Layer**
- Defines the core business logic and data models.

### **3. Presentation Layer**
- Contains:
   - The **main screen** for listing sent and received SMS with search functionality.
   - A **dialog** for composing and sending SMS.
   - A **ViewModel** using **StateFlow** to manage UI state and communicate with the business logic.

### **4. DI (Dependency Injection)**
- Utilizes **Dagger Hilt** for dependency injection to ensure a clean and testable architecture.

### **5. Utils**
- Houses utility classes, such as constants and format converters.

---

## ⚙️ Technologies Used

- **Kotlin**: Language used for the entire application.
- **Jetpack Compose**: For building modern, declarative UI.
- **Room**: For local data persistence.
- **SQLite**: Backend database with FTS4 integration.
- **Dagger Hilt**: For dependency injection.
- **Broadcast Receivers**: Dynamic receivers for handling SMS sending and receiving events.
- **StateFlow**: For reactive UI state management.

---

## 🔄 Workflow
1. **Send SMS**: Users can compose and send SMS using a custom dialog. The app confirms delivery status using a dynamic broadcast receiver.
2. **Receive SMS**: Incoming SMS messages are processed via a dynamic broadcast receiver and stored locally.
3. **Search SMS**: Users can perform advanced searches using full-text search (FTS4) for both sent and received messages.
4. **View SMS**: Sent and received SMS are displayed in separate, organized sections.

---

## 🔗 Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/cristian-celis/simple-sms-app.git
   ```
2. Open the project in **Android Studio**.
3. Sync the project to download all dependencies.
4. Run the app on an emulator or physical device.

---

## 🚀 Future Enhancements
- Add scheduling for SMS sending.
- Implement backup and restore functionality for SMS data.
- Enhance filtering and categorization of SMS.

---

## ✍️ Author
Developed by **Cristian Celis**.

Feel free to contribute or provide feedback to enhance this project!

