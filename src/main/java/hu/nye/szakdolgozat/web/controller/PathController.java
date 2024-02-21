package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.user.PasswordEditForm;
import hu.nye.szakdolgozat.data.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PathController {
    private final Session session;
    @Autowired
    public PathController(Session session) {
        this.session = session;
    }

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("client", session.getUser());
        return "index";
    }

    @GetMapping("/playGame")
    public String play(Model model) {
        model.addAttribute("client", session.getUser());
        return "playGame";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("users", session.userService.showAll());
        model.addAttribute("isLoggedIn", session.userService.exists(session.getUser().getUsername()));
        model.addAttribute("client", session.getUser());
        return "profile/clientProfile";
    }

    @GetMapping("/profile/{username}")
    public String profile(Model model, @PathVariable String username) {
        model.addAttribute("client", session.getUser());
        model.addAttribute("user", session.userService.getUser(username));
        return "profile/userProfile";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("client", session.getUser());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(Model model, User newUser) {
        model.addAttribute("client", session.getUser());
        if (session.userService.exists(newUser.getUsername())) {
            model.addAttribute("exists", true);
            return "account/register";
        } else {
            session.userService.save(newUser);
            model.addAttribute("exists", false);
            return "index";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("client", session.getUser());
        model.addAttribute("isLoggedIn", session.isLoggedIn());
        return "account/login";
    }

    @PostMapping("/login")
    public String login(Model model, User user) {
        model.addAttribute("client", session.getUser());
        model.addAttribute("isLoggedIn", session.isLoggedIn());
        if (session.userService.login(user) != null) {
            session.setUser(session.userService.login(user));
            model.addAttribute("success", true);
            model.addAttribute("client", session.getUser());
            return "index";
        } else {
            model.addAttribute("success", false);
            return "account/login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        session.setUser(new User("GUEST-" + session.session.getId(), "Guest", "Guest", "Guest"));
        model.addAttribute("client", session.getUser());
        return "account/logout";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        model.addAttribute("client", session.getUser());
        return "edit/editProfile";
    }

    @GetMapping("/profile/edit/edit-password")
    public String getEditPassword(Model model) {
        model.addAttribute("client", session.getUser());
        return "edit/editPassword";
    }

    @PostMapping("/profile/edit/edit-password")
    public String editPassword(Model model, PasswordEditForm passwordEditForm) {
        model.addAttribute("client", session.getUser());
        User newUser = session.userService.edit(passwordEditForm, session.getUser());
        if (newUser != null) {
            session.setUser(newUser);
            model.addAttribute("client", session.getUser());
        }
        System.out.println(newUser);
        return "edit/editProfile";
    }

    @GetMapping("/scoreboard")
    public String scoreboard(Model model) {
        model.addAttribute("users", session.userService.showAll());
        model.addAttribute("client", session.getUser());
        return "scoreboard";
    }
}