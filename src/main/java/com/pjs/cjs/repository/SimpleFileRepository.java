package com.pjs.cjs.repository;

import com.pjs.cjs.model.SimpleFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleFileRepository extends JpaRepository<SimpleFile, String> {
}
