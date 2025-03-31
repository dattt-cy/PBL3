/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pbl.dao;

import com.pbl.model.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface TasksDAO {
     public List<Task> getTasks(String date);

    public boolean hasTasks(String date);

    public boolean hasNotes(String date);

    public void createTask(Task t);

    public void deleteTask(int ID);

    public void updateTask(Task t);

    public String getQuote(int day);

    public String getNotes(String date);

    public void createOrUpdateNotes();

    public void updateNotes(String date, String note);
    
    
}
