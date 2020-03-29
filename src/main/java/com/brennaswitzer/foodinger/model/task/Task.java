package com.brennaswitzer.foodinger.model.task;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"list"})
public class Task {

    private String name;
    private boolean deleted;

    private TaskList list;

    public Task() {}

    public Task(String name) {
        this.name = name;
    }

}
