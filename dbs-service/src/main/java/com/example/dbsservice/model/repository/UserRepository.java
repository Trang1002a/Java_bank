package com.example.dbsservice.model.repository;

import com.example.dbsservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserNameAndStatusAndCustomerType(String userName, String status, String customerType);

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    Optional<UserEntity> findByCardNumber(String cardNumber);

    Optional<UserEntity> findByEmail(String email);
}
