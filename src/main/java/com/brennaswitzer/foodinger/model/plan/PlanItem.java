package com.brennaswitzer.foodinger.model.plan;

import com.brennaswitzer.foodinger.model.Item;
import com.brennaswitzer.foodinger.model.library.Ingredient;
import com.brennaswitzer.foodinger.model.library.RecipeItem;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"parent", "componentOf"})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanItem implements Item, Task {

    public static PlanItem section(String name) {
        return new PlanItem(name, ItemType.SECTION);
    }

    public static PlanItem task(String name) {
        return new PlanItem(name, ItemType.AD_HOC);
    }

    @NonNull
    private String raw;
    private Quantity quantity;
    private Ingredient ingredient;
    private String notes;
    private boolean deleted;
    @NonNull
    private ItemType type;
    private ItemStatus status = ItemStatus.NEEDED;
    private Instant dueAt;

    // plan hierarchy - on delete of parent, children become peers of their aunts and uncles
    // nested sets might make this storage a bit easier, but because the representation
    // primarily being acted upon is in the application, it buys us rather little.
    @Singular private List<PlanItem> children;
    private PlanItem parent;

    // creation binding - on delete of componentOf, components are deleted to, regardless
    // of their location in the tree.
    @Singular private List<PlanItem> components;
    private PlanItem componentOf;

    private Item sourceItem;
    private boolean split;

    protected PlanItem(String raw, ItemType type) {
        this.raw = raw;
        this.type = type;
    }

    public PlanItem(Item i) {
        this.raw = i.getRaw();
        this.sourceItem = i;
        // these might just copy over a null, but that's fine.
        this.quantity = i.getQuantity();
        this.ingredient = i.getIngredient();
        this.notes = i.getNotes();
        this.type = this.ingredient != null && this.ingredient.isRecipe()
            ? ItemType.TO_MAKE
            : ItemType.TO_BUY;
    }

    public void addChild(PlanItem it) {
        if (it.parent != null) {
            it.parent.removeChild(it);
        }
        if (children == null) children = new ArrayList<>();
        children.add(it);
        it.parent = this;

        // todo: should this make the child a component, if it's not already a component of something else?
    }

    public void removeChild(PlanItem it) {
        if (children.remove(it)) return;
        throw new IllegalArgumentException("The passed item is not a child of this item");
    }

    public void addComponent(PlanItem it) {
        if (it.componentOf != null) {
            throw new UnsupportedOperationException("The plan component hierarchy is immutable");
        }
        if (components == null) components = new ArrayList<>();
        components.add(it);
        it.componentOf = this;
    }

    public void addChild(Ingredient ing) {
        addChild(Quantity.ONE, ing);
    }

    public void addChild(Quantity q, Ingredient r) {
        val ri = new RecipeItem();
        ri.setRaw(q.isExplicit()
                ? q.toString() + " " + r.getName()
                : r.getName());
        ri.setQuantity(q);
        ri.setIngredient(r);
        addChild(ri);
    }

    protected PlanItem addChild(Item ri) {
        val pi = new PlanItem(ri);
        val ing = ri.getIngredient();
        if (ing.isRecipe()) {
            ing.getItems().forEach(c ->
                    pi.addComponent(pi.addChild(c)));
        }
        addChild(pi);
        return pi;
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    @Override
    public String getTaskName() {
        return getRaw();
    }
}
