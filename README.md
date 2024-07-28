# Rick and Morty Character Viewer

This is an Android application that displays a list of characters from the Rick and Morty series in a RecyclerView using the Rick and Morty API and Jetpack libraries. The app follows the MVVM (Model-View-ViewModel) architecture for better separation of concerns and maintainability.

## Demo Video
[Link to demo video](https://drive.google.com/file/d/1E6U7BfPQTbf3xAUTSse2WNDFkdUYbWFB/view?usp=sharing)

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Libraries Used](#libraries-used)
- [Installation](#installation)
- [Usage](#usage)

## Features
- **Character List**: Displays a list of characters with their names and images.
- **Character Details**: View detailed information about each character, including status, species, gender, origin, and location.
- **Search Functionality**: Allows users to search for specific characters by name.
- **Splash Screen**: Animated splash screen with scale-in animation.
- **Offline Caching**: Caches API responses to provide offline support.

## Architecture
The app is structured following the MVVM architecture pattern for better organization and maintainability:
- **Model**: Defines data structures and handles data operations.
- **View**: Responsible for displaying data and UI components (Activities, Fragments, Adapters).
- **ViewModel**: Manages UI-related data and handles communication between the View and the Model.
- **Repository**: Provides a clean API for data access to the rest of the application, managing data operations and network/database interactions.
- **Network**: Contains API service interfaces and network-related classes, such as Retrofit instances.

## Libraries Used
- **Retrofit**: A type-safe HTTP client for Android and Java. Used for making network requests to the Rick and Morty API.
- **Glide**: An image loading and caching library for Android focused on smooth scrolling. Used to load character images.
- **Gson**: A library to convert Java Objects into JSON and back. Used in conjunction with Retrofit for JSON parsing.
- **Lifecycle**: AndroidX Lifecycle-aware components to perform actions in response to a change in the lifecycle status of another component.
- **RecyclerView**: A flexible view for providing a limited window into a large data set.
- **OkHttp**: An HTTP client for Android and Java applications. Used for implementing API response caching.
- **SafeArgs**: Safe Args for navigating and passing data between destinations.
- **View Binding**: Allows us to easily write code that interacts with views.


## Installation
1. Clone the repository: git clone https://github.com/Himanshu6124/rick-and-morty-ridecell-assignment
2. Open the project in Android Studio.
3. Checkout the `main` branch.
4. Sync the project with Gradle files.

## Usage
1. Run the application on an Android emulator or physical device.
2. The splash screen will appear with an animation.
3. The main screen displays a search bar and list of Rick and Morty characters.
4. Use the pagination to view previous and next pages.
5. Tap on a character to view detailed information.
6. Use the search bar to find specific characters.


