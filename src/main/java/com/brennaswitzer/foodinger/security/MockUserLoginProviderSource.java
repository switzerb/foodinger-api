package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.data.UserRepository;
import com.brennaswitzer.foodinger.wire.LoginProvider;
import com.brennaswitzer.foodinger.wire.LoginProviderType;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("mock-user")
@Order
public class MockUserLoginProviderSource implements LoginProviderSource {

    @Autowired
    UserRepository userRepo;

    @Override
    public List<LoginProvider> loginProviders() {
        val providers = new ArrayList<LoginProvider>();
        for (val u : userRepo.findAll(Sort.by("name"))) {
            providers.add(
                new LoginProvider(
                        LoginProviderType.LOCAL_USER,
                        u.getId().toString(),
                        u.getName()));
        }
        providers.add(
                new LoginProvider(
                        LoginProviderType.NEW_LOCAL_USER,
                        "new-local-user",
                        "New Local User"));
        return providers;
    }

}
