package com.company.springProject.controller;

import com.company.springProject.entity.User;
import com.company.springProject.service.EmailChecking;
import com.company.springProject.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EmailChecking emailChecking;

    @GetMapping("/register")
    public String registrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("register")
    public String register(User user){

       Optional<User> checkingUser = emailChecking.checkEmail(user);
       if(checkingUser.isPresent()){
           return "redirect:register?same";
       }
        userService.save(user);
        return "redirect:register?success";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/login")
    public String loginToWebsite(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest httpServletRequest){
       Optional<User> user = userService.login(email, password);
        if(!user.isPresent()){
            model.addAttribute("error", "Not FOund User");
            return "login";
        }
        httpServletRequest.getSession().setAttribute("user", user.get());
        return "welcome";
    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest httpServletRequest){
       User user = (User)httpServletRequest.getSession().getAttribute("user");
       if(user == null){
           return "login";
       }
        return "welcome";
    }

    @GetMapping("/logout")
    public  String logout(HttpServletRequest httpServletRequest, Model model){
        model.addAttribute("logout", "succesfully logout");
        httpServletRequest.getSession().invalidate();
        return "login";
    }

}
