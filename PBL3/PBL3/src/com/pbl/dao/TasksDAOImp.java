package com.pbl.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.pbl.model.Task;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TasksDAOImp implements TasksDAO{
    private DBHelper dbHelper;

    public TasksDAOImp() {
        dbHelper = DBHelper.getInstance();
    }

    public List<Task> getTasks(String date) {
        List<Task> tasks = new ArrayList<>();
        String select = "SELECT * FROM task WHERE DATE(date) = ?";
        try (ResultSet rs = dbHelper.getRecords(select, date)) {
            while (rs.next()) {
                tasks.add(getTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean hasTasks(String date) {
        String select = "SELECT COUNT(*) FROM task WHERE  DATE(date) = ?";
        try (ResultSet rs = dbHelper.getRecords(select, date)) {
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createTask(Task t) {
          String insert = "INSERT INTO task (user_id, task_name, description, category, status, date) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            dbHelper.executeUpdate(insert, t.getTitle(), t.getDescription(), t.getCategory(), t.isDone(), t.getDateToString(), t.getTimeToString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public void updateTask(Task t) {
    
    String update = "UPDATE task SET task_name = ?, description = ?, category = ?, status = ?, date = ? WHERE task_id = ?";
    try {
          String formattedDateTime = t.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dbHelper.executeUpdate(update, t.getTitle(), t.getDescription(), t.getCategory(), t.isDone(), formattedDateTime, t.getID());
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void deleteTask(int ID) {
        String delete = "DELETE FROM task WHERE ID = ?";
        try {
            dbHelper.executeUpdate(delete, ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getQuote(int day) {
        String select = "SELECT quote FROM quotes WHERE ID = ?";
        try (ResultSet rs = dbHelper.getRecords(select, day)) {
            if (rs.next()) return rs.getString("quote");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getNotes(String date) {
        String select = "SELECT note FROM notes WHERE date = ?";
        try (ResultSet rs = dbHelper.getRecords(select, date)) {
            if (rs.next()) return rs.getString("note");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void createOrUpdateNotes(String date, String note) {
        String insertOrUpdate = "INSERT INTO notes (date, note) VALUES (?, ?) ON DUPLICATE KEY UPDATE note = VALUES(note)";
        try {
            dbHelper.executeUpdate(insertOrUpdate, date, note);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Task getTaskFromResultSet(ResultSet rs) throws SQLException {
    Task t = new Task();
    // Thay "ID" thành "task_id"
    t.setID(rs.getInt("task_id"));

    // Thay "Title" thành "task_name"
    t.setTitle(rs.getString("task_name"));

    // "Description" -> "description" (viết thường)
    t.setDescription(rs.getString("description"));

    // "Category" -> "category"
    t.setCategory(rs.getString("category"));

    // "isDone" -> "status" (bạn lưu kiểu BIT; 0 hoặc 1)
    // JDBC cho phép getBoolean("status") nếu cột kiểu BIT hoặc TINYINT
    t.setDone(rs.getBoolean("status"));

    // Thay vì "Date" + "Time", bảng của bạn có "due_date" (DATETIME).
    // Bạn có thể lấy Timestamp rồi chuyển thành LocalDateTime
    Timestamp ts = rs.getTimestamp("date");
    if (ts != null) {
          t.setDateTime(LocalDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault()));
    }
    return t;
}

    @Override
    public boolean hasNotes(String date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void createOrUpdateNotes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateNotes(String date, String note) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
