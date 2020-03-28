package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.Label;
import com.brennaswitzer.foodinger.model.library.Recipe;
import com.brennaswitzer.foodinger.model.library.RecipeItem;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import com.brennaswitzer.foodinger.model.measure.Unit;

public class Library {

    public static final Recipe PIZZA = makePizza();
    public static final Recipe PIZZA_CRUST = makePizzaCrust();
    public static final Recipe PIZZA_SAUCE = makePizzaSauce();

    private static Recipe makePizza() {
        if (PIZZA != null) return PIZZA;
        return Recipe.builder()
                .name("Pizza")
                .directions("do the crust, top, bake, eat.")
                .label(Label.of("dinner"))
                .label(Label.of("kid friendly"))
                .item(RecipeItem.builder()
                        .raw("1 Pizza Crust")
                        .quantity(Quantity.of(1, null))
                        .resource(makePizzaCrust())
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 cup Pizza Sauce")
                        .quantity(Quantity.of(1, Unit.of("cup")))
                        .resource(makePizzaSauce())
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 & 1/2 cup shredded mozzarella cheese")
                        .quantity(Quantity.of(1.5, Unit.of("cup")))
                        .resource(Grocery.MOZZARELLA)
                        .notes("shredded")
                        .build())
                .item(RecipeItem.builder()
                        .raw("3 oz pepperoni")
                        .quantity(Quantity.of(3, Unit.of("oz")))
                        .resource(Grocery.PEPPERONI)
                        .build())
                .build();
    }

    private static Recipe makePizzaCrust() {
        if (PIZZA_CRUST != null) return PIZZA_CRUST;
        return Recipe.builder()
                .name("Pizza Crust")
                .directions("mix, rise, form.")
                .item(RecipeItem.builder()
                        .raw("3 cups flour")
                        .quantity(Quantity.of(3, Unit.of("cup")))
                        .resource(Grocery.FLOUR)
                        .build())
                .item(RecipeItem.builder()
                        .raw("2 cups water")
                        .quantity(Quantity.of(2, Unit.of("cup")))
                        .resource(Grocery.WATER)
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 Tbsp sugar")
                        .quantity(Quantity.of(1, Unit.of("Tbsp")))
                        .resource(Grocery.SUGAR)
                        .build())
                .item(RecipeItem.builder()
                        .raw("1.5 Tbsp yeast")
                        .quantity(Quantity.of(1.5, Unit.of("Tbsp")))
                        .resource(Grocery.YEAST)
                        .build())
                .build();
    }

    private static Recipe makePizzaSauce() {
        if (PIZZA_SAUCE != null) return PIZZA_SAUCE;
        return Recipe.builder()
                .name("Pizza Sauce")
                .directions("Dump the stuff together and cook until done!")
                .item(RecipeItem.builder()
                        .raw("28 oz tomatoes")
                        .quantity(Quantity.of(28, Unit.of("oz")))
                        .resource(Grocery.TOMATOES)
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 cup basil")
                        .quantity(Quantity.of(1, Unit.of("cup")))
                        .resource(Grocery.BASIL)
                        .build())
                .build();
    }

}
