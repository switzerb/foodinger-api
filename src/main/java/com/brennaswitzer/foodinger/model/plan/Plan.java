package com.brennaswitzer.foodinger.model.plan;

import com.brennaswitzer.foodinger.model.AccessControlled;
import com.brennaswitzer.foodinger.model.Acl;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Plan extends PlanItem implements AccessControlled, TaskList {

    private Acl acl;

    public Plan(String name) {
        super(name, ItemType.PLAN);
    }

    @Override
    public void addTask(Task t) {
        addChild(t instanceof PlanItem
                ? (PlanItem) t
                : PlanItem.task(t.getTaskName()));
    }

    @Override
    public void addTask(String t) {
        addChild(PlanItem.task(t));
    }
}
