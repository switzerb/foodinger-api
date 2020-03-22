package com.brennaswitzer.foodinger.model.shop;

import com.brennaswitzer.foodinger.model.Owned;
import com.brennaswitzer.foodinger.model.User;
import com.brennaswitzer.foodinger.model.plan.Plan;
import com.brennaswitzer.foodinger.model.task.TaskList;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class ShoppingList implements Owned {

    private User owner;

    private Collection<TaskList> includedLists;
    private Collection<Plan> includedPlans;

    private List<ListItem> items;

}
