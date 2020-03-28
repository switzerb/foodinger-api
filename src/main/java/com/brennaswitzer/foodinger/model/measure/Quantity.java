package com.brennaswitzer.foodinger.model.measure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.val;

/**
 * I represent a quantity of something: some scalar value plus a unit. I should
 * be replaced by javax.measure's machinery, but as there are no operations to
 * be done on Quantities at the moment, this is easier.
 */
@Data
@AllArgsConstructor(staticName = "of")
public class Quantity {

    public static final Quantity ONE = Quantity.of(1, null);

    @NonNull
    private Number value;
    private Unit unit;

    public boolean isExplicit() {
        return unit != null || !value.equals(1);
    }

    @Override
    public String toString() {
        val sb = new StringBuilder()
                .append(value);
        if (unit != null) {
            sb.append(' ').append(unit.getName());
        }
        return sb.toString();
    }

}
