package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.data.UserRepository;
import com.brennaswitzer.foodinger.model.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
public class UpdateDBAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository repo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (!(authentication instanceof OAuth2AuthenticationToken)) return;
        val clientRegistrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        val principal = authentication.getPrincipal();
        if (!(principal instanceof OAuth2UserInfo)) return;
        String name = ((OAuth2UserInfo) principal).getName();
        // todo: use the client registration id too
        var user = repo.findByUsername(name); // todo: use an actual key
        if (user == null) {
            user = new User(name);
            user.setId(System.currentTimeMillis());
            repo.save(user);
        } // todo: else update
    }

}
