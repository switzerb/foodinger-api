package com.brennaswitzer.foodinger.web;

import com.brennaswitzer.foodinger.wire.LoginProvider;
import com.brennaswitzer.foodinger.wire.UserInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    HttpSessionCsrfTokenRepository csrfTokenRepo;

    @Autowired(required = false)
    ClientRegistrationRepository oauthClientRepo;

    @GetMapping("/user-info")
    public UserInfo userInfo(
            @AuthenticationPrincipal OAuth2User principal,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        CsrfToken token = csrfTokenRepo.loadToken(request);
        response.addHeader(token.getHeaderName(), token.getToken());
        return new UserInfo(principal.getAttribute("name"));
    }

    @GetMapping("/login-providers")
    public List<LoginProvider> loginProviders() {
        val providers = new ArrayList<LoginProvider>();
        if (oauthClientRepo == null) return providers;
        //noinspection unchecked
        for (val r : (Iterable<ClientRegistration>) oauthClientRepo) {
            providers.add(new LoginProvider(
                    r.getRegistrationId(),
                    r.getClientName()));
        }
        return providers;
    }
}
