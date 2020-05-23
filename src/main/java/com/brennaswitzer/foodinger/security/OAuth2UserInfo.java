package com.brennaswitzer.foodinger.security;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OAuth2UserInfo implements OAuth2User {

    private Map<String, Object> attributes = new HashMap<>();
    @Getter
    private List<GrantedAuthority> authorities =
            AuthorityUtils.createAuthorityList("ROLE_USER");

    @JsonAnySetter
    public void setAttr(String key, Object value) {
        attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        if (!attributes.containsKey("id")) {
            attributes.put("id", getId());
            attributes.put("name", getName());
            attributes.put("email", getEmail());
            attributes.put("imageUrl", getImageUrl());
        }
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

}
