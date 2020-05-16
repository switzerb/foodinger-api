package com.brennaswitzer.foodinger.model;

import com.brennaswitzer.foodinger.model.library.Ingredient;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.val;

public interface Item {

    String getRaw();
    Quantity getQuantity();
    Ingredient getIngredient();
    String getNotes();

    default boolean hasIngredient() {
        return getIngredient() != null;
    }

    default String toLabel() {
        val ing = getIngredient();
        if (ing == null) return getRaw();
        val sb = new StringBuilder();
        val q = getQuantity();
        if (q.isExplicit()) {
            sb.append(q)
                    .append(' ');
        }
        sb.append(ing.getName());
        if (getNotes() != null && !getNotes().isEmpty()) {
            sb.append(", ")
                    .append(getNotes());
        }
        return sb.toString();
    }

}
