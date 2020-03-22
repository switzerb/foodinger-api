package com.brennaswitzer.foodinger.model.library;

import lombok.Data;

@Data
public class PurchasedResource implements Resource {

    private String name;
    private boolean deleted;

    public PurchasedResource() {}

    public PurchasedResource(String name) {
        this.name = name;
    }

}
