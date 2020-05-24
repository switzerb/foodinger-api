package com.brennaswitzer.foodinger.security;

import org.springframework.security.core.AuthenticationException;

public class NoFederationAuthException extends AuthenticationException {
    public NoFederationAuthException(String clientRegistrationId, String email) {
        super("Looks like you're signed up with your " +
                clientRegistrationId + " account. Please use that to login.");
    }
}
