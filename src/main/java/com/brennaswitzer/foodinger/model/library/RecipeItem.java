package com.brennaswitzer.foodinger.model.library;

import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeItem implements ResourceItem {

    @NonNull
    private String raw;
    private Quantity quantity;
    private Resource resource;
    private String notes;

    public RecipeItem() {}

    public boolean isCompound() {
        return resource instanceof CompoundResource;
    }

    public String toLabel() {
        if (resource == null) return raw;
        val sb = new StringBuilder();
        if (quantity.isExplicit()) {
            sb.append(quantity)
                    .append(' ');
        }
        sb.append(resource.getName());
        if (notes != null && !notes.isEmpty()) {
            sb.append(", ")
                    .append(notes);
        }
        return sb.toString();
    }

}
