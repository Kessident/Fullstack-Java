package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Repositorys.MajorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/major")
public class MajorController {

    @Autowired
    MajorRepo majors;

    @GetMapping("/all")
    public ResponseEntity getAllMajors(){
        List<Major> majorList = new ArrayList<>();
        majors.findAll().forEach(majorList::add);

        return ResponseEntity.status(OK).body(new JSONResponse("Success", majorList));
    }

    @GetMapping("/{majorID}")
    public ResponseEntity getAMajor(@RequestParam int majorID){
        return ResponseEntity.status(OK).body(new JSONResponse("Success", majors.findOne(majorID)));
    }

    @PostMapping("/create")
    public ResponseEntity createMajor(@RequestBody Major major) {
        majors.save(major);
        return ResponseEntity.status(CREATED).build();
    }
}
