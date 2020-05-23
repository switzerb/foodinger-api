package com.brennaswitzer.foodinger.wire;

import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.security.UserPrincipal;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class UserInfo {

    private Long id;
    @NonNull
    private String name;
    private String email;
    private String imageUrl;
    private String provider;

    private Collection<String> roles;

    public static UserInfo of(User user) {
        UserInfo info = new UserInfo(user.getName());
        info.setId(user.getId());
        info.setEmail(user.getEmail());
        info.setImageUrl(user.getImageUrl());
        info.setProvider(user.getProvider());
        return info;
    }

    public UserInfo withPrincipal(UserPrincipal principal) {
        if (id == null) {
            setId(principal.getId());
            setEmail(principal.getEmail());
        } else if (!id.equals(principal.getId())) {
            throw new IllegalArgumentException("withPrincipal requires the UserInfo's existing User's UserPrincipal");
        }
        setRoles(principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(it -> it.startsWith("ROLE_"))
                .map(it -> it.substring(5))
                .collect(Collectors.toList()));
        return this;
    }

}
