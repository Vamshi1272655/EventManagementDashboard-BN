package in.event.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.event.model.task;

public interface TaskRepository extends JpaRepository<task, Long> {

}
