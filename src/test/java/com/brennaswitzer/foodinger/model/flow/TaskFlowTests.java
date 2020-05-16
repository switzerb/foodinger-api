package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.plan.Plan;
import com.brennaswitzer.foodinger.model.plan.PlanItem;
import com.brennaswitzer.foodinger.model.plan.TaskList;
import org.junit.jupiter.api.Test;

public class TaskFlowTests {

    @Test
    public void makeSomeTasks() {
        TaskList tl = new Plan("Groceries");
        tl.addTask(PlanItem.task("chocolate milk"));
        tl.addTask(PlanItem.task("rice wine vinegar"));
        tl.addTask(PlanItem.task("goat cheese"));
        System.out.println(FTU.dumpPlan((Plan) tl));
    }

}
