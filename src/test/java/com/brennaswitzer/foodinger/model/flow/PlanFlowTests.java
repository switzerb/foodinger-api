package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.plan.Plan;
import com.brennaswitzer.foodinger.model.plan.PlanItem;
import lombok.val;
import org.junit.jupiter.api.Test;

public class PlanFlowTests {

    @Test
    public void somethingSoNeat() {
        val p = new Plan("Week of 3/21");
        val sat = new PlanItem("Saturday");
        p.addChild(sat);
        val sun = new PlanItem("Sunday");
        p.addChild(sun);
        sun.addChild(Library.PIZZA);
        val crust = findChildNamed(sun, "Pizza Crust");
        assert crust != null;
        sat.addChild(crust);
        System.out.println(dumpPlan(p));
    }

    private String dumpPlan(Plan p) {
        return dumpPlan(p, 0);
    }

    private String dumpPlan(PlanItem it, int depth) {
        val sb = new StringBuilder()
                .append(it.getType().name(), 0, 1)
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
        if (name.equals(root.getName())) {
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
