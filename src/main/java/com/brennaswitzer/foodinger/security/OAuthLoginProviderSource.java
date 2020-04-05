package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.wire.LoginProvider;
import com.brennaswitzer.foodinger.wire.LoginProviderType;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OAuthLoginProviderSource implements LoginProviderSource {

    @Autowired(required = false)
    ClientRegistrationRepository oauthClientRepo;

    @Override
    public List<LoginProvider> loginProviders() {
        val providers = new ArrayList<LoginProvider>();
        if (oauthClientRepo == null) return providers;
        //noinspection unchecked
        for (val r : (Iterable<ClientRegistration>) oauthClientRepo) {
            providers.add(new LoginProvider(
                    LoginProviderType.OAUTH,
                    r.getRegistrationId(),
                    r.getClientName()));
        }
        return providers;
    }

}
