package com.brennaswitzer.foodinger.model.library;

import com.brennaswitzer.foodinger.model.Label;
import com.brennaswitzer.foodinger.model.Labeled;
import com.brennaswitzer.foodinger.model.Owned;
import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ingredient implements Owned, Labeled {

    private User owner;
    private String name;
    private boolean deleted;

    private boolean recipe;
    private String imagePath;
    private String externalUrl;
    @Singular private Set<Label> labels;
    private Quantity yield;
    private Quantity timeToMake;
    @Singular private List<RecipeItem> items;
    private String directions;

    public Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
    }

    @Override
    public void addLabel(Label label) {
        if (labels == null) labels = new HashSet<>();
        labels.add(label);
    }

    @Override
    public void removeLabel(Label label) {
        if (labels == null) return;
        labels.remove(label);
    }

    @Override
    public void clearLabels() {
        labels.clear();
    }
}
