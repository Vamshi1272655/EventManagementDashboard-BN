package in.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

 
import in.event.model.user;
import in.event.repo.UserRepository;
import in.event.util.JwtUtil;
import jakarta.validation.Valid;

@Service
public class UserService {
	
	 @Autowired
	    private UserRepository userRepository;
	 
	 @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	 
	 @Autowired
	 	private JwtUtil jwtToken;

	 	public List<user> getAllUsers() {
	        return  userRepository.findAll();
	    }
	 	
	 	public user createUser(@Valid user data) {
	 	    // Check if any of the required fields are missing
	 	    if (data.getEmail() == null || data.getPassword() == null || data.getUsername() == null) {
	 	        throw new RuntimeException("Any of the fields value is missing");
	 	    }

	 	    // Check if the email already exists in the database
	 	    user existingUser = userRepository.findByEmail(data.getEmail());
	 	    if (existingUser != null) {
	 	        throw new RuntimeException("Email is already in use");
	 	    }

	 	    // Encrypt the password and save the user
	 	    data.setPassword(passwordEncoder.encode(data.getPassword()));
	 	    return userRepository.save(data);
	 	}

	 	 
	 	 public String login(String email, String password) {
	         user existingUser = userRepository.findByEmail(email);
	         System.out.println(email+" "+password);
	         if (existingUser == null) {
	             throw new RuntimeException("User not found");
	         }

	         // Check password
	         if (!passwordEncoder.matches(password, existingUser.getPassword())) {
	             throw new RuntimeException("Invalid password");
	         }
	         

	         return jwtToken.generateToken(email);
	     }
}
