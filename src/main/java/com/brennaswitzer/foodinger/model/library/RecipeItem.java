package com.brennaswitzer.foodinger.model.library;

import com.brennaswitzer.foodinger.model.Item;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeItem implements Item {

    @NonNull
    private String raw;
    private Quantity quantity;
    private Ingredient ingredient;
    private String notes;

    public RecipeItem() {}

}
