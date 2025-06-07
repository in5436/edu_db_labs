package com.example.model;

import java.sql.Timestamp;

/**
 * Модель об’єкта MediaRequest, що відображає рядок таблиці.
 */
public class MediaRequest {
    private int id;
    private String name;
    private String description;
    private String keywords;
    private String type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int userId;
    private int sourceId;

    public MediaRequest() { }

    /**
     * Конструктор для вставки нового запису (без id, createdAt, updatedAt).
     */
    public MediaRequest(String name,
                        String description,
                        String keywords,
                        String type,
                        int userId,
                        int sourceId) {
        this.name = name;
        this.description = description;
        this.keywords = keywords;
        this.type = type;
        this.userId = userId;
        this.sourceId = sourceId;
    }

    /**
     * Конструктор для читання з БД (з усіма полями).
     */
    public MediaRequest(int id,
                        String name,
                        String description,
                        String keywords,
                        String type,
                        Timestamp createdAt,
                        Timestamp updatedAt,
                        int userId,
                        int sourceId) {
        this(name, description, keywords, type, userId, sourceId);
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ————— Геттери та сеттери —————

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getSourceId() { return sourceId; }
    public void setSourceId(int sourceId) { this.sourceId = sourceId; }

    @Override
    public String toString() {
        return "MediaRequest{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", keywords='" + keywords + '\'' +
               ", type='" + type + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               ", userId=" + userId +
               ", sourceId=" + sourceId +
               '}';
    }
}