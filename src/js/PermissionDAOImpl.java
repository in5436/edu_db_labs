package com.example.dao;

import com.example.model.Permission;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAOImpl implements PermissionDAO {
    private final Connection conn;

    public PermissionDAOImpl() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    @Override
    public void addPermission(Permission permission) throws SQLException {
        String sql = "INSERT INTO permission (name) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, permission.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    permission.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Permission getPermissionById(int id) throws SQLException {
        String sql = "SELECT * FROM permission WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Permission(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Permission> getAllPermissions() throws SQLException {
        List<Permission> list = new ArrayList<>();
        String sql = "SELECT * FROM permission";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Permission(
                    rs.getInt("id"),
                    rs.getString("name")
                ));
            }
        }
        return list;
    }

    @Override
    public void updatePermission(Permission permission) throws SQLException {
        String sql = "UPDATE permission SET name = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, permission.getName());
            ps.setInt(2, permission.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deletePermission(int id) throws SQLException {
        String sql = "DELETE FROM permission WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
