package in.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.event.model.attendee;
import in.event.repo.AttendeeRepository;
import in.event.util.JwtUtil;

@Service
public class AttendeeService {
	
	@Autowired
	private JwtUtil jwt;
	
	@Autowired
	private AttendeeRepository attendeeRepo;
	

	public attendee createAttendee(attendee data,String token) {
		String useremail = jwt.extractUsername(token);
	 
        if (jwt.validateToken(token, useremail)) {
 
            return attendeeRepo.save(data);
        } else {
            throw new RuntimeException("Invalid or expired token");
        }
		 
		
	}
	
	public List<attendee> getAllAttendee(String token){
		String useremail = jwt.extractUsername(token);
		if(jwt.validateToken(token,useremail)) {
			return attendeeRepo.findAll() ;
		}else {
			 throw new RuntimeException("Invalid or expired token");
		}
	}
	
	 public void deleteAttendee(Long attendeeId, String token) {
	     
	        String username = jwt.extractUsername(token);

	        if (!jwt.validateToken(token, username)) {
	            throw new RuntimeException("Invalid or expired token");
	        }

	        
	        if (!attendeeRepo.existsById(attendeeId)) {
	            throw new RuntimeException("Event with ID " + attendeeId + " not found");
	        }

	       
	        attendeeRepo.deleteById(attendeeId);
	    }
}
