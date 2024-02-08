package hu.nye.szakdolgozat.service;

import hu.nye.szakdolgozat.data.model.User;
import hu.nye.szakdolgozat.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Business-logic containing - User/Database related methods
 */
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Using this method to register, and saving it into the database
     * @param user via @RequestBody in Controller
     * @return new User if insert didn't happen, or User object if it did
     */
    @Override
    public User save(User user) {
        if (user.getUsername() == null || user.getFirstName() == null
                || user.getLastName() == null || user.getPassword() == null
        ) return new User();
        else {
            if (user.getUsername().isEmpty() || user.getFirstName().isEmpty() ||
                    user.getLastName().isEmpty() || user.getPassword().isEmpty()
            ) return new User();
            else {
                if (!exists(user.getUsername())) {
                    return userRepository.save(user);
                } else return new User();
            }
        }
    }

    /**
     * @return List of Users from the database
     */
    @Override
    public List<User> showAll() {
        return userRepository.findAll();
    }

    /**
     * @param user : getting the ID(username)
     * @return true if the username is already in the database
     */
    @Override
    public boolean exists(String user) {
        return userRepository.existsById(user);
    }

    /**
     * Updates the given user in database by deleting it and inserting the new with updated data
     * @param newUser : User object with the updated values
     * @return saved User if the update was successful
     */
    @Override
    public User edit(User newUser) {
        if (exists(newUser.getUsername())) {
            userRepository.delete(userRepository.findById(newUser.getUsername()).orElseThrow());
            return userRepository.save(newUser);
        } else return new User();
    }

    /**
     * Deletes the given user from database
     * @param username to find the user in database
     * @return true if the given user is not in the database anymore
     */
    @Override
    public boolean delete(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            if (userRepository.findById(username).isEmpty()) {
                return true;
            } else return false;
        } else return false;
    }
}
