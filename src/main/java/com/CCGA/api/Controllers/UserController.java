package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Models.School;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private UserRepo users;
    private SchoolRepo schools;
    private MajorRepo majors;

    @Autowired
    public UserController(UserRepo users, SchoolRepo schools, MajorRepo majors) {
        this.users = users;
        this.schools = schools;
        this.majors = majors;
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity registerNewUser(@RequestBody String registeringUser) {
        JsonNode json;

        try {
            json = new ObjectMapper().readTree(new StringReader(registeringUser));
        } catch (IOException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new JSONResponse("Error processing request, please try again", null));
        }

        if (!(json.has("name") && json.has("password") && json.has("passwordConfirm") && json.has("schoolID") && json.has("majorID"))) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Please supply all required fields (name, email, password, majorID, schoolID)", null));
        }

        List<String> errors = checkRegistrationErrors(json);

        if (!errors.isEmpty()) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Error(s) registering user", errors));
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

    @PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity registerNewUserFormData(String name, String email, String password, String passwordConfirm, Integer majorID, Integer schoolID) {
        if ((name == null || name.isEmpty()) || (email == null || email.isEmpty()) || (password == null || password.isEmpty()) || (passwordConfirm == null || passwordConfirm.isEmpty()) || (majorID
            == null) || (schoolID == null)) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Please supply all required fields (name, email, password, passwordConfirm, majorID, schoolID)", null));
        } else if (password.length() < 8) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Password must be at least 8 characters", null));
        } else if (!password.equals(passwordConfirm)) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Passwords must match", null));
        }

        Major major = majors.findOne(majorID);
        if (major == null) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("No major with that ID found", null));
        }
        School school = schools.findOne(schoolID);
        if (school == null) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("No school with that ID found", null));
        }
        User doesExist = users.findByEmail(email);
        if (doesExist != null) {
            return ResponseEntity.status(CONFLICT).body(new JSONResponse("User already exists", null));
        }

        User registeringUser = new User();
        registeringUser.setName(name);
        registeringUser.setEmail(email);
        registeringUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        registeringUser.setSchool(school);
        registeringUser.setMajor(major);
        users.save(registeringUser);
        return ResponseEntity.status(CREATED).body(new JSONResponse("User successfully registered", registeringUser));
    }

    @PostMapping("/register")
    public ResponseEntity registerMediaNotSupported() {
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(new JSONResponse("Content-Type not supported, please use \"application/json\" or \"application/x-www-form-urlencoded\"", null));
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity loginUser(@RequestBody String loginAttempt, HttpSession session) {
        JsonNode json;

        try {
            json = new ObjectMapper().readTree(new StringReader(loginAttempt));
        } catch (IOException e) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Error processing request, please try again", null));
        }

        List<String> errors = new ArrayList<>();
        if (json.get("email") == null) {
            errors.add("Please provide an email");
        }
        if (json.get("password") == null) {
            errors.add("Please provide a password");
        }
        if (!errors.isEmpty()) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Error(s) logging in", errors));
        }

        User exists = users.findByEmail(json.get("email").asText());

        if (exists != null && !exists.isDeleted()) {
            if (BCrypt.checkpw(json.get("password").asText(), exists.getPassword())) {
                session.setAttribute("userID", exists.getUserID());

                exists.setUpdatedAt(LocalDateTime.now());
                users.save(exists);

                return ResponseEntity.status(OK).body(new JSONResponse("Successfully logged in", null));
            } else {
                return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("Invalid username/password combination", null));
            }
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("Invalid username/password combination", null));
        }
    }

    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ResponseEntity loginUserFormData(String email, String password, HttpSession session) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Please provide both an email and a password", null));
        }

        User loginAttempt = users.findByEmail(email);

        if (loginAttempt == null || loginAttempt.isDeleted() || !BCrypt.checkpw(password, loginAttempt.getPassword())) {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("Invalid username/password combination", null));
        } else {
            session.setAttribute("userID", loginAttempt.getUserID());

            loginAttempt.setUpdatedAt(LocalDateTime.now());
            users.save(loginAttempt);

            return ResponseEntity.status(OK).body(new JSONResponse("Successfully logged in", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity loginMediaNotSupported() {
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(new JSONResponse("Content-Type not supported, please use \"application/json\" or \"application/x-www-form-urlencoded\"", null));
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody String updatedUserString, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            JsonNode json;

            try {
                json = new ObjectMapper().readTree(new StringReader(updatedUserString));
            } catch (IOException e) {
                return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("Error processing request, please try again", null));
            }

            if (json == null) {
                return ResponseEntity.status(BAD_REQUEST).body(new JSONResponse("No data sent", null));
            }

            User tobeUpdated = users.findOne((int) session.getAttribute("userID"));
            if (json.get("name") != null) {
                tobeUpdated.setName(json.get("name").asText());
            }
            if (json.get("schoolID") != null) {
                School schoolUpdate = schools.findOne(json.get("schoolID").asInt());
                if (schoolUpdate != null) {
                    tobeUpdated.setSchool(schoolUpdate);
                }
            }
            if (json.get("majorID") != null) {
                Major majorUpdate = majors.findOne(json.get("majorID").asInt());
                if (majorUpdate != null) {
                    tobeUpdated.setMajor(majorUpdate);
                }
            }

            tobeUpdated.setUpdatedAt(LocalDateTime.now());
            users.save(tobeUpdated);
            return ResponseEntity.status(OK).body(new JSONResponse("User updated", null));
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to update a user", null));
        }
    }

    @PutMapping(value = "/update", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ResponseEntity updateUserFormData(String name, Integer schoolID, Integer majorID, HttpSession session) {
        if (session.getAttribute("userID") != null) {
            User tobeUpdated = users.findOne((int) session.getAttribute("userID"));
            if (name != null) {
                tobeUpdated.setName(name);
            }
            School schoolUpdate = schools.findOne(schoolID);
            if (schoolUpdate != null) {
                tobeUpdated.setSchool(schoolUpdate);
            }
            Major majorUpdate = majors.findOne(majorID);
            if (majorUpdate != null) {
                tobeUpdated.setMajor(majorUpdate);
            }

            tobeUpdated.setUpdatedAt(LocalDateTime.now());
            users.save(tobeUpdated);
            return ResponseEntity.status(OK).body(new JSONResponse("User updated", null));

        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to update a user", null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateMediaNotSupported() {
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(new JSONResponse("Content-Type not supported, please use \"application/json\" or \"application/x-www-form-urlencoded\"", null));
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
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to delete a user", null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(OK).body(new JSONResponse("Logged out successfully", null));
    }

    @GetMapping("/all")
    public ResponseEntity getUsers() {
        List<User> allUsers = new ArrayList<>();

        users.findAll().forEach(user -> {
            if (!user.isDeleted()) {
                allUsers.add(user);
            }
        });

        return ResponseEntity.status(OK).body(new JSONResponse("success", allUsers));
    }

    private List<String> checkRegistrationErrors(JsonNode json) {
        List<String> errors = new ArrayList<>();
        if (json.get("name").asText().isEmpty()) {
            errors.add("Name must not be empty");
        }
        if (json.get("email").asText().isEmpty()) {
            errors.add("Email must not be empty");
        }
        if (json.get("password").asText().isEmpty()) {
            errors.add("Password must not be empty");
        }
        if (!json.get("passwordConfirm").asText().equals(json.get("password").asText())) {
            errors.add("Password and password Confirm must be the same");
        }
        if (json.get("password").asText().length() < 8) {
            errors.add("Password must be at least 8 characters");
        }
        if (json.get("majorID").asText().isEmpty()) {
            errors.add("MajorID must not be empty");
        }
        if (json.get("schoolID").asText().isEmpty()) {
            errors.add("SchoolID must not be empty");
        }
        Major majorExists = majors.findOne(json.get("majorID").asInt());
        School schoolExists = schools.findOne(json.get("schoolID").asInt());
        if (majorExists == null) {
            errors.add("Major with that ID not found");
        }
        if (schoolExists == null) {
            errors.add("School with that ID not found");
        }
        if (users.findByEmail(json.get("email").asText()) != null) {
            errors.add("User already exists");
        }

        return errors;
    }
}
