-- Индексы для ускорения запросов (опционально для SQLite)
-- Можно применить при миграции или вручную

CREATE INDEX IF NOT EXISTS idx_users_name ON users(name);
CREATE INDEX IF NOT EXISTS idx_orders_createdAt ON orders(createdAt);
CREATE INDEX IF NOT EXISTS idx_orders_userName ON orders(userName);
CREATE INDEX IF NOT EXISTS idx_reviews_createdAt ON reviews(createdAt);
CREATE INDEX IF NOT EXISTS idx_reviews_rating ON reviews(rating);
