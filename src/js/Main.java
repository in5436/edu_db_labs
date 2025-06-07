package com.example;

import com.example.dao.MediaRequestDAO;
import com.example.dao.MediaRequestDAOImpl;
import com.example.model.MediaRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * Приклад виконання CRUD-операцій над таблицею media_request.
 */
public class Main {
    public static void main(String[] args) {
        try {
            MediaRequestDAO dao = new MediaRequestDAOImpl();

            // CREATE
            MediaRequest mr = new MediaRequest(
                "News Analysis",
                "Analyze latest news",
                "news,analysis,politics",
                "text",
                1,  // user_id
                1   // source_id
            );
            dao.addMediaRequest(mr);
            System.out.println("Created: " + mr);

            // READ ALL
            List<MediaRequest> all = dao.getAllMediaRequests();
            System.out.println("All media requests:");
            all.forEach(System.out::println);

            // UPDATE
            mr.setDescription("Updated description for news analysis");
            dao.updateMediaRequest(mr);
            System.out.println("After update: " + dao.getMediaRequestById(mr.getId()));

            // DELETE
            dao.deleteMediaRequest(mr.getId());
            System.out.println("After delete:");
            dao.getAllMediaRequests().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}