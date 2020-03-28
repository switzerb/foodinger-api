package com.brennaswitzer.foodinger.model.flow;

import com.brennaswitzer.foodinger.model.task.Task;
import com.brennaswitzer.foodinger.model.task.TaskList;
import lombok.val;
import org.junit.jupiter.api.Test;

public class TaskFlowTests {

    @Test
    public void makeSomeTasks() {
        val tl = new TaskList("Groceries");
        tl.addTask(new Task("chocolate milk"));
        tl.addTask(new Task("rice wine vinegar"));
        tl.addTask(new Task("goat cheese"));
        System.out.println(tl);
    }

}
