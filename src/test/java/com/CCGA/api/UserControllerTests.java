package com.CCGA.api;

import com.CCGA.api.Models.Location;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Models.School;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.SchoolRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    @DirtiesContext
    public void userControllerTests() {
        setUp();

        School school = schools.findByName("School");
        Major major = majors.findByName("Biology");

        Map<String, Object> userAsJSON = new HashMap<>();

        userAsJSON.put("name", "name");
        userAsJSON.put("email", "email");
        userAsJSON.put("password", "pass");
        userAsJSON.put("majorID", major.getMajorID());
        userAsJSON.put("schoolID", school.getSchoolID());


        given().contentType(JSON).body(userAsJSON).post("/api/user/register");
    System.out.println("User can register");

        User foundUser = users.findByEmail("email");

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

        String sessionID = given().contentType(JSON).body(loginInfo)
            .post("/api/user/login").then()
            .statusCode(200)
            .extract().sessionId();

        System.out.println("\nCan login\n");

        given().sessionId(sessionID)
            .delete("/api/user/delete").then()
            .statusCode(204);

        System.out.println("\nCan delete self\n");

        given().sessionId(sessionID).post("/api/user/logout");

        System.out.println("\nCan logout\n");

        given().contentType(JSON).body(loginInfo)
            .post("/api/user/login").then()
            .statusCode(401)
            .body(equalTo("Invalid username/password combination"));

        System.out.println("\nShould not be able to login after deleting self\n");
    }

    private void setUp() {
        Map<String, Object> majorAsJSON = new HashMap<>();
        majorAsJSON.put("name", "Biology");
        given().contentType(JSON).body(majorAsJSON).post("/api/major/create");

        School school1 = new School("School", new Location(1, "street", "city", "state", 11111, 1111, new Float(1), new Float(1)), null);
        Map<String, Object> schoolAsJSON = new ObjectMapper().convertValue(school1, Map.class);
        given().contentType(JSON).body(schoolAsJSON).post("/api/school/create");
    }
}
