## SQL: create_media_request_db.sql

```sql

-- 1. Створити схему (якщо не існує)
CREATE DATABASE IF NOT EXISTS media_request_db;

-- 2. Перемкнути контекст на нову базу
USE media_request_db;

-- 3. Визначити структуру таблиці media_request
CREATE TABLE IF NOT EXISTS media_request (
    id INT AUTO_INCREMENT PRIMARY KEY,            -- Унікальний ідентифікатор
    name VARCHAR(255),                            -- Назва запиту
    description VARCHAR(1024),                    -- Опис запиту
    keywords VARCHAR(255),                        -- Ключові слова (розділені комами)
    type VARCHAR(255),                            -- Тип контенту (video, audio, text)
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,-- Дата створення
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP -- Дата останнього оновлення
        ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,                                  -- Логічне посилання на користувача
    source_id INT                                 -- Логічне посилання на джерело
);
```

**Пояснення**  
- `AUTO_INCREMENT PRIMARY KEY` автоматично генерує унікальний `id`.  
- `created_at` і `updated_at` фіксують час, коли запис створено і коли він востаннє змінювався.  
- `user_id` і `source_id` залишаються без зовнішніх ключів, щоб таблиця була незалежною.
