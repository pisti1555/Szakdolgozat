package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.user.User;
import hu.nye.szakdolgozat.service.GameService;
import hu.nye.szakdolgozat.service.GameServiceImpl;
import hu.nye.szakdolgozat.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Session {
    protected final HttpSession session;
    protected UserService userService;
    private GameService gameService;

    @Autowired
    public Session(HttpSession session, UserService userService) {
        this.session = session;
        this.userService = userService;
    }


    public GameService getGameService() {
        if (session.getAttribute("gameService") == null) {
            session.setAttribute("gameService", new GameServiceImpl());
        }
        return (GameService) session.getAttribute("gameService");
    }

    public User getUser() {
        if (session.getAttribute("user") == null) {
            session.setAttribute("user", new User("GUEST-" + session.getId(), "Guest", "Guest", "Guest"));
        }
        return (User) session.getAttribute("user");
    }

    public User setUser(User user) {
        session.setAttribute("user", user);
        return (User) session.getAttribute("user");
    }

    public boolean isLoggedIn() {
        User user = getUser();
        if (userService.exists(user.getUsername())) return true; else return false;
    }
}