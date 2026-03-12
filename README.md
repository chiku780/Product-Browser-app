Product Browser App – Kotlin Multiplatform
Overview
This project is a cross-platform mobile product catalog prototype built using Kotlin Multiplatform (KMP) and Compose Multiplatform. The application allows users to browse products, view detailed information, and search products using an API.
The goal of this project is to demonstrate Clean Architecture, modularization, and shared UI/business logic across Android and iOS while keeping the codebase scalable and maintainable.
The application consumes a remote API to display product data and follows modern Kotlin development practices including StateFlow, Ktor Client, and kotlinx.serialization.

Business Requirements
The application provides the following capabilities:
1. Product List
   Users can view a list of products displaying:
* Product thumbnail
* Product name
* Price
2. Product Details
   Users can tap on a product to view detailed information including:
* Product title
* Description
* Brand
* Price
* Rating
3. Product Search
   Users can search products using keywords. The search feature integrates with an API to return matching products dynamically.

4. Optional Feature (Not Implemented)
   Filter products by category.

Additional Features Implemented
Beyond the required features, the project includes several enhancements:
Koin Dependency Injection
Internet Connectivity Monitoring
The app observes real-time internet connectivity and updates UI accordingly.
Pull-to-Refresh
Users can refresh the product list using swipe-to-refresh.
No Internet Screen
A dedicated UI screen is displayed when the device is offline.
No Data Screen
If the API returns empty results, a "No Data Found" screen is shown.
Cross-Platform Custom Toast
A shared custom toast library was implemented to show messages on both Android and iOS.
UI Animations
Basic UI animations were added to improve the user experience.
Unit Testing
Unit tests were implemented for core business logic (use cases and repositories).

Tech Stack
Kotlin Multiplatform
Used for sharing business logic and UI across Android and iOS.
Compose Multiplatform
Shared UI framework used to build UI components for both platforms.
Ktor Client
Used for performing API requests.
Kotlinx Serialization
Used for JSON parsing and serialization.
StateFlow
Used for managing reactive UI state inside ViewModels.
Clean Architecture
Project is structured using data, domain, and presentation layers.
Dependency Injection
Koin Dependency Injection

Project Architecture
The project follows Clean Architecture with modularization.
root
│
├── core
│   ├── common
│   ├── network
│   └── ui
│
├── feature
│   └── home
│       ├── data
│       ├── domain
│       └── ui
│
└── app

Module Explanation
Core Modules
core/common
Contains shared utilities and helpers such as:
* connectivity observer
* custom toast
* common constants
* shared extensions
  core/network
  Handles networking logic including:
* Ktor client setup
* API service definitions
* request configuration
  core/ui
  Contains reusable UI components used across features.

Feature Module
feature/home
data layer
Responsible for:
* API calls
* DTO models
* Repository implementations
  domain layer
  Contains:
* business models
* repository interfaces
* use cases such as:
  GetProductsUseCase
  SearchProductsUseCase
  ui layer
  Responsible for:
* screens
* ViewModels
* UI state management

Application Flow
UI Layer (Compose Screens)
│
▼
ViewModel
(StateFlow for UI state)
│
▼
Use Case (Domain Layer)
│
▼
Repository Interface
│
▼
Repository Implementation
│
▼
API Service (Ktor Client)


Testing
Basic unit tests were implemented to verify business logic.
Example tested components:
* Use case logic
* Repository behavior
  Testing ensures that domain layer logic works correctly independent of UI or network layers.

How to Build and Run
Clone Repository
git clone <repository-url>
cd product-browser-kmp

Run Android App
Requirements:
* Android Studio (latest version)
* Android SDK
  Steps:
1. Open project in Android Studio.
2. Sync Gradle.
3. Select Android configuration.
4. Run the app on emulator or device.

Run iOS App
Requirements:
* macOS
* Xcode installed
  Steps:
1. Open the iOS project in Xcode.
2. Select an iOS simulator.
3. Run the project.
   The shared Kotlin code will automatically compile and run in the iOS app.

Trade-offs and Assumptions
* Category filtering was not implemented as it was an optional requirement.
* During development several challenges were encountered due to the evolving ecosystem of Kotlin Multiplatform and Compose Multiplatform.
* API Calls on Emulator
* While implementing the network layer using Ktor, API requests were not working on the Android emulator. After extensive debugging, the issue appeared to be emulator-specific. The application worked correctly on a physical device, so further testing was performed on real hardware.
* Image Loading Compatibility
* An attempt was made to use the Kamel image loading library for Compose Multiplatform. However, it was not compatible with the Kotlin and Android Gradle Plugin versions used in this project. An alternative image loading approach was implemented instead.
* Navigation Argument Passing
* Passing arguments between Compose Multiplatform screens did not work reliably across Android and iOS in the current version. To resolve this, a shared object instance was provided through dependency injection (Koin) to pass the selected product data between screens.
* Modular Architecture
* The project was structured using a modular architecture (core and feature modules) to maintain Clean Architecture principles. Although modularization required additional setup time, it improves separation of concerns, scalability, and maintainability.
* Prototype Scope
* This project is intended as a prototype to demonstrate architecture, cross-platform UI, and networking. Advanced features such as category filtering, pagination, and offline caching were not implemented.

Future Improvements
Possible enhancements for production-level applications:
* Add category filtering
* Implement caching with local database
* Add pagination
* Improve UI animations and transitions

Key Learnings
This project demonstrates:
* Kotlin Multiplatform development
* Compose Multiplatform UI sharing
* Clean Architecture implementation
* Modular project structure
* Cross-platform UI development

Author
Biswajit Singh
Android Developer with experience in:
* Kotlin
* Jetpack Compose
* Clean Architecture
* Kotlin Multiplatform
* Compose Multiplatform
