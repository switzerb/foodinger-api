package com.brennaswitzer.foodinger.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GitHubOAuth2User extends OAuth2UserInfo {

    private String id;
    private String name;
    private String login;
    private String email;
    @JsonProperty("avatar_url")
    private String imageUrl;

}
