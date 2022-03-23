package be.kdg.java2.project.repository;

import be.kdg.java2.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    Optional<User> findByUsername(String username);
}
