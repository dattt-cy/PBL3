
package com.pbl.service;

import com.pbl.model.Takenote;
import com.pbl.dao.DBHelper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TakeNoteService {
   private final DBHelper db = DBHelper.getInstance();

    /**
     * Lấy tất cả note của user, mới nhất trước.
     */
    public List<Takenote> loadAll(int userId) {
        List<Takenote> list = new ArrayList<>();
        String sql = "SELECT id, user_id, title, content, created_at "
                   + "FROM takenotes WHERE user_id = ? "
                   + "ORDER BY created_at DESC";
        try {
            ResultSet rs = db.getRecords(sql, userId);
            while (rs.next()) {
                Takenote note = new Takenote();
                note.setId(rs.getInt("id"));
                note.setUserId(rs.getInt("user_id"));
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    note.setCreatedAt(ts.toLocalDateTime());
                }
                list.add(note);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi load notes", e);
        }
        return list;
    }

    /**
     * Thêm mới note.
     */
    public void add(Takenote note) {
        String sql = "INSERT INTO takenotes (user_id, title, content) VALUES (?, ?, ?)";
        try {
            db.executeUpdate(sql,
                note.getUserId(),
                note.getTitle(),
                note.getContent()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thêm note", e);
        }
    }

    /**
     * Cập nhật note đã có.
     */
    public void update(Takenote note) {
        String sql = "UPDATE takenotes SET title = ?, content = ? WHERE id = ?";
        try {
            db.executeUpdate(sql,
                note.getTitle(),
                note.getContent(),
                note.getId()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật note", e);
        }
    }

    /**
     * Xóa note theo id.
     */
    public void delete(int noteId) {
        String sql = "DELETE FROM takenotes WHERE id = ?";
        try {
            db.executeUpdate(sql, noteId);
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa note", e);
        }
    }
}
