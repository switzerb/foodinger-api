package com.brennaswitzer.foodinger.model.shop;

import com.brennaswitzer.foodinger.model.Item;
import com.brennaswitzer.foodinger.model.Owned;
import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.model.library.Ingredient;
import com.brennaswitzer.foodinger.model.measure.Quantity;
import com.brennaswitzer.foodinger.model.plan.ItemType;
import com.brennaswitzer.foodinger.model.plan.Plan;
import com.brennaswitzer.foodinger.model.plan.PlanItem;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.val;

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

    private User owner;

    private Collection<Plan> includedPlans;

    private List<Ingredient> orderedIngredients;

    private Map<PlanItem, Ingredient> adHocOrder;

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
        byIngredient.forEach((ing, items) ->
                result.add(new SLItemProxy(ing, items)));

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

        public SLItemProxy(Ingredient ing, PlanItem item) {
            delegate = item;
            components = Collections.singletonList(item);
        }

        public SLItemProxy(Ingredient ing, List<PlanItem> items) {
            val raw = ing.getName() + " (" + items
                    .stream()
                    .map(it -> it.getQuantity().toString())
                    .collect(Collectors.joining(", "))
                    + ")";
            delegate = new Item() {
                @Override
                public String getRaw() {
                    return raw;
                }

                @Override
                public Quantity getQuantity() {
                    return null;
                }

                @Override
                public Ingredient getIngredient() {
                    return ing;
                }

                @Override
                public String getNotes() {
                    return "";
                }
            };
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
