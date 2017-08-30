package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Repositorys.MajorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/major")
public class MajorController {

    @Autowired
    MajorRepo majors;

    @GetMapping("/all")
    public ResponseEntity getAllMajors() {
        List<Major> majorList = new ArrayList<>();
        majors.findAll().forEach(majorList::add);

        return ResponseEntity.status(OK).body(new JSONResponse("Success", majorList));
    }

    @GetMapping("/{majorID}")
    public ResponseEntity getAMajor(@PathVariable int majorID) {
        Major foundMajor = majors.findOne(majorID);
        if (foundMajor == null) {
            return ResponseEntity.status(NOT_FOUND).body(new JSONResponse("major with that ID not found", null));
        } else {
            return ResponseEntity.status(OK).body(new JSONResponse("Success", foundMajor));
        }
    }

    @PostMapping("/create")
    public ResponseEntity createMajor(@RequestBody Major major) {
        majors.save(major);
        return ResponseEntity.status(CREATED).body(new JSONResponse("major created", major));
    }
}
