package com.brennaswitzer.foodinger.model.plan;

import com.brennaswitzer.foodinger.model.AccessControlled;

public interface TaskList extends AccessControlled {

    String getTaskName();

    void addTask(Task t);

    void addTask(String t);

}
