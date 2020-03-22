package com.brennaswitzer.foodinger.model.shop;

import com.brennaswitzer.foodinger.model.task.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskListItem extends ListItem {

    private Task task;

}
