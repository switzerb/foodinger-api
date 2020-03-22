package com.brennaswitzer.foodinger.model.plan;

import com.brennaswitzer.foodinger.model.library.Recipe;
import com.brennaswitzer.foodinger.model.library.RecipeItem;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class PlanItem {

    private String name;
    private boolean deleted;
    private ItemType type;
    private ItemStatus status;
    private Instant dueAt;

    private List<PlanItem> children;
    private PlanItem parent;

    private List<PlanItem> components;
    private PlanItem componentOf;

    private Recipe recipe;
    private RecipeItem recipeItem;


}
