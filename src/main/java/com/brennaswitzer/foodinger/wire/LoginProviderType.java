package com.brennaswitzer.foodinger.wire;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LoginProviderType {

    @JsonProperty("oauth")
    OAUTH,
    @JsonProperty("local-user")
    LOCAL_USER,
    @JsonProperty("new-local-user")
    NEW_LOCAL_USER,

}
