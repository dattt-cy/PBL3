/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbl.service;



import com.pbl.dao.TasksDAO;
import com.pbl.dao.TasksDAOImp;
import com.pbl.model.Task;
import java.util.List;

public class TaskService {
    private TasksDAO tasksDAO;
    
    public TaskService(){
       this.tasksDAO = new TasksDAOImp(); 
    }
    
    public List<Task> getTasksByDate(String date){
        if(date == null || date.isEmpty()){
            throw new IllegalArgumentException("Date không được để trống!");
        }
        return tasksDAO.getTasks(date);
    }
    
    public boolean hasTaskOnDate(String date){
        return tasksDAO.hasTasks(date);
    }
    
    public void createTask(Task task){
        if(task == null){
              throw new IllegalArgumentException("Task không được null!");
        }
        if(task.getTitle() == null  || task.getTitle().trim().isEmpty()){
             throw new IllegalArgumentException("Tiêu đề công việc không được để trống!");
        }
        
        tasksDAO.createTask(task);
    }
    
    public void updateTask(Task task){
        if(task == null || task.getID() == 0){
            throw new IllegalArgumentException("Task hoặc ID không hợp lệ!");
        }
        tasksDAO.updateTask(task);
    }
     public void deleteTask(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID phải > 0!");
        }
        tasksDAO.deleteTask(id);
    }
    
     public String getQuote(int day) {
        return tasksDAO.getQuote(day);
    }
     
     public String getNotes(String date) {
        return tasksDAO.getNotes(date);
    }
//      public void createOrUpdateNotes(String date, String note) {
//        tasksDAO.createOrUpdateNotes(date, note);
//    }
    
}
