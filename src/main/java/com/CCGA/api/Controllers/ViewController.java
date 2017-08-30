package com.CCGA.api.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller

public class ViewController {

    @GetMapping("/")
    public ModelAndView getHome() {
        ModelAndView homeView = new ModelAndView();
        homeView.setViewName("index");
        return homeView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getRegistration() {
        ModelAndView registrationView = new ModelAndView();
        registrationView.setViewName("registration");
        return registrationView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogIn() {
        ModelAndView logInView = new ModelAndView();
        logInView.setViewName("login");
        return logInView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView getProfile() {
        ModelAndView profileView = new ModelAndView();
        profileView.setViewName("profile");
        return profileView;
    }

    @RequestMapping(value = "/createabook", method = RequestMethod.GET)
    public ModelAndView getCreateBook() {
        ModelAndView addabookView = new ModelAndView();
        addabookView.setViewName("createABook");
        return addabookView;
    }

    @GetMapping("/books")
    public String getBooks(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            ModelAndView getBooks = new ModelAndView();
            getBooks.setViewName("books");
            return "books";
        } else {
            return "redirect:login";
        }
    }
}
