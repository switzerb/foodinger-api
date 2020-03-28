package com.brennaswitzer.foodinger.model.task;

import com.brennaswitzer.foodinger.model.AccessControlled;
import com.brennaswitzer.foodinger.model.Acl;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskList implements AccessControlled {

    private String name;
    private Acl acl;
    private List<Task> tasks;

    public TaskList() {}

    public TaskList(String name) {
        this.name = name;
    }

    public void addTask(Task t) {
        if (tasks == null) tasks = new ArrayList<>();
        tasks.add(t);
        t.setList(this);
    }

}
