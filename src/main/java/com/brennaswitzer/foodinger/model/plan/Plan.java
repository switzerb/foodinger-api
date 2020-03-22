package com.brennaswitzer.foodinger.model.plan;

import com.brennaswitzer.foodinger.model.AccessControlled;
import com.brennaswitzer.foodinger.model.Acl;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Plan extends PlanItem implements AccessControlled {

    private Acl acl;

}
