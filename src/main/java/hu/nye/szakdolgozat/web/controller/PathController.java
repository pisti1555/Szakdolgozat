package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.User;
import hu.nye.szakdolgozat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PathController {
    UserService userService;
    @Autowired
    public PathController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String home(Model model, User user) {
        model.addAttribute("player", user);
        return "index";
    }

    @GetMapping("/playGame")
    public String play() {
        return "playGame";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/scoreboard")
    public String scoreboard(Model model) {
        model.addAttribute("users", userService.showAll());
        return "scoreboard";
    }
}