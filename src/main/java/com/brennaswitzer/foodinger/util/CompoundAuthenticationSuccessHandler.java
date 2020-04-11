package com.brennaswitzer.foodinger.util;

import org.hibernate.validator.internal.util.CollectionHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CompoundAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private List<AuthenticationSuccessHandler> handlers;

    public CompoundAuthenticationSuccessHandler(List<AuthenticationSuccessHandler> handlers) {
        this.handlers = CollectionHelper.toImmutableList(handlers);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        for (AuthenticationSuccessHandler h : handlers) {
            h.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
