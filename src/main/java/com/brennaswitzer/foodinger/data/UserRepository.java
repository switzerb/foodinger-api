package com.brennaswitzer.foodinger.data;

import com.brennaswitzer.foodinger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByProviderAndProviderId(String provider, String providerId);

}
