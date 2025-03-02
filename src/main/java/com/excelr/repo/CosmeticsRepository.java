package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Cosmetics;
import com.excelr.model.Electronics;
@Repository
public interface CosmeticsRepository extends JpaRepository<Cosmetics, Long> {
	List<Cosmetics> findByNameContainingIgnoreCase(String name);
}
