package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Electronics;
import com.excelr.model.Laptops;
@Repository
public interface LaptopsRepository extends JpaRepository<Laptops, Long> {
	List<Laptops> findByNameContainingIgnoreCase(String name);
}
