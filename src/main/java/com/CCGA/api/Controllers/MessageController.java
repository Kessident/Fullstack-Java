package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Message;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.MessageRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController

@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageRepo messages;
    @Autowired
    UserRepo users;

    @GetMapping("/{userID}/all")
    public ResponseEntity getAllMessagesFromUser(@PathVariable int userID, HttpSession session) {
        User loggedIn = users.findOne((int) session.getAttribute("UserID"));
        User sentTo = users.findOne(userID);
        List<Message> allMessages = messages.findAllBySentFromAndSentTo(loggedIn, sentTo);
        return ResponseEntity.status(OK).body(new JSONResponse("Success", allMessages));
    }

    @PostMapping(value = "/{userID}/create", consumes = "application/json")
    public ResponseEntity createNewMessage(@PathVariable int userID, @RequestBody String messageString, HttpSession session) {
        JsonNode messageJSON;

        try {
            messageJSON = new ObjectMapper().readTree(new StringReader(messageString));
            if (messageJSON == null) {
                return ResponseEntity.status(BAD_REQUEST)
                    .body(new JSONResponse("Error reading message", null));
            }
        } catch (IOException ex) {
            return ResponseEntity.status(BAD_REQUEST)
                .body(new JSONResponse("Error reading message", null));
        }

        Message newMessage = new Message();
        newMessage.setMessage(messageJSON.get("message").asText());
        newMessage.setSentFrom(users.findOne((int) session.getAttribute("userID")));
        newMessage.setSentTo(users.findOne(userID));

        messages.save(newMessage);

        return ResponseEntity.status(CREATED)
            .body(new JSONResponse("success", newMessage));
    }

    @PostMapping("/{userID}/create")
    public ResponseEntity createNewMessageMediaNotSupported(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(new JSONResponse("Content-Type not supported, please use \"application/json\" or \"application/x-www-form-urlencoded\"", null));
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to do that", null));
        }
    }

    @GetMapping("/contacts")
    public ResponseEntity getAllContacts(HttpSession session) {
        if (session.getAttribute("userID") != null) {

            User loggedIn = users.findOne((int) session.getAttribute("userID"));

            List<Message> allMessages = messages.findAllBySentFrom(loggedIn);
            List<User> allContacts = new ArrayList<>();

            allMessages.forEach(msg -> allContacts.add(msg.getSentTo()));

            return ResponseEntity.status(OK)
                .body(new JSONResponse("success", allContacts));
        } else {
            return ResponseEntity.status(UNAUTHORIZED).body(new JSONResponse("You must be logged in to do that", null));
        }
    }


}
