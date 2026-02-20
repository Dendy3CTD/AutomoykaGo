-- AutomoykaGo: примеры SQL-запросов к базе SQLite
-- Используются в приложении через AppDb / DatabaseHelper

-- ========== USERS ==========
-- Вставка пользователя (регистрация)
INSERT INTO users (name, password, createdAt) VALUES (?, ?, ?);

-- Поиск пользователя по имени (вход)
SELECT id, name, password, createdAt
FROM users
WHERE name = ?
LIMIT 1;

-- Все пользователи (админ-панель)
SELECT id, name, password, createdAt
FROM users
ORDER BY createdAt DESC;


-- ========== ORDERS ==========
-- Создание заказа (оплата услуг)
INSERT INTO orders (userName, address, moduleNumber, services, totalAmount, createdAt)
VALUES (?, ?, ?, ?, ?, ?);

-- Все заказы (бухучёт)
SELECT id, userName, address, moduleNumber, services, totalAmount, createdAt
FROM orders
ORDER BY createdAt DESC;

-- Общая выручка
SELECT COALESCE(SUM(totalAmount), 0) AS total
FROM orders;

-- Заказы за период
SELECT id, userName, address, moduleNumber, services, totalAmount, createdAt
FROM orders
WHERE createdAt BETWEEN ? AND ?
ORDER BY createdAt DESC;


-- ========== REVIEWS ==========
-- Добавление отзыва
INSERT INTO reviews (userName, text, rating, createdAt) VALUES (?, ?, ?, ?);

-- Все отзывы (экран отзывов)
SELECT id, userName, text, rating, createdAt
FROM reviews
ORDER BY createdAt DESC;

-- Средняя оценка
SELECT AVG(rating) AS avgRating FROM reviews;
