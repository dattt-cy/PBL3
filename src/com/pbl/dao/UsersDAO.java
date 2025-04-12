/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pbl.dao;

import com.pbl.model.Users;

/**
 *
 * @author ADMIN
 */
public interface UsersDAO {
   Users getUserByEmai(String email);
   void createUser(Users user);
}
