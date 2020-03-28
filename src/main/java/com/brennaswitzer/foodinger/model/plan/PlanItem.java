package com.brennaswitzer.foodinger.model.plan;

import com.brennaswitzer.foodinger.model.library.CompoundResource;
import com.brennaswitzer.foodinger.model.library.RecipeItem;
import com.brennaswitzer.foodinger.model.library.Resource;
import com.brennaswitzer.foodinger.model.library.ResourceComponent;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString(exclude = {"parent", "componentOf"})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanItem {

    @NonNull
    private String name;
    private boolean deleted;
    private ItemStatus status;
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

    private ResourceComponent sourceResourceComponent;
    private boolean split;

    public PlanItem() {}

    public PlanItem(String name) {
        this.name = name;
    }

    public ItemType getType() {
        if (sourceResourceComponent == null) {
            return ItemType.SECTION;
        }
        if (sourceResourceComponent.getResource().isCompound()) {
            return ItemType.RECIPE;
        }
        return ItemType.BASE_RESOURCE;
    }

    public void addChild(PlanItem it) {
        if (it.parent != null) {
            it.parent.removeChild(it);
        }
        if (children == null) children = new ArrayList<>();
        children.add(it);
        it.parent = this;
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

    public void addChild(Resource r) {
        addChild(Quantity.ONE, r);
    }

    public void addChild(Quantity q, Resource r) {
        addChild(RecipeItem.builder()
                .raw(q.toString() + " " + r.getName())
                .quantity(q)
                .resource(r)
                .build());
    }

    protected PlanItem addChild(ResourceComponent rc) {
        val r = rc.getResource();
        val pi = new PlanItem(r.getName());
        pi.setSourceResourceComponent(rc);
        addChild(pi);
        if (r.isCompound()) {
            ((CompoundResource) r).getComponents().forEach(c ->
                    pi.addComponent(pi.addChild(c)));
        }
        return pi;
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    public String toLabel() {
        if (sourceResourceComponent == null) {
            return name;
        }
        return sourceResourceComponent.toLabel();
    }

}
