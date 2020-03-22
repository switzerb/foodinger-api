package com.brennaswitzer.foodinger.model.shop;

import com.brennaswitzer.foodinger.model.plan.PlanItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanListItem extends ListItem {

    private PlanItem planItem;

}
