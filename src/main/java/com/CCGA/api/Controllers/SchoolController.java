package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.School;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Autowired
    SchoolRepo schools;
    @Autowired
    MajorRepo majors;

    @GetMapping("/all")
    public ResponseEntity getAllSchools() {
        List<School> schoolList = new ArrayList<>();
        schools.findAll().forEach(schoolList::add);

        return ResponseEntity.status(OK).body(new JSONResponse("Success", schoolList));
    }

    @GetMapping("/{ID}")
    public ResponseEntity getASchool(@PathVariable int schoolID) {
        School foundSchool = schools.findOne(schoolID);
        if (foundSchool == null){
            return ResponseEntity.status(NOT_FOUND).build();
        } else {
            return ResponseEntity.status(OK).body(new JSONResponse("Success", foundSchool));
        }
    }

    @PostMapping("/create")
    public ResponseEntity createSchool(@RequestBody School school) {
        majors.save(school.getMajorsOffered());
        schools.save(school);
        return ResponseEntity.status(CREATED).body(new JSONResponse("School created", school));
    }
}
