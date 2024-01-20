package com.example.dbsservice.model.repository;

import com.example.dbsservice.model.entity.ExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionRepository extends JpaRepository<ExceptionEntity, String> {
}
