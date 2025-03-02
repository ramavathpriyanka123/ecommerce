package com.excelr.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excelr.model.Cart;

import jakarta.transaction.Transactional;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	  
	   Cart findByUserId(Long userId);
	  
	   @Modifying
	    @Transactional
	    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
	    void deleteByUserId(@Param("userId") Long userId);

}
