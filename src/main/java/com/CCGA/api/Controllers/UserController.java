package com.CCGA.api.Controllers;

import com.CCGA.api.Models.Book;
import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepo users;
    @Autowired
    SchoolRepo schools;
    @Autowired
    MajorRepo majors;
    @Autowired
    BookRepo books;


    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity registerNewUser(@RequestBody String registeringUser) {
        JsonNode json;

        try {json = new ObjectMapper().readTree(new StringReader(registeringUser));}
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
        }

        if (json == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        } else if (users.findByEmail(json.get("email").asText()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        User newUser = new User();
        newUser.setName(json.get("name").asText());
        newUser.setEmail(json.get("email").asText());
        newUser.setPassword(json.get("password").asText());
        newUser.setSchool(schools.findOne(json.get("schoolID").asInt()));
        newUser.setMajor(majors.findOne(json.get("majorID").asInt()));
        users.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity loginUser(@RequestBody String loginAttempt, HttpSession session) throws NotFoundException {
        JsonNode json;

        try {json = new ObjectMapper().readTree(new StringReader(loginAttempt));}
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
        }

        if (json == null){throw new IllegalArgumentException();}

        User exists = users.findByEmail(json.get("email").asText());

        if (exists != null && !exists.isDeleted()) {
            if (json.get("password").asText().equals(exists.getPassword())) {
                session.setAttribute("userID", exists.getUserID());
                return ResponseEntity.status(HttpStatus.OK).body("Successfully logged in");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username/password combination");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username/password combination");
        }
    }

    @PutMapping(value = "/update", consumes = {"application/json"})
    public ResponseEntity updateUser(@RequestBody String updatedUserString, HttpSession session) {
        if (session.getAttribute("userID") != null) {

            JsonNode json;
            User updatedUser;

            try {
                json = new ObjectMapper().readTree(new StringReader(updatedUserString));
                updatedUser = new ObjectMapper().treeToValue(json, User.class);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
            }

            if (updatedUser == null) {throw new IllegalArgumentException();}
            users.save(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body("User updated");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to update a user");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(HttpSession session) {
        if ( session.getAttribute("userID") != null){
            User deleted = users.findOne((int) session.getAttribute("userID"));
            deleted.setDeleted(true);
            deleted.setEmail("");
            deleted.setPassword("");
            deleted.setMajor(null);
            deleted.setSchool(null);
            deleted.setBooksOwned(null);
            deleted.setBooksForSale(null);
            users.save(deleted);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete a user");
        }
    }

    @GetMapping("/all")
    public ResponseEntity getUsers() {
        List<User> allUsers = new ArrayList<>();

        users.findAll().forEach(user -> {
            if (!user.isDeleted()){ allUsers.add(user); }
        });

        JSONResponse jsonResponse = new JSONResponse("success", allUsers);
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("/book/all")
    public JSONResponse getAllBooksOwned(HttpSession session){
        try{
            List<Book> bookList = users.findOne( (Integer) session.getAttribute("userID")).getBooksOwned();
            return new JSONResponse("Success", bookList);
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/book/add")
    public ResponseEntity addABookToCollection(@RequestBody String bookToBeAdded, HttpSession session) throws IOException {
        if (session.getAttribute("userID") != null) {
            JsonNode json;

            try {
                json = new ObjectMapper().readTree(new StringReader(bookToBeAdded));
                if (json == null) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");}
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
            }


            User loggedIn = users.findOne( (Integer) session.getAttribute("userID"));
            List<Book> bookList = loggedIn.getBooksOwned();

            Book added = books.findByName(json.get("name").asText());
            bookList.add(added);
//            bookList.add(new ObjectMapper().readValue(json.asText(), Book.class));
            loggedIn.setBooksOwned(bookList);

            users.save(loggedIn);

            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added book to collection");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to add a book to your collection");
        }
    }

    @GetMapping("/book/{bookID}")
    public JSONResponse getSpecificBook(@PathVariable int bookID, HttpSession session){
        try{
            User loggedIn = users.findOne( (int) session.getAttribute("userID"));
            Book book = books.findOne(bookID);

            return new JSONResponse("Success", loggedIn.getBooksOwned().contains(book) ? book : null);
        } catch (Exception e){
            return new JSONResponse("Error", null);
        }
    }

    @DeleteMapping("/book/{bookID}/delete")
    public ResponseEntity deleteABook(@PathVariable int bookID, HttpSession session){
        try{
            User loggedIn = users.findOne( (int) session.getAttribute("userID"));
            Book book = books.findOne(bookID);
            if (loggedIn.getBooksOwned().contains(book)){
                List<Book> booksOwned = loggedIn.getBooksOwned();
                booksOwned.remove(book);
                loggedIn.setBooksOwned(booksOwned);
                users.save(loggedIn);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book successfully removed");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have specified book in collection");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete a book from your collection");
        }
    }
}
