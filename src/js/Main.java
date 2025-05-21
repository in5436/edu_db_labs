package com.example.dao;

import com.example.dao.PermissionDAO;
import com.example.dao.PermissionDAOImpl;
import com.example.model.Permission;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            PermissionDAO permDao = new PermissionDAOImpl();

            // Додавання
            Permission p = new Permission("view_media_request");
            permDao.addPermission(p);
            System.out.println("Додано: " + p);

            // Читання всіх
            List<Permission> all = permDao.getAllPermissions();
            System.out.println("Всі записи:");
            all.forEach(System.out::println);

            // Оновлення
            p.setName("edit_media_request");
            permDao.updatePermission(p);
            System.out.println("Після оновлення: " + permDao.getPermissionById(p.getId()));

            // Видалення
            //permDao.deletePermission(p.getId());
            //System.out.println("Після видалення залишились:");
            //permDao.getAllPermissions().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
