package com.example.dbsservice.presentation.service;

import com.example.dbsservice.model.entity.TransactionEntity;
import com.example.dbsservice.model.request.transaction.ConfirmRequest;
import com.example.dbsservice.model.request.transaction.CreateTransRequest;
import com.example.dbsservice.model.response.ResStatus;
import com.example.dbsservice.model.response.transaction.CreateTransResponse;
import com.example.dbsservice.model.response.transaction.TransResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    CreateTransResponse createTrans(CreateTransRequest request);

   ResStatus confirm(ConfirmRequest request);

    List<TransactionEntity> getAllTransaction(String sourceAccount);
}
