package com.example.dbsservice.model.repository;

import com.example.dbsservice.model.entity.WalletEntity;
import com.example.dbsservice.model.entity.WalletLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletLinkRepository extends JpaRepository<WalletLinkEntity, String> {

    Optional<WalletLinkEntity> findAllByWalletIdAndAccountNumber(String walletId, String accountNumber);

    List<WalletLinkEntity> findAllByUserIdAndStatus(String userId, String status);

    List<WalletLinkEntity> findAllByUserIdAndStatusAndWalletIdIn(String userId, String status, List<String> walletId);
}
