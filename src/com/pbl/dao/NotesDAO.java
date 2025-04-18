/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pbl.dao;

import com.pbl.model.Note;

/**
 *
 * @author ADMIN
 */
public interface NotesDAO {
     Note getNoteByDate(String date);

   
    void saveOrUpdateNote(String date, String note);
}
