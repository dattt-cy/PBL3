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
    List<Task> getTasks(String date, int userId);
    boolean hasTasks(String date, int userId);
    void createTask(Task t);
    void deleteTask(int taskId);
    void updateTask(Task t);
    
    
}
