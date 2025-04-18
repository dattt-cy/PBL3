package com.pbl.service;

import com.pbl.dao.TasksDAO;
import com.pbl.dao.TasksDAOImp;
import com.pbl.model.Task;
import java.util.List;

public class TaskService {

    private TasksDAO tasksDAO;

    public TaskService() {
       this.tasksDAO = new TasksDAOImp(); 
    }
    
    /**
     * Lấy danh sách task theo ngày và userId.
     * @param date Ngày ở định dạng "yyyy-MM-dd"
     * @param userId ID của người dùng cần lấy task
     * @return Danh sách task tương ứng
     */
    public List<Task> getTasksByDate(String date, int userId) {
        if(date == null || date.trim().isEmpty()){
            throw new IllegalArgumentException("Date không được để trống!");
        }
        return tasksDAO.getTasks(date, userId);
    }
    
    /**
     * Kiểm tra xem có task nào của user vào ngày cho trước không.
     * @param date Ngày ở định dạng "yyyy-MM-dd"
     * @param userId ID của người dùng
     * @return true nếu tồn tại task, false nếu không có
     */
    public boolean hasTaskOnDate(String date, int userId) {
        if(date == null || date.trim().isEmpty()){
            throw new IllegalArgumentException("Date không được để trống!");
        }
        return tasksDAO.hasTasks(date, userId);
    }
    
    /**
     * Tạo mới một task.
     * Task phải có tiêu đề hợp lệ và chứa userId > 0.
     * @param task Task cần tạo
     */
    public void createTask(Task task) {
        if(task == null){
            throw new IllegalArgumentException("Task không được null!");
        }
        if(task.getTitle() == null || task.getTitle().trim().isEmpty()){
            throw new IllegalArgumentException("Tiêu đề công việc không được để trống!");
        }
        if(task.getUserId() <= 0){
            throw new IllegalArgumentException("User ID không hợp lệ!");
        }
        tasksDAO.createTask(task);
    }
    
    /**
     * Cập nhật một task hiện có.
     * Task phải có task_id hợp lệ (khác 0).
     * @param task Task cần cập nhật
     */
    public void updateTask(Task task) {
        if(task == null || task.getID() == 0) {
            throw new IllegalArgumentException("Task hoặc ID không hợp lệ!");
        }
        tasksDAO.updateTask(task);
    }
    
    /**
     * Xoá task theo task_id.
     * @param id ID của task cần xoá (phải > 0)
     */
    public void deleteTask(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException("ID phải > 0!");
        }
        tasksDAO.deleteTask(id);
    }
    
    /**
     * Lấy một câu nói truyền cảm hứng dựa vào số day.
     * @param day Số ngày (ví dụ: 1, 2, …)
     * @return Câu nói
     */
 
    
    // Nếu có nhu cầu, bạn có thể thêm phương thức
    // public void createOrUpdateNotes(String date, String note) {
    //     tasksDAO.createOrUpdateNotes(date, note);
    // }
}
