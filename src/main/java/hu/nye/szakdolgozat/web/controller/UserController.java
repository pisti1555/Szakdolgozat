package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final Session session;
    @Autowired
    public UserController(Session session) {
        this.session = session;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return session.userService.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return session.userService.login(user);
    }

    @PutMapping("/edit")
    public User edit(@RequestBody User user) {
        return session.userService.edit(user);
    }

    @GetMapping("/showAll")
    public List<User> showAll() {
        return session.userService.showAll();
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam String username) {
        return session.userService.delete(username);
    }
}
