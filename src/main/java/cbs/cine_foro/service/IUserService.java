package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.User;
import cbs.cine_foro.error.UserAlreadyExistsException;
import cbs.cine_foro.error.UserNotExistsException;


public interface IUserService {
    /*
     * So I don't think I want to remove the users because
     * doing so will delete the movies and the user-veredicts or
     * setting them to null. 
     * Therefore, I think that is better to not remove users
     * from the database, at least for now
     */
    
    public User saveUser(final User user) throws UserAlreadyExistsException;

    public List<User> getAllUsers();

    public User getUserById(final Long id) throws UserNotExistsException;

    public User getUserByName(final String name) throws UserNotExistsException;

    public User updateUserName(final String originalName, final String newName) throws UserNotExistsException;


    


}
