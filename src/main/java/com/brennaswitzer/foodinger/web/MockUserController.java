package com.brennaswitzer.foodinger.web;

import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.security.MockUserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Profile("mock-user")
public class MockUserController {

    @Autowired
    MockUserService userService;

    @GetMapping("/mock-user/{username}")
    public View chooseUser(
            @PathVariable("username") String username
    ) {
        // do neat auth things?
        return new RedirectView("/");
    }

    @PostMapping("/mock-user")
    public View createUser(
            @RequestParam("username") String username
    ) {
        val user = new User(username);
        user.setId(System.currentTimeMillis());
        userService.createUser(user);
        return chooseUser(username);
    }

}
