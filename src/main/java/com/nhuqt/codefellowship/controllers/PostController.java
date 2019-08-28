package com.nhuqt.codefellowship.controllers;

import com.nhuqt.codefellowship.models.ApplicationUser;
import com.nhuqt.codefellowship.models.ApplicationUserRepository;
import com.nhuqt.codefellowship.models.Post;
import com.nhuqt.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {

  @Autowired
  ApplicationUserRepository applicationUserRepository;

  @Autowired
  PostRepository postRepository;

  @GetMapping("/posts")
  public String getPostForm(Model m, Principal p) {
    m.addAttribute("user", p);
    return "post";
  }

  @PostMapping("/posts")
  public RedirectView createPost(String body, Principal p, Model m){
    ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
    Post post = new Post(body, loggedInUser);
    postRepository.save(post);
    return new RedirectView("/users/" + loggedInUser.getId());
  }


}
