package com.CCGA.api.Controllers;

import com.CCGA.api.Models.Book;
import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Transaction;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.TransactionRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionRepo transactions;
    @Autowired
    UserRepo users;
    @Autowired
    BookRepo books;

    @GetMapping("/all")
    public ResponseEntity getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        transactions.findAll().forEach(allTransactions::add);
        return ResponseEntity.status(OK).body(new JSONResponse("success", allTransactions));
    }

    @GetMapping("/{transID}")
    public ResponseEntity getSpecificTransaction(@PathVariable int transID) {
        Transaction transaction = transactions.findOne(transID);
        return ResponseEntity.status(OK).body(new JSONResponse("success", transaction));
    }

    @PostMapping("/create")
    public ResponseEntity createNewTransaction(@RequestBody String transactionAsString) {
        JsonNode transactionAsJson;

        try {
            transactionAsJson = new ObjectMapper().readTree(new StringReader(transactionAsString));
            if (transactionAsJson == null) {
                throw new IOException();
            }
        } catch (IOException ex) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new JSONResponse("Error", "Error Processing JSON request"));
        }

        Transaction newTrans = new Transaction();

        try {
            User seller = users.findOne(transactionAsJson.get("sellerID").asInt());
            User buyer = users.findOne(transactionAsJson.get("buyerID").asInt());
            Book bookSold = books.findOne(transactionAsJson.get("bookSold").asInt());
            newTrans.setSeller(seller);
            newTrans.setBuyer(buyer);
            newTrans.setBookSold(bookSold);
            newTrans.setAmountSoldFor(transactionAsJson.get("amountSoldFor").asLong());
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Error", "Error reading properties from JSON, Ensure all fields are properly spelled/present and try again"));
        }

        transactions.save(newTrans);

        return ResponseEntity.status(CREATED).body(new JSONResponse("success", newTrans));
    }

}
