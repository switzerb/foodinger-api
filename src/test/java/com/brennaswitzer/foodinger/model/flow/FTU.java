package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.plan.PlanItem;
import com.brennaswitzer.foodinger.model.shop.ShoppingList;
import lombok.val;

final class FTU {

    public static final String INDENT = "  ";

    static String dumpPlan(PlanItem p) {
        return dumpPlan(p, 0);
    }

    static String dumpPlan(PlanItem it, int depth) {
        val sb = new StringBuilder()
                .append(it.getType().getInitial())
                .append(' ')
                .append(INDENT.repeat(depth))
                .append(it.toLabel())
                .append('\n');
        if (it.hasChildren()) {
            it.getChildren().forEach(i ->
                    sb.append(dumpPlan(i, depth + 1)));
        }
        return sb.toString();
    }

    static PlanItem findChildNamed(PlanItem root, String name) {
        if (name.equals(root.getRaw())) {
            return root;
        }
        if (root.hasChildren()) {
            for (PlanItem it : root.getChildren()) {
                PlanItem found = findChildNamed(it, name);
                if (found != null) return found;
            }
        }
        return null;
    }

    static String dumpList(ShoppingList list) {
        val sb = new StringBuilder();
        list.aggregate().forEach(it -> {
            sb.append(it.getRaw());
            if (it.isAggregate()) {
                it.getComponents().forEach(c -> {
                    sb.append("\n")
                            .append(INDENT)
                            .append(c.getQuantity());
                    while ((c = c.getParent()) != null) {
                        sb.append(" / ");
                        if (c.hasIngredient()) {
                            sb.append(c.getIngredient().getName());
                        } else {
                            sb.append(c.getRaw());
                        }
                    }
                });
            }
            sb.append("\n");
        });
        return sb.toString();
    }

}
