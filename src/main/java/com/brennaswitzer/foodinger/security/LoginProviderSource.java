package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.wire.LoginProvider;

import java.util.List;

public interface LoginProviderSource {

    List<LoginProvider> loginProviders();

}
