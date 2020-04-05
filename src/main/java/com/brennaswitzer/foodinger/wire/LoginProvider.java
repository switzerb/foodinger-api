package com.brennaswitzer.foodinger.wire;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginProvider {

    @NonNull
    private String id;
    @NonNull
    private String name;

}
