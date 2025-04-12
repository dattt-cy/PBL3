/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbl.dao;

import com.pbl.model.Users;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class UsersDAOImp implements UsersDAO {

    private DBHelper dbHelper;

    public UsersDAOImp() {
        dbHelper = DBHelper.getInstance();
    }

    @Override
    public Users getUserByEmai(String email) {
      String sql = "SELECT * FROM USERS WHERE email = ? LIMIT 1";
      try(ResultSet rs = dbHelper.getRecords(sql, email)){
          if(rs.next()){
               return mapRowToUser(rs);
          }
      }catch(SQLException e){
          e.printStackTrace();
      }
      return null;
              
    }

    @Override
    public void createUser(Users user) {
           String sql = "INSERT INTO users (username, email, password, salt, status, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            dbHelper.executeUpdate(sql, 
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getSalt(),
                    user.isStatus(),
                    user.getRole());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
      private Users mapRowToUser(ResultSet rs) throws SQLException {
        Users u = new Users();
        u.setUser_id(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setSalt(rs.getString("salt"));
        u.setStatus(rs.getBoolean("status"));
        u.setRole(rs.getString("role"));
        u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        u.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        if(rs.getTimestamp("last_login") != null) {
            u.setLastLogin(rs.getTimestamp("last_login").toLocalDateTime());
        }
        return u;
    }

   
}
