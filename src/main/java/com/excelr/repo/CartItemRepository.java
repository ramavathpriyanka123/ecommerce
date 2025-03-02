package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excelr.model.CartItem;

import jakarta.transaction.Transactional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	 List<CartItem> findByCartId(Long cartId);
	
	 @Modifying
	    @Transactional
	    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = (SELECT c.id FROM Cart c WHERE c.user.id = :userId)")
	    void deleteByCartUserId(@Param("userId") Long userId);
}
