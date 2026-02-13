-- created_at is indexed
SELECT id, user_id, created_at
FROM orders
WHERE DATE(created_at) = '2026-02-12';


SELECT id, user_id, created_at
FROM orders
WHERE created_at >= '2026-02-12 00:00:00'
  AND created_at <  '2026-02-13 00:00:00';


-- SELECT id, name
-- FROM products
-- WHERE name LIKE '%laptop%';


-- CREATE INDEX idx_products_name_fts
-- ON products
-- USING GIN (to_tsvector('english', name));

-- SELECT id, name
-- FROM products
-- ORDER BY id
-- LIMIT 20 OFFSET 100000;


-- SELECT id, name
-- FROM products
-- WHERE id > 100000
-- ORDER BY id
-- LIMIT 20;
