package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.data.UserRepository;
import com.brennaswitzer.foodinger.model.User;
import lombok.val;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class LocalUserOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    final UserRepository userRepo;

    final OAuth2UserService<OAuth2UserRequest, OAuth2UserInfo> delegate;

    public LocalUserOAuth2UserService(UserRepository userRepo, OAuth2UserService<OAuth2UserRequest, OAuth2UserInfo> delegate) {
        this.userRepo = userRepo;
        this.delegate = delegate;
    }

    @Override
    public UserPrincipal loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        val info = delegate.loadUser(userRequest);
        if (info == null) return null;
        val clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
        var user = userRepo.findByProviderAndProviderId(clientRegistrationId, info.getId());
        boolean isNew = user == null;
        if (isNew) {
            user = new User();
            user.setProvider(clientRegistrationId);
            user.setProviderId(info.getId());
        }
        user.setName(info.getName());
        user.setEmail(info.getEmail());
        user.setImageUrl(info.getImageUrl());
        if (isNew) userRepo.save(user);
        return UserPrincipal.of(user);
    }

}
