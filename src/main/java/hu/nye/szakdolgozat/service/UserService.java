package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> showAll();
    boolean exists(String user);
    User edit(User user);
    boolean delete(String username);
}
