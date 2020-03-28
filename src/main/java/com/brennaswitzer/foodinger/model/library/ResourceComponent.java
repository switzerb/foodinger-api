package com.brennaswitzer.foodinger.model.library;

import com.brennaswitzer.foodinger.model.measure.Quantity;

public interface ResourceComponent {
    String getRaw();
    Quantity getQuantity();
    Resource getResource();
    String getNotes();
    String toLabel();
}
