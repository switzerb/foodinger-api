package com.brennaswitzer.foodinger.model.library;

import lombok.Data;
import lombok.NonNull;

@Data
public class GroceryResource implements Resource {

    @NonNull
    private String name;
    private boolean deleted;

    public GroceryResource() {}

    public GroceryResource(String name) {
        this.name = name;
    }

}
