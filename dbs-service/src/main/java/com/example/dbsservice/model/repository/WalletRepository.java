package com.example.dbsservice.model.repository;

import com.example.dbsservice.model.entity.UserEntity;
import com.example.dbsservice.model.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    List<WalletEntity> findAllByStatus(String status);
}
