package in.event.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.event.model.attendee;

public interface AttendeeRepository extends JpaRepository<attendee, Long> {

}
