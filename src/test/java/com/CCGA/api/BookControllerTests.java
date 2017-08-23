package com.CCGA.api;

import com.CCGA.api.Models.Book;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.UserRepo;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTests {
    private String sessionID;
    @Autowired
    BookRepo books;
    @Autowired
    MajorRepo majors;
    @Autowired
    UserRepo users;

    public void setUp() {
        Major major = new Major("CSC");
        majors.save(major);

        Book book1 = new Book("book1", "book1Author", "1111111111", "Book Description", "path to picture", major);
        Book book2 = new Book("book2", "book2Author", "2222222222", "Book Description", "path to picture", major);
        Book book3 = new Book("book3", "book3Author", "3333333333", "Book Description", "path to picture", major);
        Book book4 = new Book("book4", "book4Author", "4444444444", "Book Description", "path to picture", major);
        Book book5 = new Book("book5", "book5Author", "5555555555", "Book Description", "path to picture", major);
        books.save(book1);
        books.save(book2);
        books.save(book3);
        books.save(book4);
        books.save(book5);
    }
    public void userLogIn(){
        User user = new User();
        user.setName("name");
        user.setEmail("email");
        user.setPassword("pass");
        users.save(user);

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("email", "email");
        loginInfo.put("password", "pass");

        sessionID = given().contentType(ContentType.JSON).body(loginInfo)
            .post("api.user.login")
            .then()
            .extract().sessionId();
    }

    @Test
    public void testSaveFind(){
        setUp();
        Book firstBook = books.findByIsbn("1111111111");
        Major major = majors.findByName("CSC");

        assertEquals("book1", firstBook.getName());
        assertEquals("book1Author", firstBook.getAuthor());
        assertEquals("Book Description", firstBook.getDescription());
        assertEquals("path to picture", firstBook.getPicture());
        assertEquals(major, firstBook.getMajor());
    }
}
