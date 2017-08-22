package com.CCGA.api.Controllers;

import com.CCGA.api.Models.Book;
import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookRepo books;
    @Autowired
    UserRepo users;

    @GetMapping("/all")
    public ResponseEntity getAllBooksOwned(HttpSession session) {
        try {
            List<Book> bookList = users.findOne((Integer) session.getAttribute("userID")).getBooksOwned();
            return ResponseEntity.status(200).body(new JSONResponse("Success", bookList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching books");
        }
    }

    @PostMapping("/owned/add")
    public ResponseEntity addABookToCollection(@RequestBody String bookToBeAdded, HttpSession session) throws IOException {
        if (session.getAttribute("userID") != null) {
            JsonNode json;

            try {
                json = processJSON(bookToBeAdded);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
            }

            User loggedIn = users.findOne((Integer) session.getAttribute("userID"));
            List<Book> bookList = loggedIn.getBooksOwned();

            Book added = books.findByIsbn(json.get("isbn").asText());
            bookList.add(added);

            loggedIn.setBooksOwned(bookList);

            users.save(loggedIn);

            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added book to collection");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to add a book to your collection");
        }
    }

    @GetMapping("/owned/{bookID}")
    public JSONResponse getSpecificBook(@PathVariable int bookID, HttpSession session) {
        try {
            User loggedIn = users.findOne((int) session.getAttribute("userID"));
            Book book = books.findOne(bookID);

            return new JSONResponse("Success", loggedIn.getBooksOwned().contains(book) ? book : null);
        } catch (Exception e) {
            return new JSONResponse("Error", null);
        }
    }

    @DeleteMapping("/owned/{bookID}")
    public ResponseEntity deleteABook(@PathVariable int bookID, HttpSession session) {
        try {
            User loggedIn = users.findOne((int) session.getAttribute("userID"));
            Book book = books.findOne(bookID);
            if (loggedIn.getBooksOwned().contains(book)) {
                List<Book> booksOwned = loggedIn.getBooksOwned();
                booksOwned.remove(book);
                loggedIn.setBooksOwned(booksOwned);
                users.save(loggedIn);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book successfully removed");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have specified book in collection");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete a book from your collection");
        }
    }

    @PostMapping("/search")
    public ResponseEntity bookSearch(@RequestBody String bookSearchJSON){
        JsonNode json;

        try {
            json = processJSON(bookSearchJSON);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
        }

        if (json.get("isbn") != null){
            Book book = books.findByIsbn(json.get("isbn").asText());
            if (book == null){
                // TODO: 8/22/17 Book Search API
            } else {
                return ResponseEntity.status(200).body(new JSONResponse("Book Found", book));
            }

        } else if (json.get("name") != null){
            Book book = books.findByName(json.get("name").asText());
            if (book == null){
                // TODO: 8/22/17 Book Search API
            } else {
                return ResponseEntity.status(200).body(new JSONResponse("Book Found", book));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide an ISBN or name to search for");
    }


    private JsonNode processJSON(String toBeProcessed) throws Exception {
        JsonNode json = new ObjectMapper().readTree(new StringReader(toBeProcessed));
        if (json == null) {
            throw new Exception();
        }

        return json;
    }
}
