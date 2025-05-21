package com.example.dao;

import com.example.model.Permission;
import java.sql.SQLException;
import java.util.List;

public interface PermissionDAO {
    void addPermission(Permission permission) throws SQLException;
    Permission getPermissionById(int id) throws SQLException;
    List<Permission> getAllPermissions() throws SQLException;
    void updatePermission(Permission permission) throws SQLException;
    void deletePermission(int id) throws SQLException;
}
