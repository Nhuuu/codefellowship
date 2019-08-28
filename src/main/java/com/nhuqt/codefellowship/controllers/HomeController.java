package com.nhuqt.codefellowship.controllers;

import com.nhuqt.codefellowship.models.ApplicationUser;
import com.nhuqt.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

  @Autowired
  ApplicationUserRepository applicationUserRepository;

  @GetMapping("/")
  public String getRoot(Principal p, Model m){
    ApplicationUser applicationUser = null;
    if(p != null){
      applicationUser = applicationUserRepository.findByUsername(p.getName());
      m.addAttribute("user", applicationUser);
      return "myprofile";
    }
    return "home";
  }

  @GetMapping("/signup")
  public String getSignupPage(){
    return "signup";
  }

  @GetMapping("/login")
  public String getLoginPage(){
    return "login";
  }


}
