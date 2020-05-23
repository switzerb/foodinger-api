package com.brennaswitzer.foodinger.web;

import com.brennaswitzer.foodinger.data.UserRepository;
import com.brennaswitzer.foodinger.security.LoginProviderSource;
import com.brennaswitzer.foodinger.security.UserPrincipal;
import com.brennaswitzer.foodinger.wire.LoginProvider;
import com.brennaswitzer.foodinger.wire.UserInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user-info")
    @Transactional
    public UserInfo userInfo(
            @AuthenticationPrincipal UserPrincipal principal,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        val token = csrfTokenRepo.loadToken(request);
        response.addHeader(token.getHeaderName(), token.getToken());
        return UserInfo.of(userRepository.getOne(principal.getId()))
                .withPrincipal(principal);
    }

    @GetMapping("/login-providers")
    public List<LoginProvider> loginProviders() {
        val providers = new ArrayList<LoginProvider>();
        loginProviderSource.forEach(lps ->
                providers.addAll(lps.loginProviders()));
        return providers;
    }
}
