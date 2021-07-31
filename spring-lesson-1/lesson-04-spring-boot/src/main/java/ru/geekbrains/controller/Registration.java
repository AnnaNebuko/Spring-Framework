package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
public class Registration {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        Optional<User> userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb.isPresent()) {
            model.put("message", "User exists!");
            return "registration";
        }
        userRepo.save(user);

        return "redirect:/login_page";
    }
}
