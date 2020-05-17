package com.brennaswitzer.foodinger.model.shop;

import com.brennaswitzer.foodinger.model.Item;
import com.brennaswitzer.foodinger.model.Owned;
import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.model.library.Ingredient;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import com.brennaswitzer.foodinger.model.plan.ItemType;
import com.brennaswitzer.foodinger.model.plan.Plan;
import com.brennaswitzer.foodinger.model.plan.PlanItem;
import lombok.*;
import lombok.experimental.Delegate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
/* todo: perhaps this whole concept should just hang off the user? The only part
    that isn't user-global is the included plans, and that could just as easily
    be a flag on Plan for whether it's "hot for shopping" or whatever.
 */
public class ShoppingList implements Owned {

    public interface SLItem extends Item {
        boolean isAggregate();

        Collection<PlanItem> getComponents();
    }

    @Data
    @AllArgsConstructor
    private static class ListItem implements Item {

        @NonNull
        private String raw;
        private Quantity quantity;
        private Ingredient ingredient;
        private String notes;

    }

    private User owner;

    private Collection<Plan> includedPlans;

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    // todo: leaky access
    public static class Ordered {

        static Ordered of(Ingredient ing) {
            return new Ordered(ing, null);
        }

        static Ordered of(PlanItem it) {
            if (it.getIngredient() != null) {
                throw new IllegalArgumentException("PlanItems w/ an ingredient can't be ordered. Order the ingredient");
            }
            return new Ordered(null, it);
        }

        private Ingredient ingredient;
        private PlanItem adHocItem;
    }

    // todo: talk about linear scans....
    private List<Ordered> orderedIngredients = new LinkedList<>();

    private void orderAfter(Ordered it, Ordered anchor) {
        orderedIngredients.remove(it);
        int i = getOrder(anchor);
        orderedIngredients.add(i + 1, it);
    }

    private int getOrder(Ordered it) {
        int i = orderedIngredients.indexOf(it);
        if (i >= 0) return i;
        orderedIngredients.add(0, it);
        return 0;
    }

    public void orderAfter(Ingredient ing, Ingredient anchor) {
        orderAfter(Ordered.of(ing), Ordered.of(anchor));
    }

    public void orderAfter(Ingredient ing, PlanItem anchor) {
        orderAfter(Ordered.of(ing), Ordered.of(anchor));
    }

    public void orderAfter(PlanItem it, Ingredient anchor) {
        orderAfter(Ordered.of(it), Ordered.of(anchor));
    }

    public void orderAfter(PlanItem it, PlanItem anchor) {
        orderAfter(Ordered.of(it), Ordered.of(anchor));
    }

    private int getOrder(Ingredient ing) {
        return getOrder(Ordered.of(ing));
    }

    private int getOrder(PlanItem it) {
        return getOrder(Ordered.of(it));
    }

    public List<? extends SLItem> aggregate() {
        val allPlanItems = new LinkedList<PlanItem>();
        includedPlans.forEach(p ->
                addItems(allPlanItems, p));

        val adHoc = allPlanItems
                .stream()
                .filter(Predicate.not(Item::hasIngredient))
                .collect(Collectors.toList());
        val byIngredient = allPlanItems
                .stream()
                .filter(Item::hasIngredient)
                .collect(Collectors.groupingBy(Item::getIngredient));

        val result = new ArrayList<SLItemProxy>(adHoc.size() + byIngredient.size());
        adHoc.forEach(it ->
                // let these get added to the list on demand, so they're first
                result.add(new SLItemProxy(it)));
        byIngredient.forEach((ing, items) -> {
            getOrder(ing); // get all these in the list ahead of time
            result.add(new SLItemProxy(ing, items));
        });

        result.sort((a, b) -> {
            // todo: stinky!
            val ai = a.isAggregate() ? getOrder(a.getIngredient()) : getOrder((PlanItem) a.delegate);
            val bi = b.isAggregate() ? getOrder(b.getIngredient()) : getOrder((PlanItem) b.delegate);
            return ai - bi;
        });

        return result;
    }

    private static class SLItemProxy implements SLItem {
        @Delegate
        private final Item delegate;
        @Getter
        private final Collection<PlanItem> components;

        SLItemProxy(Item del) {
            delegate = del;
            components = null;
        }

        public SLItemProxy(Ingredient ing, List<PlanItem> items) {
            if (items.size() == 1) {
                delegate = items.get(0);
                components = items;
                return;
            }
            val firstUnit = Optional.ofNullable(items.get(0))
                    .map(PlanItem::getQuantity)
                    .map(Quantity::getUnit);
            String raw;
            Quantity q;
            if (items.stream()
                    .map(PlanItem::getQuantity)
                    .map(Quantity::getUnit)
                    .allMatch(it ->
                            firstUnit.isEmpty()
                                    ? it == null
                                    : firstUnit.get().equals(it))) {
                // there's a single unit
                q = Quantity.of(items.stream()
                                .map(PlanItem::getQuantity)
                                .map(Quantity::getValue)
                                .map(Number::doubleValue)
                                .reduce(0.0, Double::sum),
                        firstUnit.orElse(null));
                raw = ing.getName() + " (" + q + ")";
            } else {
                q = null; // todo: some sort of aggregate quantity?
                raw = ing.getName() + " (" + items
                        .stream()
                        .filter(it -> it.getQuantity() != null)
                        .map(it -> it.getQuantity().toString())
                        .collect(Collectors.joining(", "))
                        + ")";
            }
            delegate = new ListItem(raw, q, ing, null);
            components = items;
        }

        @Override
        public boolean isAggregate() {
            return components != null;
        }
    }

    private void addItems(Collection<PlanItem> items, PlanItem it) {
        if (it.getType() == ItemType.TO_BUY || it.getType() == ItemType.AD_HOC) {
            items.add(it);
        }
        if (it.hasChildren()) {
            it.getChildren().forEach(c ->
                    addItems(items, c));
        }
    }

}
