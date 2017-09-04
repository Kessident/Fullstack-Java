package com.CCGA.api.Controllers;

import com.CCGA.api.Models.User;
import com.CCGA.api.Repositorys.BookRepo;
import com.CCGA.api.Repositorys.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller

public class ViewController {

    @Autowired
    UserRepo users;

    @Autowired
    BookRepo books;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome() {
        ModelAndView homeView = new ModelAndView();
        homeView.setViewName("index");
        return homeView;
    }

    @RequestMapping(value = "/api/user/register", method = RequestMethod.GET)
    public String getRegistration(HttpSession session) {
        return "registration";
    }

    @RequestMapping(value = "/api/user/login", method = RequestMethod.GET)
    public String getLogIn(HttpSession session) {
        return "login";
    }

    @RequestMapping(value = "/api/user/profile", method = RequestMethod.GET)
    public ModelAndView getProfile(HttpSession session) {
        ModelAndView profileView = new ModelAndView();
        profileView.setViewName("profile");
        return profileView;
    }

    @RequestMapping(value = "/api/book/create", method = RequestMethod.GET)
    public ModelAndView getCreateBook() {
        ModelAndView addabookView = new ModelAndView();
        addabookView.setViewName("createAListing");
        return addabookView;
    }

    @RequestMapping(value = "/api/request/create", method = RequestMethod.GET)
    public ModelAndView getRequestBook() {
        ModelAndView requestBookView = new ModelAndView();
        requestBookView.setViewName("createARequest");
        return requestBookView;
    }

    @RequestMapping(value = "/api/user/logout", method = RequestMethod.GET)
    public ModelAndView getLogout() {
        ModelAndView logoutView = new ModelAndView();
        logoutView.setViewName("logout");
        return logoutView;
    }

    @RequestMapping(value = "/api/book/search", method = RequestMethod.GET)
    public String getBooks(HttpSession session) {
        if (session.getAttribute("userID") != null) {
            return "books";
        } else return "redirect:login";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView getContact() {
        ModelAndView contactView = new ModelAndView();
        contactView.setViewName("contact");
        return contactView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getUserProfile(HttpSession session, Model model) {
        if (session.getAttribute("userID") != null) {
            User user = users.findOne((int) session.getAttribute("userID"));
            model.addAttribute("user", user);
            return "profile";
        } else return "redirect:login";

    }
}
