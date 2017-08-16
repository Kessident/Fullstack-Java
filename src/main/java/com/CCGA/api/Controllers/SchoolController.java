package com.CCGA.api.Controllers;

import com.CCGA.api.Models.School;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<School> getAllSchools(){
        List<School> schoolList = new ArrayList<>();
        schools.findAll().forEach(schoolList::add);

        return schoolList;
    }

    @GetMapping("{ID}")
    public School getASchool(@RequestParam int schoolID){
        return schools.findOne(schoolID);
    }

    @PostMapping("/create")
    public void createSchool(@RequestBody School school) {
        System.out.println(school);
        majors.save(school.getMajorsOffered());
        schools.save(school);
    }
}
