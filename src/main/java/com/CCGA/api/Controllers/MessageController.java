package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Message;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.MessageRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController("/api/message")
public class MessageController {

    @Autowired
    MessageRepo messages;
    @Autowired
    UserRepo users;

    @GetMapping("{userID}/all")
    public JSONResponse getAllMessagesFromUser(@PathVariable int userID, HttpSession session){
        User loggedIn = users.findOne((int)session.getAttribute("UserID"));
        User messagesAbout = users.findOne(userID);
        List<Message> allMessages = messages.findAllBySentFromAndSentTo(loggedIn, messagesAbout);
        return new JSONResponse("Success", allMessages);
    }

    @PostMapping("{userID}/create")
    public ResponseEntity createNewMessage(@PathVariable int userID, @RequestBody String messageString, HttpSession session){
        JsonNode messageJSON;

        try {
            messageJSON = new ObjectMapper().readTree(messageString);
            if (messageJSON == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JSONResponse("Error reading message", null));
            }
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new JSONResponse("Error reading message", null));
        }

        Message newMessage = new Message();
        newMessage.setMessage(messageJSON.get("message").asText());
        newMessage.setSentFrom(users.findOne( (int) session.getAttribute("userID")));
        newMessage.setSentTo(users.findOne(userID));

        messages.save(newMessage);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new JSONResponse("success", newMessage));
    }

    @GetMapping("/contacts")
    public ResponseEntity getAllContacts(HttpSession session){
        User loggedIn = users.findOne( (int) session.getAttribute("userID"));

        List<Message> allMessages = messages.findAllBySentFrom(loggedIn);
        List<User> allContacts = new ArrayList<>();

        allMessages.forEach(msg -> allContacts.add(msg.getSentTo()));

        return ResponseEntity.status(HttpStatus.OK)
            .body(new JSONResponse("success", allContacts));
    }


}
