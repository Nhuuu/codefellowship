package com.nhuqt.codefellowship.controllers;

import com.nhuqt.codefellowship.models.ApplicationUser;
import com.nhuqt.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.util.ArrayList;

@Controller
public class ApplicationUserController {
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  ApplicationUserRepository applicationUserRepository;

  @PostMapping("/users")
  public RedirectView createUser(String username, String password, String firstName, String lastName, Date dateOfBirth,
                                 String bio){
    ApplicationUser newUser = new ApplicationUser(username, encoder.encode(password), firstName, lastName,
        dateOfBirth,
        bio);
    applicationUserRepository.save(newUser);
    Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return new RedirectView("/myprofile");
  }

  @GetMapping("/signup")
  public String getSignupPage(){
    return "signup";
  }

  @GetMapping("/login")
  public String getLoginPage(){
    return "login";
  }


  @GetMapping("/users/{id}")
  public String getOneUser(@PathVariable long id, Model m){
    ApplicationUser u = applicationUserRepository.findById(id).get();
    m.addAttribute("user", u);
    return "oneUser";
  }

//  Upon logging in, users should be taken to a /myprofile route that displays their information.
//  Ensure that user registration also logs users into your app automatically.
//  The site should be reasonably styled. (This can be using CSS that you did not create yourself.)
}