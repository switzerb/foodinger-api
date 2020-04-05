package com.brennaswitzer.foodinger.wire;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginProvider {

    @NonNull
    private LoginProviderType type;

    @NonNull
    private String id;

    @NonNull
    private String name;

}
