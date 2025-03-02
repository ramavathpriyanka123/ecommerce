package com.excelr.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 boolean existsByUsername(String username);
	    
	    // Method to find a user by username
	    Optional<User> findByUsername(String username);
	    
    
}
