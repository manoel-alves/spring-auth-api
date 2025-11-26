package com.manoel.springauthapi.repository;

import com.manoel.springauthapi.entity.User;
import com.manoel.springauthapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by email
    Optional<User> findByEmail(String email);
    // Check if an email is already registered
    boolean existsByEmail(String email);
    // Retrieve all users with the specified role
    List<User> findByRole(Role role);
    // Find users whose email starts with the given prefix
    List<User> findByEmailStartingWith(String prefix);
    // Find users whose email contains the specified fragment
    List<User> findByEmailContaining(String fragment);
    // Count users with the specified role
    long countByRole(Role role);
}
