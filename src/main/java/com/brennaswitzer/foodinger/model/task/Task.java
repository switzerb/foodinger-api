package com.brennaswitzer.foodinger.model.task;

import lombok.Data;

import java.util.List;

@Data
public class Task {

    private String name;
    private boolean deleted;

    private List<Task> children;
    private Task parent;

}
