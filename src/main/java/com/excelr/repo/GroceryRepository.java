package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Electronics;
import com.excelr.model.Grocery;
@Repository
public interface GroceryRepository extends JpaRepository<Grocery, Long> {
	List<Grocery> findByNameContainingIgnoreCase(String name);
}
