package com.CCGA.api;

import com.CCGA.api.Models.Location;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Models.School;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import com.CCGA.api.Repositorys.UserRepo;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {
    @Autowired
    UserRepo users;
    @Autowired
    SchoolRepo schools;
    @Autowired
    MajorRepo majors;
    @Autowired
    BookRepo books;
    @Autowired
    JdbcTemplate jdbc;

    @Before
    public void seedThings(){
        User user1 = new User();
        Major major1 = new Major("Biology");
        majors.save(major1);

        List<Major> majorList = new ArrayList<>();
        majorList.add(major1);

        School school1 = new School("School", new Location(1, "street", "city", "state", 11111, 1111, new Float(1), new Float(1)), majorList);
        schools.save(school1);

        user1.setName("name");
        user1.setEmail("email");
        user1.setPassword("pass");
        user1.setMajor(major1);
        user1.setSchool(school1);
        users.save(user1);
    }

    @Test
    public void tests(){
        User foundUser = users.findByEmail("email");
        School school = schools.findByName("School");
        Major major = majors.findByName("Biology");
        Assert.assertNotNull(foundUser);
        Assert.assertEquals("Name should match", "name", "name");
        Assert.assertEquals("email should match", "email", "email");
        Assert.assertEquals("pass should match", "pass", "pass");
        Assert.assertEquals(school, foundUser.getSchool());
        Assert.assertEquals(major, foundUser.getMajor());
    }
}
