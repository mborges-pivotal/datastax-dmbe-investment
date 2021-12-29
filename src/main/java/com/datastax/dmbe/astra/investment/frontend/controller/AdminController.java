package com.datastax.dmbe.astra.investment.frontend.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.dmbe.astra.investment.frontend.security.UserCredentials;
import com.datastax.dmbe.astra.investment.frontend.security.UserCredentialsDto;
import com.datastax.dmbe.astra.investment.frontend.security.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class AdminController extends BaseController {

  public static final Set<String> AVAILABLE_ROLES = Set.of("ADMIN", "USER");

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;

  ///////////////////////////////
  // Registration
  ///////////////////////////////

  @PostMapping("/registration")
  public String register(HttpServletRequest request, @ModelAttribute UserCredentialsDto userCredentialsDto, Model model){
    UserCredentials user =
        UserCredentials.builder()
            .enabled(true)
            .name(userCredentialsDto.getUserName())
            .password(passwordEncoder.encode(userCredentialsDto.getPassword()))
            .roles(Set.of("USER"))
            .build();

    userRepo.save(user);

    return "home";
  }
  
  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("userCredentialsDto", new UserCredentialsDto());
    return "registration";
  }

  ///////////////////////////////
  // Settings
  ///////////////////////////////

  @GetMapping("/settings")
  public String settingsPage(Model model) {
    model.addAttribute("pageName", "settings");
    return "settings";
  }
  
  ///////////////////////////////
  // Profile
  ///////////////////////////////

  @GetMapping("/profile")
  public String profilePage(Model model) {
    model.addAttribute("pageName", "profile");

    String userName = getUserName();
    UserCredentials user = userRepo.findById(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
    model.addAttribute("user", user);

    Set<String> availableRoles = new HashSet<>(AVAILABLE_ROLES);
    for(String role: user.getRoles()) {
      availableRoles.remove(role);
    }

    model.addAttribute("availableRoles", availableRoles);
    
    return "profile";
  }

  @PostMapping("/profile")
  public String updateProfile(HttpServletRequest request, @ModelAttribute UserCredentials user, Model model) {
    model.addAttribute("pageName", "profile");
    model.addAttribute("user", user);

    log.debug("user credentials {}", user);

    return "profile";
  }
  
  ////////////////////////////////////
  // Authentication
  ////////////////////////////////////

  // Login form
  @RequestMapping("login.html")
  public String login() {
    return "login";
  }

  // Login form with error
  @RequestMapping("login-error.html")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return "login";
  }

  @GetMapping("logout.html")
  public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }

    return "redirect:/";
  }    
    
}
