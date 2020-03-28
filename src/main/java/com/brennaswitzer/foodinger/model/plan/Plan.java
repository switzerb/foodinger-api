package com.brennaswitzer.foodinger.model.plan;

import com.brennaswitzer.foodinger.model.AccessControlled;
import com.brennaswitzer.foodinger.model.Acl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Plan extends PlanItem implements AccessControlled {

    private Acl acl;

    public Plan() {}

    public Plan(String name) {
        super(name);
    }

}
