package com.ruralconnect.backend.repository;

import com.ruralconnect.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// We extend JpaRepository, telling it to manage the <User, Long>
// (The model is User, the ID type is Long)
public interface UserRepository extends JpaRepository<User, Long> {

    // This is a custom query Spring builds automatically from the method name
    // It's vital for our login system
    Optional<User> findByEmail(String email);
}