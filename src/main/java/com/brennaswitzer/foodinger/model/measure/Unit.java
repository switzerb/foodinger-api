package com.brennaswitzer.foodinger.model.measure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(staticName = "of")
public class Unit {

    @NonNull
    private String name;

    @Override
    public String toString() {
        return name;
    }

}
