package com.brennaswitzer.foodinger.model.library;

import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeItem implements ResourceComponent {

    private String raw;
    private Quantity quantity;
    private Resource resource;
    private String preparation;

    public RecipeItem() {}

}
