package com.brennaswitzer.foodinger.model.library;

import lombok.Data;
import lombok.NonNull;

@Data
public class BaseResource implements Resource {

    @NonNull
    private String name;
    private boolean deleted;

    public BaseResource() {}

    public BaseResource(String name) {
        this.name = name;
    }

}
