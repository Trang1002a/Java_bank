package com.example.dbsservice.model.repository;

import com.example.dbsservice.model.entity.AccountEntity;
import com.example.dbsservice.model.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, String> {
    Optional<RequestEntity> findByIdAndStatus(String id, String status);
}
