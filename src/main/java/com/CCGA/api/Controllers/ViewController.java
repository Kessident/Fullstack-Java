package com.CCGA.api.Controllers;

import com.CCGA.api.Repositorys.UserRepo;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class ViewController{


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

    @RequestMapping(value ="/api/user/createabook", method = RequestMethod.GET)
    public ModelAndView getCreateBook(){
        ModelAndView addabookView = new ModelAndView();
        addabookView.setViewName("createABook");
        return addabookView;
    }
}
