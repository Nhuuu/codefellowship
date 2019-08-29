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

import java.security.Principal;
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

  @GetMapping("/myprofile")
  public String getProfile(Principal p, Model m){
    ApplicationUser applicationUser = null;
    if(p != null){
      applicationUser = applicationUserRepository.findByUsername(p.getName());
    }
    m.addAttribute("viewedUser", applicationUser);
    m.addAttribute("user", p);
    return "myprofile";
  }

  @GetMapping("/users/{id}")
  public String getOneUser(@PathVariable long id, Principal p, Model m){
    ApplicationUser u = applicationUserRepository.findById(id).get();
    m.addAttribute("viewedUser", u);
    m.addAttribute("user", p);
    return "myprofile";
  }

  @GetMapping("/users")
  public String getAllUsers(Principal p, Model m){
    if(p != null){
      m.addAttribute("user", p);
    }
    m.addAttribute("allUsers", applicationUserRepository.findAll());
    return "allUsers";
  }

  @PostMapping("/follow/{id}")
  public RedirectView addFollowedUser(@PathVariable long id, Principal p){
    ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
    loggedInUser.addFollow(applicationUserRepository.findById(id).get());
    applicationUserRepository.save(loggedInUser);
    return new RedirectView("/myprofile");
  }

  @GetMapping("/feed")
  public String getUserFeed(Principal p, Model m){
    if(p != null){
      m.addAttribute("user", p);
    }
    m.addAttribute("loggedInUser", applicationUserRepository.findByUsername(p.getName()));
    return "feed";
  }
}
