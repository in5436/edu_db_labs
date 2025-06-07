package com.example.dao;

import com.example.model.MediaRequest;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC-реалізація CRUD-операцій для media_request.
 */
public class MediaRequestDAOImpl implements MediaRequestDAO {
    private final Connection conn;

    public MediaRequestDAOImpl() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public void addMediaRequest(MediaRequest mr) throws SQLException {
        String sql = "INSERT INTO media_request " +
                     "(name,description,keywords,type,user_id,source_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, mr.getName());
            ps.setString(2, mr.getDescription());
            ps.setString(3, mr.getKeywords());
            ps.setString(4, mr.getType());
            ps.setInt(5, mr.getUserId());
            ps.setInt(6, mr.getSourceId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) mr.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public MediaRequest getMediaRequestById(int id) throws SQLException {
        String sql = "SELECT * FROM media_request WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MediaRequest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("keywords"),
                        rs.getString("type"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getInt("user_id"),
                        rs.getInt("source_id")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<MediaRequest> getAllMediaRequests() throws SQLException {
        List<MediaRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM media_request";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new MediaRequest(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("keywords"),
                    rs.getString("type"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at"),
                    rs.getInt("user_id"),
                    rs.getInt("source_id")
                ));
            }
        }
        return list;
    }

    @Override
    public void updateMediaRequest(MediaRequest mr) throws SQLException {
        String sql = "UPDATE media_request SET " +
                     "name=?, description=?, keywords=?, type=?, user_id=?, source_id=? " +
                     "WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mr.getName());
            ps.setString(2, mr.getDescription());
            ps.setString(3, mr.getKeywords());
            ps.setString(4, mr.getType());
            ps.setInt(5, mr.getUserId());
            ps.setInt(6, mr.getSourceId());
            ps.setInt(7, mr.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteMediaRequest(int id) throws SQLException {
        String sql = "DELETE FROM media_request WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}