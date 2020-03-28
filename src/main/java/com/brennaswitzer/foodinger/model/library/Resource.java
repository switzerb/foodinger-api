package com.brennaswitzer.foodinger.model.library;

public interface Resource {

    String getName();

    default boolean isCompound() {
        return this instanceof CompoundResource;
    }

}
