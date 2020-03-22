package com.brennaswitzer.foodinger.model.library;

import java.util.List;

public interface CompoundResource<E extends ResourceComponent> extends Resource {

    List<E> getComponents();

}
