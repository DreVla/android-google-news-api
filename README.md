# Android News App

A modern Android application that displays top headlines using the News API. The app features offline support for saved articles, efficient pagination, and a clean, responsive UI built with Jetpack Compose.

https://github.com/user-attachments/assets/108fb90f-2d06-4273-8bd9-d9975d975cf2
## Setup

1. Clone the repository
2. Add your News API key to `local.properties`:
```properties
NEWS_API_KEY = "YOUR_API_KEY"
```
3. Build and run the project

## Features
- Display top news headlines
- Offline support with local caching
- Save articles for later reading
- Responsive UI with adaptive grid layout
- Network connectivity monitoring
- Efficient pagination for smooth scrolling

## Tech Stack

### Core Technologies
- Kotlin
- Jetpack Compose for UI
- Android Architecture Components
- Kotlin Coroutines & Flow for async operations
- Why? I believe Compose is a way more modern and intuitive way to build modern apps.

### Networking & Data
- Retrofit for HTTP requests
- GSON for JSON parsing
- Coil for image loading. Why? Built with Kotlin in mind, modern, lightweight, active.
- Protocol Buffers for data serialization
- DataStore for persistent storage. Why? Just overall better. Supports async operations.
- Room Database for local caching. Why? Easier, more flexible, also used it before.

### UI/UX
- Material 3 Design
- Jetpack Navigation Compose
- Adaptive grid layout for different screen sizes

### Testing
- JUnit for unit testing
- MockK for mocking. Why not Mockito? Mockk Built with kotlin in mind, supports kotlin features, also used it before
- Kotlin Coroutines Test utilities

## Architecture & Design Patterns

### Clean Architecture
- Clear separation of concerns
- Domain, Data, and Presentation layers
- Unidirectional data flow

### Design Patterns
1. **MVVM (Model-View-ViewModel)**
   - Separation of UI and business logic
   - State management through ViewModel
   - UI state observation through Flow

2. **Repository Pattern**
   - Single source of truth
   - Abstracts data sources
   - Handles data caching and offline support

3. **Factory Pattern**
   - ViewModelFactory for dependency injection
   - Clean object creation

4. **Observer Pattern**
   - Kotlin Flow for reactive programming
   - Network state observation
   - UI state updates

5. **Data Access Object (DAO)**
   - Room database operations
   - Clean database access layer

6. **Adapter Pattern**
   - Data mapping between layers
   - DTO to Domain model conversion
   - Entity to Domain model conversion

7. **Strategy Pattern**
   - Dynamic data source selection
   - Online/Offline data loading strategies

8. **Singleton Pattern**
   - RetrofitClient instance
   - Database instance

## Improvements

- Hilt for dependency injection
- Improve unit test coverage (Save feature)
- Pagination caching
- UI tests
- Pull to refresh
- Skeleton/Shimmering UI for loading
- Share article
- Search feature
