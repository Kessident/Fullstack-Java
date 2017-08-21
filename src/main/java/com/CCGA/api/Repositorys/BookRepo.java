package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepo extends CrudRepository<Book, Integer> {
    Book findByIsbn(String isbn);

    Book findByName(String name);
}
