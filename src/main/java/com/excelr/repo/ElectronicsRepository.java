package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excelr.model.Electronics;

public interface ElectronicsRepository extends JpaRepository<Electronics, Long> {
	List<Electronics> findByNameContainingIgnoreCase(String name);

}
