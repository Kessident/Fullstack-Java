package com.CCGA.api.Controllers;

import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity registerNewUser(@RequestBody String registeringUser) {
        JsonNode json = null;

        try {json = new ObjectMapper().readTree(new StringReader(registeringUser));}
        catch (IOException e) {e.printStackTrace();}

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
        JsonNode json = null;

        try {json = new ObjectMapper().readTree(new StringReader(loginAttempt));}
        catch (IOException e) {e.printStackTrace();}

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
            User updatedUser = null;

            try {
                json = new ObjectMapper().readTree(new StringReader(updatedUserString));
                updatedUser = new ObjectMapper().treeToValue(json, User.class);
            } catch (IOException e) {e.printStackTrace();}

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
    public List<User> getUsers() {
        List<User> allUsers = new ArrayList<>();
        users.findAll().forEach(user -> {
            if (!user.isDeleted()){ allUsers.add(user); }
        });
        return allUsers;
    }
}
