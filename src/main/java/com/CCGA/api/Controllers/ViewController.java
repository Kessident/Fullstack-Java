package com.CCGA.api.Controllers;

import com.CCGA.api.Repositorys.UserRepo;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class ViewController{

    @Autowired
    UserRepo users;


    @RequestMapping(value ="/api/home", method = RequestMethod.GET)
    public ModelAndView getHome(){
        ModelAndView homeView = new ModelAndView();
        homeView.setViewName("index");
        return homeView;
    }

    @RequestMapping(value ="/api/user/register", method = RequestMethod.GET)
    public ModelAndView getRegistration(){
        ModelAndView registrationView = new ModelAndView();
        registrationView.setViewName("registration");
        return registrationView;
    }

    @RequestMapping(value ="/api/user/signin", method = RequestMethod.GET)
    public ModelAndView getSignin(){
        ModelAndView signinView = new ModelAndView();
        signinView.setViewName("signin");
        return signinView;
    }

    @RequestMapping(value ="/api/user/profile", method = RequestMethod.GET)
    public ModelAndView getProfile(){
        ModelAndView profileView = new ModelAndView();
        profileView.setViewName("profile");
        return profileView;
    }

    @RequestMapping(value ="/api/book/owned/add", method = RequestMethod.GET)
    public ModelAndView getCreateBook(){
        ModelAndView addabookView = new ModelAndView();
        addabookView.setViewName("createABook");
        return addabookView;
    }

    @RequestMapping(value ="/api/books", method = RequestMethod.GET)
    public ModelAndView getAllBooks(){
        ModelAndView bookView = new ModelAndView();
        bookView.setViewName("books");
        return bookView;
    }

    @RequestMapping(value ="/api/user/logout", method = RequestMethod.GET)
    public ModelAndView getLogout(){
        ModelAndView logoutView = new ModelAndView();
        logoutView.setViewName("logout");
        return logoutView;
    }
}
