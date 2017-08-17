package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Listing;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepo extends CrudRepository<Listing, Integer> {
}
