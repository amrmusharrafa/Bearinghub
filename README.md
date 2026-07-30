# BearingHub ⚙️

**BearingHub** is a clean, modern Android application designed for workshop technicians, engineers, and inventory owners to rapidly search, look up technical specifications, and manage stock levels, prices, and shelf locations for industrial ball bearings.

---

## 🌟 Key Features

- **Instant Bearing Lookup:** Quick search by standard bearing designation or part number (e.g., `6200` to `6280`).
- **Owner Stock & Price Manager:**
  - Dedicated owner management bottom sheet to add or update item prices (EGP), quantity in stock, shelf locations, conditions, and dimensions.
  - Direct 1-tap "Owner Edit" button on inventory cards.
  - Top bar "+ Add Bearing Item" action to register new bearing designations directly into the local database.
- **Technical Specifications:**
  - Bore Diameter (ID) in mm
  - Outside Diameter (OD) in mm
  - Width (B) in mm
  - Chamfer dimensions in mm
  - Weight in kg
- **Workshop Inventory Tracking:**
  - Real-time stock status badges (`IN STOCK` / `OUT OF STOCK`)
  - Unit quantities and item condition (e.g., New, Sealed, Refurbished)
  - Unit pricing (EGP)
  - Prominent shelf location callouts (e.g., `A-03-B2`, `B-01-01`)
- **Offline Room Database Persistence:** All updates, added items, prices, and stock levels persist locally in SQLite using Android Jetpack Room.
- **Clean Utility / Minimal Design:** Material Design 3 layout built with high-contrast slate cards, rounded container grids, and clean visual typography.
- **Adaptive Layout:** Optimized for both portrait handheld use and landscape/tablet split-view layouts.

---

## 🛠️ Architecture & Tech Stack

- **Language:** 100% Kotlin
- **UI Framework:** Jetpack Compose + Material Design 3 (M3)
- **Local Persistence:** Room Database + KSP (Kotlin Symbol Processing)
- **Architecture:** MVVM (Model-View-ViewModel) with Unidirectional Data Flow (`StateFlow` & `Flow`)
- **Dependency Management:** Service Locator / Dependency Injection (`AppContainer`)
- **Networking & Data:** Retrofit2 + Moshi + OkHttp
- **Navigation:** Jetpack Navigation Compose

---

## 📊 Catalog Coverage

Pre-loaded with comprehensive technical and stock data for standard 6200-series deep groove ball bearings:

| Bearing No. | Bore ID (mm) | Outside OD (mm) | Width B (mm) | Chamfer (mm) | Weight (kg) |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **6200** | 10 | 30 | 9 | 0.6 | 0.032 |
| **6201** | 12 | 32 | 10 | 0.6 | 0.037 |
| **6204** | 20 | 47 | 14 | 1.0 | 0.106 |
| **6209** | 45 | 85 | 19 | 1.1 | 0.407 |
| **6220** | 100 | 180 | 34 | 2.1 | 3.47 |
| **6280** | 400 | 720 | 100 | 3.0 | 157.0 |
*(Includes full range from 6200 to 6280, customizable by the owner)*

---

## 📱 How to Build & Run

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 24+ (Android 7.0 Nougat minimum)

### Building from Source
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/bearing-hub.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle project files.
4. Run on an Android device or emulator (`Shift + F10`).

---

## 📄 License

This project is licensed under the MIT License - see the `LICENSE` file for details.
