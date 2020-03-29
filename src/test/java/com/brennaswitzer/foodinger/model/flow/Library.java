package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.Label;
import com.brennaswitzer.foodinger.model.library.Ingredient;
import com.brennaswitzer.foodinger.model.library.RecipeItem;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import com.brennaswitzer.foodinger.model.measure.Unit;

public class Library {

    public static final Ingredient PIZZA = makePizza();
    public static final Ingredient PIZZA_CRUST = makePizzaCrust();
    public static final Ingredient PIZZA_SAUCE = makePizzaSauce();

    private static Ingredient makePizza() {
        if (PIZZA != null) return PIZZA;
        return Ingredient.builder()
                .recipe(true)
                .name("Pizza")
                .directions("do the crust, top, bake, eat.")
                .label(Label.of("dinner"))
                .label(Label.of("kid friendly"))
                .item(RecipeItem.builder()
                        .raw("Pizza Crust")
                        .quantity(Quantity.of(1, null))
                        .ingredient(makePizzaCrust())
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 cup Pizza Sauce")
                        .quantity(Quantity.of(1, Unit.of("cup")))
                        .ingredient(makePizzaSauce())
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 & 1/2 cup shredded mozzarella cheese")
                        .quantity(Quantity.of(1.5, Unit.of("cup")))
                        .ingredient(Grocery.MOZZARELLA)
                        .notes("shredded")
                        .build())
                .item(RecipeItem.builder()
                        .raw("3 oz pepperoni")
                        .quantity(Quantity.of(3, Unit.of("oz")))
                        .ingredient(Grocery.PEPPERONI)
                        .build())
                .build();
    }

    private static Ingredient makePizzaCrust() {
        if (PIZZA_CRUST != null) return PIZZA_CRUST;
        return Ingredient.builder()
                .recipe(true)
                .name("Pizza Crust")
                .directions("mix, rise, form.")
                .item(RecipeItem.builder()
                        .raw("3 cups flour")
                        .quantity(Quantity.of(3, Unit.of("cup")))
                        .ingredient(Grocery.FLOUR)
                        .build())
                .item(RecipeItem.builder()
                        .raw("2 cups water")
                        .quantity(Quantity.of(2, Unit.of("cup")))
                        .ingredient(Grocery.WATER)
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 Tbsp sugar")
                        .quantity(Quantity.of(1, Unit.of("Tbsp")))
                        .ingredient(Grocery.SUGAR)
                        .build())
                .item(RecipeItem.builder()
                        .raw("1.5 Tbsp yeast")
                        .quantity(Quantity.of(1.5, Unit.of("Tbsp")))
                        .ingredient(Grocery.YEAST)
                        .build())
                .build();
    }

    private static Ingredient makePizzaSauce() {
        if (PIZZA_SAUCE != null) return PIZZA_SAUCE;
        return Ingredient.builder()
                .recipe(true)
                .name("Pizza Sauce")
                .directions("Dump the stuff together and cook until done!")
                .item(RecipeItem.builder()
                        .raw("28 oz tomatoes")
                        .quantity(Quantity.of(28, Unit.of("oz")))
                        .ingredient(Grocery.TOMATOES)
                        .build())
                .item(RecipeItem.builder()
                        .raw("1 cup basil")
                        .quantity(Quantity.of(1, Unit.of("cup")))
                        .ingredient(Grocery.BASIL)
                        .build())
                .build();
    }

}
