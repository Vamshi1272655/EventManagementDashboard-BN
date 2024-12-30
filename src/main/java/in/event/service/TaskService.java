package in.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import in.event.model.task;
import in.event.repo.TaskRepository;
import in.event.util.JwtUtil;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;
    
    @Autowired
	private JwtUtil jwt;

    public task createTask(task data,String token) {
    	String useremail = jwt.extractUsername(token);
		System.out.println(useremail);
		if(jwt.validateToken(token,useremail)) {
			return taskRepo.save(data);
		}else {
			 throw new RuntimeException("Invalid or expired token");
		}	
    }
    
    public List<task> getAllTask(String token){
		String useremail = jwt.extractUsername(token);
		if(jwt.validateToken(token,useremail)) {
			return taskRepo.findAll() ;
		}else {
			 throw new RuntimeException("Invalid or expired token");
		}
	}
    
    public task updateStatus(Long taskId, String status) {
       
        task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);
        

        // Save the updated task
        return taskRepo.save(task);
    }
}
