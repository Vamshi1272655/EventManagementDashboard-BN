package in.event.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.event.dto.ApiResponse;
import in.event.dto.LoginRequest;
import in.event.model.attendee;
import in.event.model.event;
import in.event.model.task;
import in.event.model.user;
import in.event.service.AttendeeService;
import in.event.service.EventService;
import in.event.service.TaskService;
import in.event.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private AttendeeService attendeeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ApiResponse apiResponse;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> createUser(@RequestBody user userData) {
	    try {
	        user createdUser = userService.createUser(userData);

	        apiResponse.setStatus("success");
	        apiResponse.setMessage("User registered successfully.");
	        apiResponse.setData(createdUser);

	        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

	    } catch (RuntimeException ex) {
	        
	        apiResponse.setStatus("failed");
	        apiResponse.setMessage(ex.getMessage());
	        apiResponse.setData(null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
	    }
	}

    
    

	@GetMapping("/users")
    public List<user> getAllUsers() {
    	return userService.getAllUsers();
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
    	try {
    		String token =userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    		apiResponse.setStatus("success");
	        apiResponse.setMessage("User Loged-in successfully.");
	        apiResponse.setData(token);
	        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    	}catch(RuntimeException ex){
    		apiResponse.setStatus("failed");
	        apiResponse.setMessage(ex.getMessage());
	        apiResponse.setData(null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    	}
          
    }
    
    @PostMapping("/events")
    public event createEvent(@RequestBody event data, @RequestHeader("Authorization") String authHeader) {
    	String token = authHeader.substring(7);
    	return eventService.createEvent(data, token);
    	
    }
    
    @GetMapping("/getevents")
    public List<event> getEvent(@RequestHeader("Authorization") String authHeader) {
    	String token = authHeader.substring(7);
    	return eventService.getAllEvent(token);
    	
    }
    
    @PutMapping("/events/{id}")
    public event updateEvent( @PathVariable("id") Long id, @RequestBody event data, @RequestHeader("Authorization") String authHeader) {
    	String token = authHeader.substring(7);
    	data.setEid(id);
        return eventService.updateEvent(data, token);
    	
    }
    
    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> deleteEvent(
    		@PathVariable("id") Long id,
            @RequestHeader("Authorization") String authHeader) {

        // Extract the token from the Authorization header
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Call the service layer to delete the event
        eventService.deleteEvent(id, token);

        // Return a success response
        return ResponseEntity.ok("Event with ID " + id + " successfully deleted.");
    }
    
    @PostMapping("/attendee")
    public attendee CreateAttendee(@RequestBody attendee data,@RequestHeader("Authorization") String authHeader) {
    	
    	String token = authHeader.substring(7);
    	return attendeeService.createAttendee(data, token);
    }
    
    @GetMapping("/getattendee")
    public List<attendee> GetAttendee(@RequestHeader("Authorization") String authHeader) {
    	
    	String token = authHeader.substring(7);
    	return attendeeService.getAllAttendee(token);
    }
    
    @DeleteMapping("/attendee/{id}")
    public ResponseEntity<String> deleteAttendee(
    		@PathVariable("id") Long id,
            @RequestHeader("Authorization") String authHeader) {

        // Extract the token from the Authorization header
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Call the service layer to delete the event
        attendeeService.deleteAttendee(id, token);

        // Return a success response
        return ResponseEntity.ok("Attendee with ID " + id + " successfully deleted.");
    }
    
    @PostMapping("/task")
    public task createTask(@RequestBody task data, @RequestHeader("Authorization") String authHeader) {
    	String token = authHeader.substring(7);
    	return taskService.createTask(data, token);
    	
    }
    
    @GetMapping("/gettask")
    public List<task> GetTask(@RequestHeader("Authorization") String authHeader) {
    	String token = authHeader.substring(7);
    	return taskService.getAllTask(token);
    }
    
    @PutMapping("/task/{id}")
    public ResponseEntity<task> updateTaskStatus(@PathVariable("id") Long id,  @RequestBody Map<String, String> requestBody) {
    	String status = requestBody.get("status");
        System.out.println("Received task ID: " + id + " and status: " + status);
        task updatedTask = taskService.updateStatus(id, status);
        return ResponseEntity.ok(updatedTask);
    }

    
}
