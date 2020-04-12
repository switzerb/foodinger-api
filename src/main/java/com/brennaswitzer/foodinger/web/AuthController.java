package com.brennaswitzer.foodinger.web;

import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.security.LoginProviderSource;
import com.brennaswitzer.foodinger.security.OAuth2UserInfo;
import com.brennaswitzer.foodinger.wire.LoginProvider;
import com.brennaswitzer.foodinger.wire.UserInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    List<LoginProviderSource> loginProviderSource;

    @GetMapping("/user-info")
    public UserInfo userInfo(
            @AuthenticationPrincipal Object principal,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        CsrfToken token = csrfTokenRepo.loadToken(request);
        response.addHeader(token.getHeaderName(), token.getToken());
        String name = principal instanceof OAuth2UserInfo
                ? ((OAuth2UserInfo) principal).getName()
                : principal instanceof User
                ? ((User) principal).getName()
                : "";
        return new UserInfo(name);
    }

    @GetMapping("/login-providers")
    public List<LoginProvider> loginProviders() {
        val providers = new ArrayList<LoginProvider>();
        loginProviderSource.forEach(lps ->
                providers.addAll(lps.loginProviders()));
        return providers;
    }
}
