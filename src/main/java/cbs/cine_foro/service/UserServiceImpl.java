package cbs.cine_foro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;
import cbs.cine_foro.repository.UserRepo;

@Service
public class UserServiceImpl implements IUserService {

    // TODO: Create Exceptions

    @Autowired
    private UserRepo repo;

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        try {
            return repo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("The User already exists in the database");
        }

    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotExistsException {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotExistsException("The user does not exists in the database"));
    }

    @Override
    public User getUserByName(String name) throws UserNotExistsException {
        return repo.findByName(name)
                .orElseThrow(() -> new UserNotExistsException("The user does not exists in the database"));
    }

    @Override
    public User updateUserName(String originalName, String newName) throws UserNotExistsException {
        User user = repo.findByName(originalName).orElseThrow(() ->
                new UserNotExistsException("The user does not exists in the database"));
        user.setName(newName);
        return repo.save(user);
    }

}
