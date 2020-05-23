package com.brennaswitzer.foodinger.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GoogleOAuth2User extends OAuth2UserInfo {

    @JsonProperty("sub")
    private String id;
    private String name;
    private String email;
    @JsonProperty("picture")
    private String imageUrl;

}
