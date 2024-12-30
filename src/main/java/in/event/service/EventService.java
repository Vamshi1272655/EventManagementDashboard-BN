package in.event.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.event.model.event;
import in.event.repo.EventRepository;
import in.event.util.JwtUtil;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepo;
	 
	
	@Autowired
	private JwtUtil jwt;

	public event createEvent(event data,String token) {
		String useremail = jwt.extractUsername(token);
		System.out.println(useremail);
		if(jwt.validateToken(token,useremail)) {
			return eventRepo.save(data);
		}else {
			 throw new RuntimeException("Invalid or expired token");
		}	
	}
	
	public List<event> getAllEvent(String token){
		String useremail = jwt.extractUsername(token);
		if(jwt.validateToken(token,useremail)) {
			return eventRepo.findAll() ;
		}else {
			 throw new RuntimeException("Invalid or expired token");
		}
	}
	
	 public event updateEvent(event data, String token) {
	        // Extract username from the token
	        String username = jwt.extractUsername(token);

	        // Validate token
	        if (!jwt.validateToken(token, username)) {
	            throw new RuntimeException("Invalid or expired token");
	        }

	        // Check if the event exists
	        Optional<event> existingEvent = eventRepo.findById(data.getEid());
	        if (existingEvent.isEmpty()) {
	            throw new RuntimeException("Event with ID " + data.getEid() + " not found");
	        }

	        // Update the event and save to the database
	        event updatedEvent = existingEvent.get();
	        updatedEvent.setName(data.getName());
	        updatedEvent.setDescription(data.getDescription());
	        updatedEvent.setLocation(data.getLocation());
	        updatedEvent.setEvent_date(data.getEvent_date());
	        return eventRepo.save(updatedEvent);
	    }
	
	 public void deleteEvent(Long eventId, String token) {
	        // Validate token and extract username
	        String username = jwt.extractUsername(token);

	        if (!jwt.validateToken(token, username)) {
	            throw new RuntimeException("Invalid or expired token");
	        }

	        // Check if the event exists
	        if (!eventRepo.existsById(eventId)) {
	            throw new RuntimeException("Event with ID " + eventId + " not found");
	        }

	       
	        eventRepo.deleteById(eventId);
	    }
	
}
