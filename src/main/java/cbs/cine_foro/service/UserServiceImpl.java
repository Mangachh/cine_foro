package cbs.cine_foro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.repository.UserRepo;

@Service
public class UserServiceImpl implements IUserService {

    //TODO: Create Exceptions
    //TODO: create repo!!
    @Autowired
    private UserRepo repo;

    @Override
    public User saveUser(User user) {
        return repo.save(user); // already exist exception;
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id).orElse(null); // exception not found!!
    }

    @Override
    public User getUserByName(String name) {
        return repo.findByName(name).orElse(null); // exception not found!!
    }


    @Override
    public User updateUserName(String originalName, String newName) {
        User user = repo.findByName(originalName).orElse(null); // execption don't exist
        user.setName(newName);
        return repo.save(user);
    }
    
}
