package com.CCGA.api;

import com.CCGA.api.Models.*;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import com.CCGA.api.Repositorys.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
//    @Rollback
    public void setUp() {
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
    public void userControllerTests() {
        User foundUser = users.findByEmail("email");
        School school = schools.findByName("School");
        Major major = majors.findByName("Biology");
        assertNotNull(foundUser);
        assertEquals("Name should match", "name", "name");
        assertEquals("email should match", "email", "email");
        assertEquals("pass should match", "pass", "pass");
        assertEquals(school, foundUser.getSchool());
        assertEquals(major, foundUser.getMajor());


        get("/api/user/all").then().body("message", equalTo("success"));


        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("email", "email");
        loginInfo.put("password", "pass");

        String sessionID = given()
            .contentType(JSON)
            .body(loginInfo)
            .when()
            .post("/api/user/login")
            .then()
            .statusCode(200)
        .extract().sessionId();

        System.out.println("\nCan login\n");

        given()
            .sessionId(sessionID)
            .when()
            .delete("/api/user/delete")
            .then()
            .statusCode(204);

        System.out.println("\nCan delete self\n");

        given().sessionId(sessionID).post("/api/user/logout");

        System.out.println("\nCan logout\n");

        given()
            .contentType(JSON).body(loginInfo)
            .post("/api/user/login").then()
            .statusCode(401)
            .body(equalTo("Invalid username/password combination"));

        System.out.println("\nShould not be able to login after deleting self\n");
    }
}
