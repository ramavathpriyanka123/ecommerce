package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Electronics;
import com.excelr.model.MenClothing;
@Repository
public interface MensRepository extends JpaRepository<MenClothing, Long> {
	List<MenClothing> findByNameContainingIgnoreCase(String name);
}
