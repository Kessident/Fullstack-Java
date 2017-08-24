package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Listing;
import com.CCGA.api.Repositorys.ListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/listing")
public class ListingController {

    @Autowired
    ListingRepo listings;

    @GetMapping("/booksforsale")
    public JSONResponse getAllBooksForSale() {
        try {
            Iterable<Listing> bookList = listings.findAll();
            return new JSONResponse("Success", bookList);
        } catch (Exception e) {
            return null;
        }
    }
}

