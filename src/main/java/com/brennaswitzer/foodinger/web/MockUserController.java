package com.brennaswitzer.foodinger.web;

import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.security.MockUserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Profile("mock-user")
public class MockUserController {

    @Autowired
    MockUserService userService;

    @PostMapping("/mock-user")
    public View createUser(
            @RequestParam("username") String username
    ) {
        val user = new User();
        user.setProvider("mock");
        user.setProviderId(username);
        user.setName(username);
        user.setEmail(username + "@foodinger.com");
        userService.createUser(user);
        return new RedirectView("/");
    }

}
