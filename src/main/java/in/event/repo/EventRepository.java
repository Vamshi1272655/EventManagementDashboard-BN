package in.event.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.event.model.event;

 
public interface EventRepository extends JpaRepository<event, Long> {
	 
}
