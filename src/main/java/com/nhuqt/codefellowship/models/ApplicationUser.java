package com.nhuqt.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  String username;
  String password;
  String firstName;
  String lastName;
  Date dateOfBirth;
  String bio;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
  List<Post> posts;

  @ManyToMany
  @JoinTable(
      name = "user_following",
      joinColumns = {@JoinColumn(name ="primaryUser")},
      inverseJoinColumns = {@JoinColumn(name = "followedUser")}
  )
  Set<ApplicationUser> followedUsers;

  @ManyToMany(mappedBy = "followedUsers")
  Set<ApplicationUser> userFollowers;

  public ApplicationUser(){}

  public ApplicationUser(String username, String password, String firstName, String lastName, Date dateOfBirth,
                         String bio){
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.bio = bio;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public Set<ApplicationUser> getFollowedUsers() {
    return followedUsers;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  public long getId() {
    return id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  public String getBio() {
    return this.bio;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void addFollow(ApplicationUser followUser){
    followedUsers.add(followUser);
  }
}
