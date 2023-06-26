package cbs.cine_foro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.service.IUserService;

/*
 * All the readings in the database will be in this controller.
 */
@RestController
public class ReadController {
    @Autowired
    private IUserService userService;


    // read users
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(name = "id") final Long id) throws UserNotExistsException {
        return userService.getUserById(id);
    }

    @GetMapping("/user/")
    public User getUserByName(@RequestParam(name = "name") final String name) throws UserNotExistsException {
        return userService.getUserByName(name);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
