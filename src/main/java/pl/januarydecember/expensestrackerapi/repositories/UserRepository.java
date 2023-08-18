package pl.januarydecember.expensestrackerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.januarydecember.expensestrackerapi.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}