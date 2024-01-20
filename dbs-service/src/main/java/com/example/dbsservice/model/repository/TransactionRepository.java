package com.example.dbsservice.model.repository;

import com.example.dbsservice.model.entity.RequestEntity;
import com.example.dbsservice.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findBySourceAccountOrDebtorAccountNumberOrderByCreatedDateDesc(String sourceAccount, String debtorAccountNumber);
}
