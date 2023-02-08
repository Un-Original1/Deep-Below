package com.unoriginal.mimicfish.world.util;

import java.util.Random;

public class modRand {

    public static <T> T choice(T[] array) {
        Random rand = new Random();
        return choice(array, rand);
    }

    public static <T> T choice(T[] array, Random rand) {
        int i = rand.nextInt(array.length);
        return array[i];
    }
}
