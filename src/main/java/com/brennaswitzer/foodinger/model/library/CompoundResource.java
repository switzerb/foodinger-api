package com.brennaswitzer.foodinger.model.library;

import java.util.List;

public interface CompoundResource extends Resource {

    List<? extends ResourceComponent> getComponents();

}
