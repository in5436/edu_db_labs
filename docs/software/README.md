# Реалізація інформаційного та програмного забезпечення

## Опис системи
Ця база даних призначена для зберігання інформації про медіа-запити користувачів, джерела медіа-контенту, теги, ролі користувачів та їхні дозволи, а також для відстеження дій і відгуків.

---

## Структура бази даних

- **role** — ролі користувачів (адміністратор, користувач)
- **permission** — права доступу (перегляд, редагування, видалення)
- **role_has_permission** — зв’язок між ролями та дозволами (багато-до-багатьох)
- **user** — користувачі системи
- **source** — джерела медіа-контенту (BBC, CNN і т.п.)
- **tag** — теги для категоризації контенту (політика, спорт і т.д.)
- **label** — зв’язок між тегами і джерелами (які теги відносяться до яких джерел)
- **media_request** — запити користувачів на аналіз медіа
- **based_on** — зв’язок між джерелами і запитами (на яких джерелах базується запит)
- **state** — стани запитів (створено, в обробці, завершено)
- **action** — дії користувачів над запитами (зміна стану, час, користувач)
- **feedback** — відгуки користувачів щодо запитів

---

## SQL код створення таблиць

```sql
CREATE DATABASE IF NOT EXISTS media_analysis_system;
USE media_analysis_system;

CREATE TABLE role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(512)
);

CREATE TABLE permission (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE role_has_permission (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE source (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    url VARCHAR(255)
);

CREATE TABLE tag (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE label (
    tag_id INT NOT NULL,
    source_id INT NOT NULL,
    PRIMARY KEY (tag_id, source_id),
    FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
    FOREIGN KEY (source_id) REFERENCES source(id) ON DELETE CASCADE
);

CREATE TABLE media_request (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(1024),
    keywords VARCHAR(255),
    type VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,
    source_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (source_id) REFERENCES source(id)
);

CREATE TABLE based_on (
    source_id INT NOT NULL,
    media_request_id INT NOT NULL,
    PRIMARY KEY (source_id, media_request_id),
    FOREIGN KEY (source_id) REFERENCES source(id) ON DELETE CASCADE,
    FOREIGN KEY (media_request_id) REFERENCES media_request(id) ON DELETE CASCADE
);

CREATE TABLE state (
    id INT AUTO_INCREMENT PRIMARY KEY,
    display_name VARCHAR(255)
);

CREATE TABLE action (
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    state_id INT,
    media_request_id INT,
    source_id INT,
    user_id INT,
    PRIMARY KEY (created_at, state_id, media_request_id, source_id, user_id),
    FOREIGN KEY (state_id) REFERENCES state(id),
    FOREIGN KEY (media_request_id) REFERENCES media_request(id),
    FOREIGN KEY (source_id) REFERENCES source(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    body VARCHAR(255),
    rating FLOAT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    media_request_id INT,
    user_id INT,
    FOREIGN KEY (media_request_id) REFERENCES media_request(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);
```

```sql
INSERT INTO role (name, description) VALUES
('Admin', 'Адміністратор системи'),
('User', 'Звичайний користувач');

INSERT INTO permission (name) VALUES
('view_media_request'),
('edit_media_request'),
('delete_media_request'),
('add_feedback');

INSERT INTO role_has_permission (role_id, permission_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 4);

INSERT INTO user (first_name, last_name, username, email, password, role_id) VALUES
('Іван', 'Петров', 'ivan_petrov', 'ivan.petrov@example.com', 'hashed_password_ivan', 1),
('Анна', 'Коваленко', 'anna_kovalenko', 'anna.kovalenko@example.com', 'hashed_password_anna', 2);

INSERT INTO source (name, url) VALUES
('BBC News', 'https://bbc.com'),
('CNN', 'https://cnn.com');

INSERT INTO tag (name) VALUES
('Політика'),
('Технології'),
('Спорт');

INSERT INTO label (tag_id, source_id) VALUES
(1, 1),
(2, 2),
(3, 1);

INSERT INTO media_request (name, description, keywords, type, user_id, source_id) VALUES
('Аналіз новин', 'Аналіз політичних новин BBC', 'вибори, уряд', 'текст', 1, 1),
('Технічний огляд', 'Огляд новинок від CNN', 'смартфони, гаджети', 'текст', 2, 2);

INSERT INTO based_on (source_id, media_request_id) VALUES
(1, 1),
(2, 2);

INSERT INTO state (display_name) VALUES
('Створено'),
('В обробці'),
('Завершено');

INSERT INTO action (created_at, state_id, media_request_id, source_id, user_id) VALUES
(NOW(), 1, 1, 1, 1),
(NOW(), 2, 1, 1, 1),
(NOW(), 1, 2, 2, 2);

INSERT INTO feedback (body, rating, media_request_id, user_id) VALUES
('Добрий аналіз, дякую!', 4.5, 1, 2),
('Потрібно покращити точність', 3.0, 2, 1);
```

---

## Висновок

Ця база даних дозволяє зберігати та керувати інформацією про медіа-запити, джерела, користувачів та їхні ролі і дії. Використання реляційних зв’язків забезпечує гнучкість у роботі із системою та зручність при розширенні функціоналу.

---

