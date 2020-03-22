package com.brennaswitzer.foodinger.model.measure;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * I represent a quantity of something: some scalar value plus a unit. I should
 * be replaced by javax.measure's machinery, but as there are no operations to
 * be done on Quantities at the moment, this is easier.
 */
@Data
@AllArgsConstructor(staticName = "of")
public class Quantity {

    private Number value;
    private Unit unit;

}
