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
        OAuth2UserInfo info = (OAuth2UserInfo) principal;
        var user = repo.findByProviderAndProviderId(clientRegistrationId, info.getId());
        boolean isNew = user == null;
        if (isNew) {
            user = new User();
            user.setProvider(clientRegistrationId);
            user.setProviderId(info.getId());
        }
        user.setName(info.getName());
        user.setEmail(info.getEmail());
        user.setImageUrl(info.getImageUrl());
        if (isNew) repo.save(user);
    }

}
