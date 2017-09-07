package com.CCGA.api;

import com.CCGA.api.Models.*;
import com.CCGA.api.Repositorys.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class BackendApiApplication {
    private static final Logger log = LoggerFactory.getLogger(BackendApiApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(BackendApiApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner seedDB(MajorRepo majors, BookRepo books, SchoolRepo schools, UserRepo users, ListingRepo listings, RequestRepo requests, TransactionRepo transactions) {
//        return (args) -> {
//            Major major1 = new Major("major1");
//            Major major2 = new Major("major2");
//            Major major3 = new Major("major3");
//            Major major4 = new Major("major4");
//            Major major5 = new Major("major5");
//            majors.save(major1);
//            majors.save(major2);
//            majors.save(major3);
//            majors.save(major4);
//            majors.save(major5);
//
//            Book book1 = new Book("name1", "author", "1111111111", null, null, major1);
//            Book book2 = new Book("name2", "author", "2222222222", null, null, major2);
//            Book book3 = new Book("name3", "author", "3333333333", null, null, major3);
//            Book book4 = new Book("name4", "author", "4444444444", null, null, major4);
//            Book book5 = new Book("name5", "author", "5555555555", null, null, major5);
//            Book book6 = new Book("name6", "author", "1111111111", null, null, major1);
//            Book book7 = new Book("name7", "author", "2222222222", null, null, major2);
//            Book book8 = new Book("name8", "author", "3333333333", null, null, major3);
//            Book book9 = new Book("name9", "author", "4444444444", null, null, major4);
//            Book book0 = new Book("name0", "author", "5555555555", null, null, major5);
//            books.save(book1);
//            books.save(book2);
//            books.save(book3);
//            books.save(book4);
//            books.save(book5);
//            books.save(book6);
//            books.save(book7);
//            books.save(book8);
//            books.save(book9);
//            books.save(book0);
//
//            School school1 = new School("school1");
//            school1.addMajor(major1);
//            school1.addMajor(major2);
//            School school2 = new School("school2");
//            school2.addMajor(major2);
//            school2.addMajor(major3);
//            School school3 = new School("school3");
//            school3.addMajor(major3);
//            school3.addMajor(major4);
//            School school4 = new School("school4");
//            school4.addMajor(major4);
//            school4.addMajor(major5);
//            School school5 = new School("school5");
//            school5.addMajor(major5);
//            school5.addMajor(major1);
//            schools.save(school1);
//            schools.save(school2);
//            schools.save(school3);
//            schools.save(school4);
//            schools.save(school5);
//
//            User user1 = new User("name1", "1@1.1", BCrypt.hashpw("pass", BCrypt.gensalt()), major1, school1);
//            User user2 = new User("name2", "2@2.2", BCrypt.hashpw("pass", BCrypt.gensalt()), major2, school2);
//            User user3 = new User("name3", "3@3.3", BCrypt.hashpw("pass", BCrypt.gensalt()), major3, school3);
//            User user4 = new User("name4", "4@4.4", BCrypt.hashpw("pass", BCrypt.gensalt()), major4, school4);
//            User user5 = new User("name5", "5@5.5", BCrypt.hashpw("pass", BCrypt.gensalt()), major5, school5);
//            user1.addBook(book1);
//            user1.addBook(book2);
//            users.save(user1);
//            user2.addBook(book2);
//            user2.addBook(book3);
//            users.save(user2);
//            user3.addBook(book3);
//            user3.addBook(book4);
//            users.save(user3);
//            user4.addBook(book4);
//            user4.addBook(book5);
//            users.save(user4);
//            user5.addBook(book5);
//            user5.addBook(book1);
//            users.save(user5);
//
//            Transaction trans1 = new Transaction(user1, user2, book1, 80);
//            Transaction trans2 = new Transaction(user1, user2, book1, 80);
//            Transaction trans3 = new Transaction(user1, user2, book1, 80);
//            Transaction trans4 = new Transaction(user1, user2, book1, 80);
//            Transaction trans5 = new Transaction(user1, user2, book1, 80);
//            transactions.save(trans1);
//            transactions.save(trans2);
//            transactions.save(trans3);
//            transactions.save(trans4);
//            transactions.save(trans5);
//
//            Request req1 = new Request(book1, user1);
//            Request req2 = new Request(book1, user1);
//            requests.save(req1);
//            requests.save(req2);
//
////            System.out.println(foundUser.getBooksOwned());
//
//            // save a couple of customers
////            User user2 = new User("name", "2@email.com", "pass", null, null, false, null, null);
////            User user3 = new User("name", "3@email.com", "pass", null, null, false, null, null);
////            User user4 = new User("name", "4@email.com", "pass", null, null, false, null, null);
////            User user5 = new User("name", "5@email.com", "pass", null, null, false, null, null);
////            users.save(user2);
////            users.save(user3);
////            users.save(user4);
////            users.save(user5);
////
////            // fetch all customers
////            log.info("Users found with findAll():");
////            log.info("-------------------------------");
////            for (User customer : users.findAll()) {
////                log.info(customer.toString());
////            }
////            log.info("");
////
////            // fetch an individual customer by ID
////            User customer = users.findOne(1);
////            log.info("User found with findOne(1):");
////            log.info("--------------------------------");
////            log.info(customer.toString());
////            log.info("");
////
////            // fetch customers by last name
////            log.info("User found with findAll():");
////            log.info("--------------------------------------------");
////            for (User bauer : users.findAll()) {
////                log.info(bauer.toString());
////            }
////            log.info("");
//        };
//    }}
