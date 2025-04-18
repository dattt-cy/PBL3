/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbl.dao;

import com.pbl.model.Note;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ADMIN
 */
public class NotesDAOImp implements NotesDAO {

    private DBHelper dbHelper;

    public NotesDAOImp() {
        dbHelper = DBHelper.getInstance();
    }

    @Override
    public Note getNoteByDate(String date) {
        String sql = "SELECT note FROM note WHERE date = ?";
        try (ResultSet rs = dbHelper.getRecords(sql, date)) {
            if (rs.next()) {
                String noteContent = rs.getString("note");
                LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new Note(d, noteContent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return new Note(d, "");

    }

    @Override
    public void saveOrUpdateNote(String date, String note) {
        String sql = "INSERT INTO note (date, note) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE note = VALUES(note)";
        try {
            dbHelper.executeUpdate(sql, date, note);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
