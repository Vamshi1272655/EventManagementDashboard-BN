package in.event.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.event.model.user;

public interface UserRepository extends JpaRepository<user, Long> {
	user findByUsername(String username);
    user findByEmail(String email);
}
