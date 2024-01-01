package vttp.ssf.miniproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.ssf.miniproject.model.RegisterUser;
import vttp.ssf.miniproject.model.User;
import vttp.ssf.miniproject.repo.AccountRepo;

@Service
public class AccountService {
    
    @Autowired
    AccountRepo accRepo;

    public void addUser(RegisterUser registerUser){
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(registerUser.getPassword());
        accRepo.addUser(user);
    }

    public User getUser(String username){
        return accRepo.getUser(username);
    }

    public boolean passwordMatch(String password, String password2){
        if(password.equals(password2)){
            return true;
        }

        return false;
    }

    public boolean userExists(String username){
        if(accRepo.userExists(username)){
            return true;
        }

        return false;
    }
}
