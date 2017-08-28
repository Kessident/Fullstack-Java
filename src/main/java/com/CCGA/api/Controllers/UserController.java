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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

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
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Error processing request, please try again");
        }

        if (json == null) {
            return ResponseEntity.status(BAD_REQUEST).body("Please supply all required fields (name, email, password, majorID, schoolID)");
        } else if (json.get("email") == null) {
            return ResponseEntity.status(BAD_REQUEST).body("Please provide an email");
        } else if (users.findByEmail(json.get("email").asText()) != null) {
            return ResponseEntity.status(BAD_REQUEST).body("User already exists");
        }

        try {
            json.get("name");
            json.get("password");
            json.get("schoolID");
            json.get("majorID");
        } catch (NullPointerException e) {
            return ResponseEntity.status(BAD_REQUEST).body("Please supply all required fields (name, email, password, majorID, schoolID)");
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body("Bad Request");
        }

        List<String> errMsgs = new ArrayList<>();
        if (json.get("name").asText().isEmpty()) {
            errMsgs.add("Name must not be empty");
        } if (json.get("email").asText().isEmpty()) {
            errMsgs.add("Email must not be empty");
        } if (json.get("password").asText().isEmpty()) {
            errMsgs.add("Password must not be empty");
        } if (json.get("password").asText().length() < 8) {
            errMsgs.add("Password must be at least 8 characters");
        } if (json.get("majorID").asText().isEmpty()) {
            errMsgs.add("MajorID must not be empty");
        } if (json.get("schoolID").asText().isEmpty()) {
            errMsgs.add("SchoolID must not be empty");
        }
        Major majorExists = majors.findOne(json.get("majorID").asInt());
        School schoolExists = schools.findOne(json.get("schoolID").asInt());
        if (majorExists == null){
            errMsgs.add("Major with that ID not found");
        } if (schoolExists == null){
            errMsgs.add("School with that ID not found");
        }
        if (!errMsgs.isEmpty()) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Error(s) registering user", errMsgs));
        }



        User newUser = new User();
        newUser.setName(json.get("name").asText());
        newUser.setEmail(json.get("email").asText());
        newUser.setPassword(BCrypt.hashpw(json.get("password").asText(), BCrypt.gensalt()));
        newUser.setSchool(schools.findOne(json.get("schoolID").asInt()));
        newUser.setMajor(majors.findOne(json.get("majorID").asInt()));
        users.save(newUser);
        return ResponseEntity.status(CREATED).body(new JSONResponse("User successfully registered", null));
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity loginUser(@RequestBody String loginAttempt, HttpSession session) throws NotFoundException {
        JsonNode json;

        try {
            json = new ObjectMapper().readTree(new StringReader(loginAttempt));
        } catch (IOException e) {
            return ResponseEntity.status(BAD_REQUEST).body("Error processing request, please try again");
        }

        if (json == null) {
            return ResponseEntity.status(BAD_REQUEST).body("Please supply all required fields (name, email, password, majorID, schoolID)");
        }

        List<String> errors = new ArrayList<>();
        if (json.get("email") == null) {
            errors.add("Please provide an email");
        } if (json.get("password") == null) {
            errors.add("Please provide a password");
        }
        if (!errors.isEmpty()){
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Error(s) logging in", errors));
        }

        User exists = users.findByEmail(json.get("email").asText());

        if (exists != null && !exists.isDeleted()) {
            if (BCrypt.checkpw(json.get("password").asText(), exists.getPassword())) {
                session.setAttribute("userID", exists.getUserID());

                exists.setUpdatedAt(LocalDateTime.now());
                users.save(exists);

                return ResponseEntity.status(OK).body("Successfully logged in");
            } else {
                return ResponseEntity.status(UNAUTHORIZED).body("Invalid username/password combination");
            }
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body("Invalid username/password combination");
        }
    }

    @PutMapping(value = "/update", consumes = {"application/json"})
    public ResponseEntity updateUser(@RequestBody String updatedUserString, HttpSession session) {
        if (session.getAttribute("userID") != null) {

            JsonNode json;

            try {
                json = new ObjectMapper().readTree(new StringReader(updatedUserString));
            } catch (IOException e) {
                return ResponseEntity.status(BAD_REQUEST).body("Error processing request, please try again");
            }

            if (json == null) {
                return ResponseEntity.status(BAD_REQUEST).body("No data sent");
            }

            User tobeUpdated = users.findOne((int) session.getAttribute("userID"));
            if (json.get("name") != null) {
                tobeUpdated.setName(json.get("name").asText());
            }
            if (json.get("schoolID") != null) {
                tobeUpdated.setSchool(schools.findOne(json.get("schoolID").asInt()));
            }
            if (json.get("majorID") != null) {
                tobeUpdated.setMajor(majors.findOne(json.get("majorID").asInt()));
            }

            tobeUpdated.setUpdatedAt(LocalDateTime.now());
            users.save(tobeUpdated);
            return ResponseEntity.status(OK).body("User updated");
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body("You must be logged in to update a user");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            User deleted = users.findOne((int) session.getAttribute("userID"));
            deleted.setDeleted(true);
            deleted.setEmail(BCrypt.hashpw(deleted.getEmail(), BCrypt.gensalt()));
            deleted.setPassword("");
            deleted.setMajor(null);
            deleted.setSchool(null);
            deleted.setBooksOwned(null);
            deleted.setBooksForSale(null);
            deleted.setUpdatedAt(LocalDateTime.now());
            users.save(deleted);
            return ResponseEntity.status(NO_CONTENT).build();
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body("You must be logged in to delete a user");
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
