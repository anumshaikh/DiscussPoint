package com.anums.blogApp.DiscussPoint.Login;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.anums.blogApp.DiscussPoint.Configuration.SecurityService;
import com.anums.blogApp.DiscussPoint.users.Role;
import com.anums.blogApp.DiscussPoint.users.UserModel;
import com.anums.blogApp.DiscussPoint.users.Validators.UserValidator;
import com.anums.blogApp.DiscussPoint.users.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({ "/", "/login" })
    public ModelAndView loadIndexPage() {
        return new ModelAndView("Login");
    }

    @GetMapping({ "/register" })
    public ModelAndView loadRegisterPage() {
        return new ModelAndView("userRegistration");
    }

    @GetMapping("/homepage")
    public ModelAndView homePageNavigation() {

        return new ModelAndView("HomePage");
    }

    @GetMapping("/postpage")
    public ModelAndView postPageNavigation() {
        System.out.println("Post page");
        return new ModelAndView("Post");
    }

    /*
     * @PostMapping("/perform_login")
     * 
     * @ResponseBody public ModelAndView submitLoginfunction(HttpServletRequest
     * request) throws Exception { String username =
     * request.getParameter("username"); String password =
     * request.getParameter("password"); System.out.println("Username " + username);
     * return new ModelAndView("HomePage"); }
     */

    @PostMapping("/perform-registration")
    @ResponseBody
    public String registerUser(HttpServletRequest request) {
       String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        Set<Role> role = new HashSet<>();
        role.add(new Role());
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        
        UserModel newuser = new UserModel(username, encodedPassword, encodedPassword,role); 
   /*      userValidator.validate(newuser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        } */

        userService.save(newuser);
        securityService.autologin(username, password);
        System.out.println("User Registered");

        return "redirect:/homepage";
    }

}
