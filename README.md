# Автомойка Go

Приложение для автомойки самообслуживания (портфолио-проект).

## Возможности

- **Выбор поста** — 4 поста, отображение статуса (свободен / занят)
- **Услуги** — быстрая мойка, стандартная, премиум, мойка поддона, воск (с ценами)
- **Итого** — автоматический расчёт суммы по выбранным услугам
- **Начать** — проверка выбора поста и услуг, вывод итога

## Как открыть в Android Studio

1. Запустите **Android Studio**.
2. **File → Open** и выберите папку проекта `Автомойка Go`.
3. Дождитесь **Sync Project with Gradle Files** (или нажмите «Sync» в панели).
4. Подключите устройство или запустите эмулятор, нажмите **Run** (зелёный треугольник).

## Требования

- Android Studio Hedgehog (2023.1.1) или новее
- JDK 17
- minSdk 24, targetSdk 34

## Стек

- Kotlin, View Binding
- Material Components (Chip, Button, темы)
- CardView, ConstraintLayout

## Структура

```
app/src/main/
├── java/com/portfolio/automoykago/
│   └── MainActivity.kt
├── res/
│   ├── layout/activity_main.xml
│   ├── values/colors.xml, strings.xml, themes.xml
│   └── drawable/ic_launcher.xml
└── AndroidManifest.xml
```

Если при первом открытии Gradle запросит загрузку — согласитесь. Если появится ошибка про Gradle Wrapper: **Tools → Gradle → Create Gradle Wrapper**, затем снова **Sync**. При других ошибках синхронизации обновите Android Gradle Plugin и Gradle до версий, предложенных в уведомлениях.

## Если sync не проходит

- **«Your project path contains non-ASCII characters»** — Gradle плохо работает с путями из кириллицы. Перенесите проект в папку без русских букв, например: `C:\Users\xiaomi\code\portfolio\AutomoykaGo`, затем откройте эту папку в Android Studio заново.
- **«Incompatible Gradle JVM»** — в проекте уже указан Gradle 8.5. Убедитесь, что в **File → Settings → Build, Execution, Deployment → Build Tools → Gradle** выбран JDK 17 (или 11), а не более новая версия (например, 21).
