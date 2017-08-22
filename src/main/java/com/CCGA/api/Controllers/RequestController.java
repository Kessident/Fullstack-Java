package com.CCGA.api.Controllers;

import com.CCGA.api.Models.*;
import com.CCGA.api.Repositorys.RequestRepo;
import com.CCGA.api.Repositorys.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    RequestRepo requests;

    @Autowired
    UserRepo users;

    //post a request
    @PostMapping("/createbook")
    public void createBookRequest(@RequestBody Request request) {
        requests.save();
    }

    //see all requests
    @GetMapping("/view-all")
    public List<Request> getAllRequests(){
        List<Request> requestsList = new ArrayList<>();
        requests.findAll().forEach(requestsList::add);

        return requestsList;
    }

    //update a request by requestid
    @PostMapping("/update/{requestID}")
    public void updateBookRequest(@PathVariable int requestID){
        requests.save();
    }

    //delete a request by requestid
    @DeleteMapping("/delete/{requestID}")
    public void deleteARequest(@PathVariable int requestID) {
       requests.remove();
    }


}
