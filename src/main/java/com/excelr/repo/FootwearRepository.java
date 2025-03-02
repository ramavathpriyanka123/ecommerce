package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.excelr.model.Footwear;
@Repository
public interface FootwearRepository extends JpaRepository<Footwear, Long> {
	List<Footwear> findByNameContainingIgnoreCase(String name);
}
