package com.felipemdf.SpringQuickStart.repository;

import com.felipemdf.SpringQuickStart.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Boolean existsByEmail(String email);
	Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
