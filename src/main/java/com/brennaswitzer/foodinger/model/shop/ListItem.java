package com.brennaswitzer.foodinger.model.shop;

import lombok.Data;

@Data
abstract class ListItem {

    private String name;
    private boolean deleted;

}
