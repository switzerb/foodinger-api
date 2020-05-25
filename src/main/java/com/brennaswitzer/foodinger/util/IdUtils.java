package com.brennaswitzer.foodinger.util;

import lombok.val;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public final class IdUtils {

    private static final AtomicInteger id_seq = new AtomicInteger(new Random().nextInt());

    public static Long next(Class<?> clazz) {
        val t = System.currentTimeMillis() / 1000;
        val c = clazz.getName().hashCode();
        val s = id_seq.getAndIncrement();
        return (t << 32) // 32 bits of epoch time
                | ((c & 0xFFFF) << 16) // 16 low-order bits of class name
                | (s & 0xFFFF); // 16 low-order bits of sequence
    }

}
