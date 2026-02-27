# Automoyka Go

A mobile app for self-service car wash (portfolio project). Choose a car wash address, bay (module), services, and see the total. Supports registration, login, settings, virtual card balance, card linking, dark theme, reviews, and admin panel with SQL database.

---

## Table of Contents

- [Features](#features)
- [Screens and Navigation](#screens-and-navigation)
- [Versions](#versions)
- [Requirements and Build](#requirements-and-build)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Troubleshooting](#troubleshooting)

---

## Features

### Account
- **Registration** — name and password (at least 4 characters).
- **Login** — sign in again after logout with the same credentials.
- **Logout** — in settings; after logout you can choose "Log in" or "Register".

### Car Wash and Order
- **Welcome screen** — greeting by name, settings (gear icon on the right), reviews (star icon), list of car wash addresses in Rostov-on-Don.
- **Address selection** — tap a card to open the order screen for that car wash.
- **Bay (module) selection** — 4 bays with status "Available" / "Occupied".
- **Services** — quick wash (150 ₽), standard (250 ₽), premium (400 ₽), underbody wash (100 ₽), wax (200 ₽). Multiple selection allowed.
- **Total** — automatic sum of selected services.
- **Start** — checks bay and at least one service; payment from virtual balance; shows summary (address, bay, services, amount).

### Virtual Card and Payment
- **Balance** — virtual balance in settings; top up via the "Top up" button (by card or demo).
- **Payment** — when you tap "Start", the amount is deducted from the balance; insufficient balance shows a message to top up in settings.

### Settings
- **Dark theme** — switch in settings; applied immediately.
- **Bank card** — link/unlink card (number, expiry, CVV); only last 4 digits stored (no real payment).
- **Log out** — clears session; you can log in or register again.

### Interface
- **Gear icon** (top right) on welcome and order screens — opens settings.
- **Star icon** (next to gear) — opens reviews.
- On the order screen — back arrow and title with the selected car wash address.

---

## Screens and Navigation

1. **WelcomeActivity** (after login) — greeting, reviews icon, settings icon, list of addresses.
2. **EntryActivity** — choice "Log in" or "Register" (first launch and after logout).
3. **RegisterActivity** — registration (name, password).
4. **LoginActivity** — login (name, password).
5. **MainActivity** — order: bay, services, total, "Start" button (pay from balance).
6. **SettingsActivity** — balance & top-up, dark theme, bank card, logout.
7. **TopUpActivity** — top up virtual balance (amount, by card / demo).
8. **LinkCardActivity** — link card (number, expiry, CVV).
9. **ReviewsActivity** — list of reviews; button to add a review.
10. **AddReviewActivity** — new review (rating 1–5, text).
11. **AdminLoginActivity** / **AdminActivity** — admin login (admin/admin), users list, orders (accounting), total revenue.

Car wash addresses — fixed list of 12 addresses in Rostov-on-Don.

---

## Versions

### v1.0.0 — Basic
- Single screen: bay selection (4 bays), services (chips with prices), total, "Start" button.
- Bay status: available / occupied (demo: bay 2 occupied).
- Material Components, View Binding, CardView.

### v1.1.0 — Registration
- Registration screen on first launch (name, password).
- After registration — main screen.
- Data stored in SharedPreferences (Prefs).

### v1.2.0 — Addresses and Settings
- Car wash address: spinner with Rostov-on-Don addresses.
- Settings screen: dark theme, bank card link, logout.
- Card link (UI): number, expiry, CVV; only last 4 digits stored.

### v1.3.0 — Dark Theme
- Dark theme: `values-night/colors.xml`, `values-night/themes.xml`.
- Switch in settings; theme saved and applied on next launch (App + Prefs).

### v1.4.0 — Screen Restructure
- Welcome screen after login: greeting, settings icon, list of car wash addresses.
- Tap address card → order screen (bay, services, price) for that address.
- Address spinner removed from order screen; address passed via Intent in title.

### v1.5.0 — Login and Logout
- "Log out" button in settings.
- After logout — choice screen: "Log in" or "Register".
- Login screen: name and password, check against stored data; on success → welcome screen.
- On logout, name and password kept for re-login.

### v1.6.0 — Settings Icon and Reviews
- Gear icon (top right) on welcome and order screens.
- Star icon for reviews; Reviews screen and add review.
- Back arrow and address in order screen title.
- Drawables: `ic_settings_gear.xml`, `ic_arrow_back.xml`, `ic_reviews.xml`.

### v1.7.0 — Database, Admin, Virtual Card
- **SQL database** (SQLite): raw SQL via `DatabaseHelper` and `AppDb`; tables: users, orders, reviews. Schema in `database/schema.sql`.
- **Admin panel** — entry "Admin" on login screen; login admin/admin; users list, orders (accounting), total revenue.
- **Virtual card** — balance in settings; top-up screen (by card / demo); payment from balance when tapping "Start".
- **Reviews** stored in DB; list and add review with rating.

---

## Requirements and Build

### Requirements
- **Android Studio** Hedgehog (2023.1.1) or newer  
- **JDK** 17  
- **minSdk** 24, **targetSdk** 34  

### Build and Run
1. Open the project in Android Studio: **File → Open** → select the `AutomoykaGo` folder.
2. Wait for **Sync Project with Gradle Files** (or click "Sync" in the toolbar).
3. Connect a device or start an emulator.
4. Click **Run** (green triangle).

On first open, Gradle may ask to download — accept. If you see a Gradle Wrapper error: **Tools → Gradle → Create Gradle Wrapper**, then **Sync** again.

---

## Tech Stack

- **Language:** Kotlin  
- **UI:** View Binding, Material Components (Chip, Button, TextInputLayout, Toolbar, CardView, SwitchMaterial, RecyclerView)  
- **Storage:** SharedPreferences (Prefs), SQLite via raw SQL (`DatabaseHelper`, `AppDb`)  
- **Themes:** Material DayNight, custom `values` and `values-night`  
- **Build:** Gradle (Kotlin DSL), AGP 8.13.2, Gradle 8.13  

---

## Project Structure

```
app/src/main/
├── java/com/portfolio/automoykago/
│   ├── App.kt
│   ├── Prefs.kt
│   ├── WelcomeActivity.kt
│   ├── EntryActivity.kt
│   ├── RegisterActivity.kt
│   ├── LoginActivity.kt
│   ├── MainActivity.kt
│   ├── SettingsActivity.kt
│   ├── TopUpActivity.kt
│   ├── LinkCardActivity.kt
│   ├── ReviewsActivity.kt
│   ├── AddReviewActivity.kt
│   ├── AdminLoginActivity.kt
│   ├── AdminActivity.kt
│   └── db/
│       ├── DatabaseHelper.kt    # SQLiteOpenHelper, raw SQL schema
│       ├── AppDb.kt             # Raw SQL access (users, orders, reviews)
│       ├── User.kt
│       ├── Order.kt
│       └── Review.kt
├── res/
│   ├── layout/
│   │   ├── activity_welcome.xml
│   │   ├── activity_entry.xml
│   │   ├── activity_register.xml
│   │   ├── activity_login.xml
│   │   ├── activity_main.xml
│   │   ├── activity_settings.xml
│   │   ├── activity_top_up.xml
│   │   ├── activity_link_card.xml
│   │   ├── activity_reviews.xml
│   │   ├── activity_add_review.xml
│   │   ├── activity_admin.xml
│   │   ├── activity_admin_login.xml
│   │   ├── item_address.xml
│   │   ├── item_review.xml
│   │   ├── item_admin_user.xml
│   │   └── item_admin_order.xml
│   ├── menu/menu_main.xml
│   ├── values/strings.xml, colors.xml, themes.xml
│   ├── values-night/colors.xml, themes.xml
│   └── drawable/ic_launcher.xml, ic_settings_gear.xml, ic_arrow_back.xml, ic_reviews.xml
└── AndroidManifest.xml

database/
├── schema.sql    # SQLite table definitions
├── queries.sql   # Example queries
└── indexes.sql   # Optional indexes
```

---

## Troubleshooting

- **"Your project path contains non-ASCII characters"** — move the project to a path without Cyrillic (e.g. `C:\Users\...\AutomoykaGo`) and open it again.
- **"Incompatible Gradle JVM"** — in **File → Settings → Build, Execution, Deployment → Build Tools → Gradle** select JDK 17 (or 11).
- Other sync errors — update Android Gradle Plugin and Gradle to the versions suggested by the IDE.
