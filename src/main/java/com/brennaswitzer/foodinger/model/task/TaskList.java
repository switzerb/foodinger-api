package com.brennaswitzer.foodinger.model.task;

import com.brennaswitzer.foodinger.model.AccessControlled;
import com.brennaswitzer.foodinger.model.Acl;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskList extends Task implements AccessControlled {

    private Acl acl;

}
