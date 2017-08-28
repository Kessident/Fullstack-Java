package com.CCGA.api.Controllers;

import com.CCGA.api.Models.Book;
import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Request;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.RequestRepo;
import com.CCGA.api.Repositorys.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    RequestRepo requests;
    @Autowired
    UserRepo users;
    @Autowired
    BookRepo books;

    //Get a list of all requests
    @GetMapping("/all")
    public ResponseEntity getAllRequests() {
        List<Request> requestsList = new ArrayList<>();
        requests.findAll().forEach(requestsList::add);
        if (requestsList.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("No requests");
        } else {
            return ResponseEntity.status(OK).body(new JSONResponse("Success", requestsList));
        }
    }

    //Get a specific request
    @GetMapping("/{requestID}")
    public ResponseEntity getASpecificRequest(@PathVariable int requestID) {
        Request foundRequest = requests.findOne(requestID);
        if (foundRequest != null) {
            return ResponseEntity.status(OK).body(new JSONResponse("Success", foundRequest));
        } else {
            return ResponseEntity.status(NOT_FOUND).body("Request with that ID not found");
        }
    }

    //Get a list of all requests a logged in user made
    @GetMapping("/mine/all")
    public ResponseEntity getAllRequestMadeByLoggedIn(HttpSession session) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        List<Request> requestListByUser = new ArrayList<>();

        requestListByUser.addAll(requests.findAllByUserRequestedEquals(loggedIn));
        if (requestListByUser.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("No requests");
        } else {
            return ResponseEntity.status(OK).body(new JSONResponse("Success", requestListByUser));
        }
    }

    //Get a specific request a logged in user made
    @GetMapping("/mine/{requestID}")
    public ResponseEntity getASpecificRequestByLoggedIn(@PathVariable int requestID, HttpSession session) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        Request foundRequest = requests.findByRequestIDAndUserRequested(requestID, loggedIn);

        if (foundRequest != null) {
            return ResponseEntity.status(OK).body(new JSONResponse("Success", foundRequest));
        } else {
            return ResponseEntity.status(NOT_FOUND).body("Request with that ID not found by logged in user");
        }
    }

    //Create a new request for a book from logged in user
    @PostMapping("/create")
    public ResponseEntity createBookRequest(@RequestBody int bookID, HttpSession session) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        Book requested = books.findOne(bookID);
        Request newRequest = new Request(requested, loggedIn);
        requests.save(newRequest);
        return ResponseEntity.status(CREATED).body(new JSONResponse("Request created", newRequest));
    }

    //Delete a request a logged in user made
    @DeleteMapping("/delete/{requestID}")
    public ResponseEntity deleteARequest(@PathVariable int requestID, HttpSession session) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        Request foundRequest = requests.findByRequestIDAndUserRequested(requestID, loggedIn);

        if (foundRequest != null) {
            requests.delete(foundRequest);
            return ResponseEntity.status(NO_CONTENT).build();
        } else {
            return ResponseEntity.status(NOT_FOUND).body("Request with that ID not found by logged in user");
        }
    }


}
