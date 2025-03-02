package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Electronics;
import com.excelr.model.WomenClothing;
@Repository
public interface WomenRepository extends JpaRepository<WomenClothing, Long> {
	List<WomenClothing> findByNameContainingIgnoreCase(String name);
}
