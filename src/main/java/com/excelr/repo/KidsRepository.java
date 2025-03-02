package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Electronics;
import com.excelr.model.KidsClothing;
@Repository
public interface KidsRepository extends JpaRepository<KidsClothing, Long> {
	List<KidsClothing> findByNameContainingIgnoreCase(String name);
}
