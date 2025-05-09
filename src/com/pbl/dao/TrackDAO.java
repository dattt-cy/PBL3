//
//package com.pbl.dao;
//
//import com.pbl.form.Clockk;
//import com.pbl.model.Track;
//import java.util.ArrayList;
//import java.util.List;
//import java.sql.*;
//
//
//public class TrackDAO {
//      public static List<Track> loadTracks() {
//        String sql = "SELECT id, title, file_path FROM tracks";
//        List<Track> list = new ArrayList<>();
//        try (ResultSet rs = DBHelper.getInstance().getRecords(sql)) {
//            while (rs.next()) {
//                list.add(new Track(
//                    rs.getInt("id"),
//                    rs.getString("title"),
//                    rs.getString("file_path")
//                ));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
////    public static byte[] getTrackData(int id) {
////         String sql =
////            "SELECT data FROM songs WHERE id=? " +
////            "UNION ALL " +
////            "SELECT data FROM ringstones WHERE id=?";
////        try(ResultSet rs =
////                DBHelper.getInstance().getRecords(sql, id, id)){
////            if(rs.next()) return rs.getBytes(1);
////        }catch(Exception e){ e.printStackTrace(); }
////        return null;
////    }
//   public static void insertTrack(int userId, String title, String filePath) {
//        String sql = "INSERT INTO tracks(user_id, title, file_path) VALUES(?, ?, ?)";
//        try {
//            DBHelper.getInstance().executeUpdate(sql,
//                userId,   
//                title,
//                filePath);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//   
//}
