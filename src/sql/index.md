
# Permission DB — Структура та реалізація

Цей документ містить **детальний опис** та пояснення всіх кроків для створення та використання бази даних `permission_db` з єдиною таблицею `permission`. Також наведено Java-код, що демонструє підключення до БД та роботу з таблицею через DAO (Data Access Object) патерн.

---

## SQL-скрипт: create_permission_db.sql

```sql
-- Файл: create_permission_db.sql

-- 1) Створюємо базу даних, якщо її ще немає
CREATE DATABASE IF NOT EXISTS permission_db;

-- 2) Перемикаємося на створену базу
USE permission_db;

-- 3) Створюємо таблицю permission:
CREATE TABLE IF NOT EXISTS permission (
    id INT AUTO_INCREMENT PRIMARY KEY,   -- унікальний ідентифікатор дозволу
    name VARCHAR(255) NOT NULL           -- назва дозволу (обов’язкове)
);
```

**Пояснення**  
- **CREATE DATABASE IF NOT EXISTS**: створює схему `permission_db`, якщо вона відсутня.  
- **USE permission_db**: вибирає схему для наступних операцій.  
- Таблиця `permission` має два поля:  
  - `id` — автоінкрементований первинний ключ;  
  - `name` — назва дозволу, не може бути порожньою.


## Результати

**JAVA**

![Diagram](Java.png)

**SQL**

![Diagram](SQL.png)