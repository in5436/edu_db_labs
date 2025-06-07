package com.example.dao;

import com.example.model.MediaRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Контракт CRUD-операцій для таблиці media_request.
 */
public interface MediaRequestDAO {
    void addMediaRequest(MediaRequest mr) throws SQLException;
    MediaRequest getMediaRequestById(int id) throws SQLException;
    List<MediaRequest> getAllMediaRequests() throws SQLException;
    void updateMediaRequest(MediaRequest mr) throws SQLException;
    void deleteMediaRequest(int id) throws SQLException;
}