package com.CCGA.api;

import com.CCGA.api.Models.Book;
import com.CCGA.api.Models.Major;
import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.MajorRepo;
import com.CCGA.api.Repositorys.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTests {
    @Autowired
    BookRepo books;
    @Autowired
    MajorRepo majors;
    @Autowired
    UserRepo users;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
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

        User user = new User();
        user.setName("name");
        user.setEmail("email");
        user.setPassword("pass");
        users.save(user);
    }

    @Test
    public void bookControllerTests() {
        List<Book> bookList = new ArrayList<>();
        books.findAll().forEach(bookList::add);

        Book book1 = bookList.get(0);
        Major major = majors.findByName("CSC");

        assertEquals("book1", book1.getName());
        assertEquals("book1Author", book1.getAuthor());
        assertEquals("Book Description", book1.getDescription());
        assertEquals("path to picture", book1.getPicture());
        assertEquals(major, book1.getMajor());

        Book book2 = bookList.get(1);
        Book book3 = bookList.get(2);
        Book book4 = bookList.get(3);
        Book book5 = bookList.get(4);

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("email", "email");
        loginInfo.put("password", "pass");

        String sessionID = given().contentType(JSON).body(loginInfo).post("/api/user/login").then().extract().sessionId();

        @SuppressWarnings("unchecked") Map<String, Object> book1AsJSON = mapper.convertValue(book1, Map.class);

        given().sessionId(sessionID)
            .contentType(JSON).body(book1AsJSON)
            .post("/api/book/owned/add").then()
            .statusCode(201)
            .body(equalTo("Successfully added book to collection"));

    System.out.println("Can add book to collection\n");


        @SuppressWarnings("unchecked") Map<String, Object> book2AsJSON = mapper.convertValue(book2, Map.class);
        @SuppressWarnings("unchecked") Map<String, Object> book3AsJSON = mapper.convertValue(book3, Map.class);
        @SuppressWarnings("unchecked") Map<String, Object> book4AsJSON = mapper.convertValue(book4, Map.class);
        @SuppressWarnings("unchecked") Map<String, Object> book5AsJSON = mapper.convertValue(book5, Map.class);
        given().sessionId(sessionID).contentType(JSON).body(book2AsJSON).post("/api/book/owned/add");
        given().sessionId(sessionID).contentType(JSON).body(book3AsJSON).post("/api/book/owned/add");
        given().sessionId(sessionID).contentType(JSON).body(book4AsJSON).post("/api/book/owned/add");
        given().sessionId(sessionID).contentType(JSON).body(book5AsJSON).post("/api/book/owned/add");


        given().sessionId(sessionID)
            .get("/api/book/all").then()
            .statusCode(200)
            .body("data.name", hasItems(book1.getName(), book2.getName(), book3.getName(), book4.getName(), book5.getName()))
            .body("data.isbn", hasItems(book1.getisbn(), book2.getisbn(), book3.getisbn(), book4.getisbn(), book5.getisbn()));
    System.out.println("List of Books successfully returned\n");


        List<Book> responseBookList = given().sessionId(sessionID).get("/api/book/all").then().extract().path("data");

        book1 = mapper.convertValue(responseBookList.get(0), Book.class);
        Integer book1ID = book1.getBookID();

        given().sessionId(sessionID)
            .get("/api/book/owned/"+book1ID).then()
            .body("data.name", equalTo(book1.getName()))
            .body("data.isbn", equalTo(book1.getisbn()));
    System.out.println("Successfully returns individual book based on ID\n");


        given().sessionId(sessionID)
            .get("/api/book/243572").then()
            .body("data", equalTo(null));
    System.out.println("Book not in collection is null\n");


        given().sessionId(sessionID)
            .delete("/api/book/owned/"+book1ID).then()
            .statusCode(204);
    System.out.println("Can properly remove books\n");


        given().sessionId(sessionID).get("/api/book/owned/"+book1ID).then()
            .body("data", equalTo(null));
    System.out.println("after deleting book, should return null\n");


        Map<String, Object> bookSearch = new HashMap<>();
        bookSearch.put("isbn", "1111111111");

        given()
            .contentType(JSON).body(bookSearch)
            .post("/api/book/search").then()
            .statusCode(200)
            .body("data.name", equalTo(book1.getName()))
            .body("data.isbn", equalTo(book1.getisbn()));
    System.out.println("Can find book by ISBN\n");


        bookSearch.clear();
        bookSearch.put("name", "book1");
        given()
            .contentType(JSON).body(bookSearch)
            .post("/api/book/search").then()
            .statusCode(200)
            .body("data.name", equalTo(book1.getName()))
            .body("data.isbn", equalTo(book1.getisbn()));
    System.out.println("Can find book by exact name\n");


        bookSearch.clear();
        given()
            .contentType(JSON).body(bookSearch)
            .post("/api/book/search").then()
            .statusCode(400)
            .body(equalTo("Please provide an ISBN or name to search for"));
    System.out.println("Bad Request if no ISBN or name");


        get("/api/book/search/1111111111").then()
            .body("data.name", equalTo(book1.getName()))
            .body("data.isbn", equalTo(book1.getisbn()));
    System.out.println("Can search by ISBN from URL");
    }
}
