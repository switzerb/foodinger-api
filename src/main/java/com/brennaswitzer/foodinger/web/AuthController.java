package com.brennaswitzer.foodinger.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    HttpSessionCsrfTokenRepository csrfTokenRepo;

    @GetMapping("/user-info")
    public Map<String, Object> userInfo(
            @AuthenticationPrincipal OAuth2User principal,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        CsrfToken token = csrfTokenRepo.loadToken(request);
        response.addHeader(token.getHeaderName(), token.getToken());
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
}
