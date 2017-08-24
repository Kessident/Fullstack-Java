package com.CCGA.api.Controllers;

import com.CCGA.api.Models.JSONResponse;
import com.CCGA.api.Models.School;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        return ResponseEntity.status(HttpStatus.OK).body(new JSONResponse("Success", schoolList));
    }

    @GetMapping("{ID}")
    public ResponseEntity getASchool(@RequestParam int schoolID) {
        return ResponseEntity.status(HttpStatus.OK).body(new JSONResponse("Success", schools.findOne(schoolID)));
    }

    @PostMapping("/create")
    public ResponseEntity createSchool(@RequestBody School school) {
        majors.save(school.getMajorsOffered());
        schools.save(school);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
