package com.excelr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelr.model.Electronics;
import com.excelr.model.Mobiles;
@Repository
public interface MobilesRepository extends JpaRepository<Mobiles, Long> {
	List<Mobiles> findByNameContainingIgnoreCase(String name);
}
