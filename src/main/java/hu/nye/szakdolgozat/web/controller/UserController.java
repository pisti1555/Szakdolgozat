package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.User;
import hu.nye.szakdolgozat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/edit")
    public User edit(@RequestBody User user) {
        return userService.edit(user);
    }

    @GetMapping("/showAll")
    public List<User> showAll() {
        return userService.showAll();
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam String username) {
        return userService.delete(username);
    }
}
