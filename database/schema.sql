-- AutomoykaGo: схема базы данных SQLite
-- Файл: automoykago.db, версия: 3

-- Пользователи (регистрация / вход)
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    password TEXT NOT NULL,
    createdAt INTEGER NOT NULL DEFAULT 0
);

-- Заказы (бухучёт, оплата услуг)
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userName TEXT NOT NULL,
    address TEXT NOT NULL,
    moduleNumber INTEGER NOT NULL,
    services TEXT NOT NULL,
    totalAmount INTEGER NOT NULL,
    createdAt INTEGER NOT NULL DEFAULT 0
);

-- Отзывы
CREATE TABLE IF NOT EXISTS reviews (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userName TEXT NOT NULL,
    text TEXT NOT NULL,
    rating INTEGER NOT NULL,
    createdAt INTEGER NOT NULL DEFAULT 0
);

-- Примеры запросов:
-- SELECT * FROM users ORDER BY createdAt DESC;
-- SELECT COALESCE(SUM(totalAmount), 0) AS total FROM orders;
-- SELECT * FROM orders ORDER BY createdAt DESC;
-- SELECT * FROM reviews ORDER BY createdAt DESC;
