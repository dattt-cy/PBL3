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

    public List<Task> getTasks(String date, int userId) {
        List<Task> tasks = new ArrayList<>();
        String select = "SELECT * FROM task WHERE DATE(date) = ? AND user_id = ?";
        try (ResultSet rs = dbHelper.getRecords(select, date, userId)) {
            while (rs.next()) {
                tasks.add(getTaskFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

   public boolean hasTasks(String date, int userId) {
        String select = "SELECT COUNT(*) FROM task WHERE DATE(date) = ? AND user_id = ?";
        try (ResultSet rs = dbHelper.getRecords(select, date, userId)) {
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

    private Task getTaskFromResultSet(ResultSet rs) throws SQLException {
        Task t = new Task();
        t.setID(rs.getInt("task_id"));
        t.setUserId(rs.getInt("user_id"));
        t.setTitle(rs.getString("task_name"));
        t.setDescription(rs.getString("description"));
        t.setCategory(rs.getString("category"));
        t.setDone(rs.getBoolean("status"));
        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) {
            t.setDateTime(LocalDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault()));
        }
        return t;
    }

   
}
