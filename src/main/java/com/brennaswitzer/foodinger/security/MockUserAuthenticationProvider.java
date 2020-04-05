package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.data.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Profile("mock-user")
public class MockUserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepo;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        val username = auth.getName();
        val user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No '" + username + "' user is known");
        }
        return new MockUserAuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

}
