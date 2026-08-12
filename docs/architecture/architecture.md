# PayFlow Architecture

## Architectural Style

The project follows:

Feature-Based MVVM

Structure:

feature/
data/
core/

---

## UI Layer

Screens are located inside:

feature/<feature-name>/ui

Examples:

feature/dashboard/ui

feature/profile/ui

---

## ViewModel Layer

ViewModels are located inside:

feature/<feature-name>/viewmodel

All ViewModels must inherit:

BaseViewModel

Location:

core/base/BaseViewModel.kt

---

## State Management

All screens must use:

UiState

Location:

core/state/UiState.kt

Available states:

- Loading
- Success
- Error
- Empty

---

## Data Flow

View
↓
ViewModel
↓
Repository
↓
Datasource
↓
Room / Retrofit

---

## Local Storage

Room

Location:

data/local

Structure:

database/
dao/

---

## Remote Communication

Retrofit

Location:

data/remote

Structure:

api/
dto/

---

## Configuration

Application configuration:

core/config/AppConfig.kt

Examples:

- Base URL
- Timeouts

---

## Design System

All visual components must be reused from:

core/components

Developer reference:

Developer Playground

Route:

Developer Playground

---

## Navigation

Navigation is centralized.

Files:

core/navigation/Routes.kt

core/navigation/PayFlowNavGraph.kt

Do not implement parallel navigation flows.

---

## Prohibited Practices

Do not:

- Create alternative navigation systems
- Create custom UI components if an existing component already exists
- Create ViewModels without BaseViewModel
- Create states outside UiState
- Hardcode API URLs
- Duplicate repositories

---

## MVP Features

- Dashboard
- Subscriptions
- Add Subscription
- Simulator
- Profile

---

## Out of Scope

- Dark Theme
- Firebase
- Analytics
- Crashlytics
- Multi-module architecture