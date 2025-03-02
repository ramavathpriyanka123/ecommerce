package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Electronics;
import com.excelr.model.Toys;
@Repository
public interface ToysRepository extends JpaRepository<Toys, Long> {
	List<Toys> findByNameContainingIgnoreCase(String name);
}
