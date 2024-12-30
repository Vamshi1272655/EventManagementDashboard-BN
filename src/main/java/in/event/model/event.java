package in.event.model;

 
import java.util.Date;
 

 

 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
import lombok.Data;
 

@Entity
@Data
public class event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eid;
	private String name;
	private String description;
	private String location;
	private Date event_date ;
	
	 
}
