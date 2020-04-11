package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.data.UserRepository;
import com.brennaswitzer.foodinger.model.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Transactional
public class UpdateDBAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository repo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        val principal = authentication.getPrincipal();
        if (!(principal instanceof OAuth2User)) return;
        // todo: this should be strongly typed, but we're proving the concept
        String name = ((OAuth2User) principal).getAttribute("name");
        var user = repo.findByUsername(name);
        if (user == null) {
            user = new User(name);
            user.setId(System.currentTimeMillis());
            repo.save(user);
        } // todo: else update
    }

}
