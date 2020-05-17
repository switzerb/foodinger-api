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

    // todo: talk about linear scans....
    private List<Ingredient> orderedIngredients = new LinkedList<>();

    public void orderAfter(Ingredient ing, Ingredient anchor) {
        orderedIngredients.remove(ing);
        int i = ensureOrdered(anchor);
        orderedIngredients.add(i + 1, ing);
    }

    private int ensureOrdered(Ingredient ing) {
        int i = orderedIngredients.indexOf(ing);
        if (i >= 0) return i;
        orderedIngredients.add(0, ing);
        return 0;
    }

    public List<SLItem> aggregate() {
        val allGlerg = new LinkedList<PlanItem>();
        includedPlans.forEach(p ->
                addItems(allGlerg, p));

        val adHoc = allGlerg
                .stream()
                .filter(Predicate.not(Item::hasIngredient))
                .collect(Collectors.toList());
        val byIngredient = allGlerg
                .stream()
                .filter(Item::hasIngredient)
                .collect(Collectors.groupingBy(Item::getIngredient));

        val result = new ArrayList<SLItem>(adHoc.size() + byIngredient.size());
        adHoc.forEach(it ->
                result.add(new SLItemProxy(it)));
        byIngredient.forEach((ing, items) -> {
            ensureOrdered(ing);
            result.add(new SLItemProxy(ing, items));
        });

        result.sort((a, b) -> {
            val ai = orderedIngredients.indexOf(a.getIngredient());
            val bi = orderedIngredients.indexOf(b.getIngredient());
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
