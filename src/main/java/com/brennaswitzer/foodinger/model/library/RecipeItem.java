package com.brennaswitzer.foodinger.model.library;

import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeItem implements ResourceComponent {

    @NonNull
    private String raw;
    private Quantity quantity;
    private Resource resource;
    private String notes;

    public RecipeItem() {}

    public boolean isCompound() {
        return resource instanceof CompoundResource;
    }

}
