package hu.nye.szakdolgozat.data.repository;

import hu.nye.szakdolgozat.data.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}