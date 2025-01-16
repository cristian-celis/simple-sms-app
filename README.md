# Simple SMS App

## Project Description
A minimalistic SMS application that allows users to load, send, and receive text messages. The project is implemented in Kotlin, following Clean Architecture and the MVVM design pattern.

---

## Features
- Load SMS from the device.
- Send SMS to a specific number.
- Receive and display incoming SMS in real-time.

---

## Requirements
- Android Studio (latest version recommended).
- Kotlin 1.8 or above.
- Minimum Android SDK 23 (Marshmallow).

---

## Project Structure
This project is organized into three main layers:

1. **Domain Layer:** Contains the business logic and use cases.
2. **Data Layer:** Manages data sources (e.g., SMS APIs, local storage).
3. **Presentation Layer:** Handles the UI with Jetpack Compose and ViewModels.

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/simple-sms-app.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle and build the project.
4. Run the app on an emulator or a physical device.
