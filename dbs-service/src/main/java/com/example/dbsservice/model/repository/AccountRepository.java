package com.example.dbsservice.model.repository;

import com.example.dbsservice.model.entity.AccountEntity;
import com.example.dbsservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    Optional<AccountEntity> findFirstByUserIdOrderByCreatedDateDesc(String userId);

    List<AccountEntity> findByUserIdAndStatus(String userId, String status);

    Optional<AccountEntity> findFirstByAccountNumberAndStatus(String accountNumber, String status);

    List<AccountEntity> findByAccountNumberInAndStatus(List<String> accountNumbers, String status);

    Optional<AccountEntity> findByAccountNumberAndStatus(String accountNumbers, String status);
}
