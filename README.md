# ToDoList
A clean-architecture ToDo list app built with **Kotlin**, **Jetpack Compose**, **Hilt**, and **Room Database**.  
This project demonstrates MVVM, state management with Kotlin Flows, navigation with Navigation Compose, and error handling via Snackbar.

---

## ✨ Features

- 📋 View a list of ToDo items  
- 🔍 Search ToDo's with **2s debounce**  
- ➕ Add new ToDo's with a dedicated screen  
- ⚠️ Error handling (typing `"Error"` shows Snackbar "Failed to add TODO")  
- ⏳ Loading indicator while adding a new TODO (3s simulated delay)  
- 🔄 Preserves state across screen rotations 
 

## 🛠 Tech Stack

- **Kotlin**  
- **Jetpack Compose**  
- **Hilt** for dependency injection  
- **Room Database**  
- **Coroutines + Flow**  
- **Navigation Compose**  
- **Material3 Components** 

## 🧪 Testing

This project includes unit tests for both the `MainToDoViewModel` and `AddToDoViewModel`.