package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.measure.Quantity;
import com.brennaswitzer.foodinger.model.plan.Plan;
import com.brennaswitzer.foodinger.model.plan.PlanItem;
import com.brennaswitzer.foodinger.model.plan.TaskList;
import com.brennaswitzer.foodinger.model.shop.ShoppingList;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShoppingFlowTests {

    @Test
    public void doTheThing() {
        val napalmBoard = new Plan("Groceries");
        //noinspection UnnecessaryLocalVariable,RedundantCast
        TaskList tl = (TaskList) napalmBoard;
        val napalm = PlanItem.task("napalm");
        tl.addTask(napalm);
        tl.addTask(PlanItem.task("goat cheese"));

        val p = new Plan("Week of 3/21");
        val sat = PlanItem.section("Saturday");
        p.addChild(sat);
        sat.addChild(Quantity.of(1, null), Library.PIZZA_SAUCE);
        sat.addChild(Grocery.WINE);
        val sun = PlanItem.section("Sunday");
        p.addChild(sun);
        sun.addChild(Quantity.of(2, null), Grocery.WINE);
        sun.addChild(Quantity.of(3, null), Library.PIZZA);

        val sl = new ShoppingList();
        sl.setIncludedPlans(List.of(napalmBoard, p));

        System.out.println(FTU.dumpList(sl));
        System.out.println("---");

        sl.orderAfter(Grocery.WINE, Grocery.TOMATOES);
        sl.orderAfter(Grocery.BASIL, Grocery.TOMATOES);
        sl.orderAfter(napalm, Grocery.FLOUR);
        sl.orderAfter(Grocery.YEAST, Grocery.FLOUR);
        sl.orderAfter(Grocery.PEPPERONI, Grocery.BASIL);
        sl.orderAfter(Grocery.WINE, Grocery.BASIL);
        sl.getOrderedIngredients()
                .forEach(System.out::println);
        System.out.println("---");

        System.out.println(FTU.dumpList(sl));

    }

}
