package cbs.cine_foro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.service.IUserService;
import cbs.cine_foro.service.UserServiceImpl;

/**
 * All the writings in the database are here
 */
@RestController
public class WriteController {

    @Autowired
    private IUserService userService;
    
    // create user
    @PostMapping("/user")
    public User createUser(@RequestBody User user) throws UserAlreadyExistsException {
        return userService.saveUser(user);
    }


    @PutMapping("/user/{id}")
    public User updateUserById(@PathVariable(name = "id") final Long id,
            @RequestParam(name = "newName") final String newName) throws UserNotExistsException {
        return userService.updateUserNameById(id, newName);
    }
    
    @PutMapping("/user/")
    public User updateUserById(@RequestParam(name = "userName") final String name,
            @RequestParam(name = "newName") final String newName) throws UserNotExistsException {
        return userService.updateUserNameByName(name, newName);
    }
    
    

    @DeleteMapping("user/{id}")
    public void deleteUserById() {
    }

    @DeleteMapping("/user/")
    public void deleteUserByName() {
        
    }

    // create nationality

    // create movie

    // create valid whatever
}
