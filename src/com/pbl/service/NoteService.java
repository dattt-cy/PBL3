/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbl.service;

import com.pbl.dao.NotesDAO;
import com.pbl.dao.NotesDAOImp;
import com.pbl.model.Note;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NoteService {
    private NotesDAO notesDAO;

    public NoteService() {
        
        this.notesDAO = new NotesDAOImp();
    }

  
    public Note getNoteByDate(LocalDate date) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return notesDAO.getNoteByDate(dateStr);
    }

  
    public void saveOrUpdateNote(LocalDate date, String noteContent) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        notesDAO.saveOrUpdateNote(dateStr, noteContent);
    }
}

