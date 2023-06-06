package cbs.cine_foro.service;

import java.util.List;

import cbs.cine_foro.entity.User;


public interface IUserService {
    /*
     * So I don't think I want to remove the users because
     * doing so will delete the movies and the user-veredicts or
     * setting them to null. 
     * Therefore, I think that is better to not remove users
     * from the database, at least for now
     */
    
    public User saveUser(final User user);

    public List<User> getAllUsers();

    public User getUserById(final Long id);

    public User getUserByName(final String name);

    public User updateUserName(final String originalName, final String newName);


    


}
