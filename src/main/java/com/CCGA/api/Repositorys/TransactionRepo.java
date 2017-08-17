package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepo extends CrudRepository<Transaction, Integer> {
}
