package vttp.ssf.miniproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.miniproject.model.RegisterUser;
import vttp.ssf.miniproject.model.User;
import vttp.ssf.miniproject.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accSvc;
    
    @GetMapping("/login")
    public ModelAndView getLogin(){
        ModelAndView mav = new ModelAndView("login");
        User user = new User();
        mav.addObject("user", user);

        return mav;
    }

    @PostMapping("/login")
    public String postLogin(@Valid @ModelAttribute User user, BindingResult result, HttpSession session){

        if(result.hasErrors()){
            return "login";
        }

        //Check if username exists
       if(!accSvc.userExists(user.getUsername())){
            FieldError err = new FieldError("user", "username", "Username does not exist");
            result.addError(err);
            return "login";
        }

        //Check if password is correct
        User loginUser = accSvc.getUser(user.getUsername());
        if(!loginUser.getPassword().equals(user.getPassword())){
            FieldError err = new FieldError("user", "password", "Your password is incorrect");
            result.addError(err);
            return "login";
        }

        //Start session
        session.setAttribute("username", user.getUsername());

        return "redirect:/";
    }

    @GetMapping("/register")
    public ModelAndView getRegister(){
        ModelAndView mav = new ModelAndView("register");
        RegisterUser registerUser = new RegisterUser();
        mav.addObject("registerUser",registerUser);
        
        return mav;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterUser registerUser, BindingResult result){

        if(result.hasErrors()){
            return "register";
        }
        
        //Check if username already exist
        if(accSvc.userExists(registerUser.getUsername())){
            FieldError err = new FieldError("registerUser", "username", "This username is taken");
            result.addError(err);            
            return "register";
        }

        //Check if both passwords match
        if(!accSvc.passwordMatch(registerUser.getPassword(), registerUser.getPassword2())){
            FieldError err = new FieldError("registerUser", "password2", "Passwords do not match");
            result.addError(err);

            return "register";
        }

        accSvc.addUser(registerUser);

        //How to show succesfully registered before redirecting to login page
        
        return "redirect:/account/login";
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session){
        String username = (String) session.getAttribute("username");

        session.invalidate();
        System.out.println("Logging out " + username);
        return "redirect:/";
    }

}
