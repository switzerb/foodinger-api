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
        dumpPlan(p);
    }

    private void dumpPlan(Plan p) {
        dumpPlan(p, 0);
    }

    private void dumpPlan(PlanItem it, int depth) {
        System.out.print("  ".repeat(depth));
        System.out.println(it.toLabel());
        if (it.hasChildren()) {
            it.getChildren().forEach(i -> dumpPlan(i, depth + 1));
        }
    }

}
