package com.CCGA.api.Repositorys;

import com.CCGA.api.Models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Integer> {
    Book findByIsbn(String isbn);
    List<Book> findAllByAuthor(String author);
    Book findByName(String name);

    void save(MultipartFile store);
}
