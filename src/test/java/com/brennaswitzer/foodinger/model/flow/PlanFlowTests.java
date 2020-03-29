package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.measure.Quantity;
import com.brennaswitzer.foodinger.model.measure.Unit;
import com.brennaswitzer.foodinger.model.plan.Plan;
import com.brennaswitzer.foodinger.model.plan.PlanItem;
import lombok.val;
import org.junit.jupiter.api.Test;

public class PlanFlowTests {

    @Test
    public void somethingSoNeat() {
        val p = new Plan("Week of 3/21");
        val sat = PlanItem.section("Saturday");
        p.addChild(sat);
        val sun = PlanItem.section("Sunday");
        p.addChild(sun);
        sun.addChild(Quantity.of(3, null), Library.PIZZA);
        val crust = findChildNamed(sun, "Pizza Crust");
        assert crust != null;
        sat.addChild(crust);
        System.out.println(dumpPlan(p));
    }

    @Test
    public void andWine() {
        val p = new Plan("Saturday");
        p.addChild(Quantity.of(2, null), Library.PIZZA_CRUST);
        p.addChild(Quantity.of(2, Unit.of("bottles")), Grocery.WINE);
        p.addChild(Grocery.WINE);
        p.addChild(PlanItem.adhoc(Grocery.WINE.getName()));
        System.out.println(dumpPlan(p));
    }

    private String dumpPlan(PlanItem p) {
        return dumpPlan(p, 0);
    }

    private String dumpPlan(PlanItem it, int depth) {
        val sb = new StringBuilder()
                .append(it.getType().getInitial())
                .append(' ')
                .append("  ".repeat(depth))
                .append(it.toLabel())
                .append('\n');
        if (it.hasChildren()) {
            it.getChildren().forEach(i ->
                    sb.append(dumpPlan(i, depth + 1)));
        }
        return sb.toString();
    }

    private PlanItem findChildNamed(PlanItem root, String name) {
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

}
