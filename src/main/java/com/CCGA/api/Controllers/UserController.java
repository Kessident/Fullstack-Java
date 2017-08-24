package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Models.School;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
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

        try {
            json = new ObjectMapper().readTree(new StringReader(registeringUser));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
        }

        if (json == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        } else if (users.findByEmail(json.get("email").asText()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        User newUser = new User();
        newUser.setName(json.get("name").asText());
        newUser.setEmail(json.get("email").asText());
        newUser.setPassword(BCrypt.hashpw(json.get("password").asText(), BCrypt.gensalt()));
        newUser.setSchool(schools.findOne(json.get("schoolID").asInt()));
        newUser.setMajor(majors.findOne(json.get("majorID").asInt()));
        users.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity loginUser(@RequestBody String loginAttempt, HttpSession session) throws NotFoundException {
        JsonNode json;

        try {
            json = new ObjectMapper().readTree(new StringReader(loginAttempt));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
        }

        if (json == null) {
            throw new IllegalArgumentException();
        }

        User exists = users.findByEmail(json.get("email").asText());

        if (exists != null && !exists.isDeleted()) {
            if (BCrypt.checkpw(json.get("password").asText(), exists.getPassword())) {
                session.setAttribute("userID", exists.getUserID());

                exists.setUpdatedAt(LocalDateTime.now());
                users.save(exists);

                return ResponseEntity.status(HttpStatus.OK).body("Successfully logged in");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/password combination");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/password combination");
        }
    }

    @PutMapping(value = "/update", consumes = {"application/json"})
    public ResponseEntity updateUser(@RequestBody String updatedUserString, HttpSession session) {
        if (session.getAttribute("userID") != null) {

            JsonNode json;

            try {
                json = new ObjectMapper().readTree(new StringReader(updatedUserString));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing request, please try again");
            }

            if (json == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No data sent");
            }

            User tobeUpdated = users.findOne((int) session.getAttribute("userID"));
            if (json.get("name") != null){
                tobeUpdated.setName(json.get("name").asText());
            }
            if (json.get("schoolID") != null){
                tobeUpdated.setSchool(schools.findOne(json.get("schoolID").asInt()));
            }
            if (json.get("majorID") != null){
                tobeUpdated.setMajor(majors.findOne(json.get("majorID").asInt()));
            }

            tobeUpdated.setUpdatedAt(LocalDateTime.now());
            users.save(tobeUpdated);
            return ResponseEntity.status(HttpStatus.OK).body("User updated");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to update a user");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            User deleted = users.findOne((int) session.getAttribute("userID"));
            deleted.setDeleted(true);
            deleted.setEmail("");
            deleted.setPassword("");
            deleted.setMajor(null);
            deleted.setSchool(null);
            deleted.setBooksOwned(null);
            deleted.setBooksForSale(null);
            deleted.setUpdatedAt(LocalDateTime.now());
            users.save(deleted);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete a user");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(200).body("Logged out successfully");
    }

    @GetMapping("/all")
    public ResponseEntity getUsers() {
        List<User> allUsers = new ArrayList<>();

        users.findAll().forEach(user -> {
            if (!user.isDeleted()) {
                allUsers.add(user);
            }
        });

        JSONResponse jsonResponse = new JSONResponse("success", allUsers);
        return ResponseEntity.ok(jsonResponse);
    }
}
