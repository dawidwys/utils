package com.blogspot.yetanothercoders.collections;

import com.google.common.collect.Table;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.collect.Sets.cartesianProduct;
import static org.junit.Assert.assertEquals;

public class Collectors2Test {

    @Test
    public void toTableTest() throws Exception {

        //Given
        Set<Integer> bases = IntStream.range(1, 4).boxed().collect(Collectors.toSet());
        Set<Integer> exponents = IntStream.range(1, 4).boxed().collect(Collectors.toSet());

        Set<List<Integer>> cartesianProduct = cartesianProduct(bases, exponents);

        //When
        Table<Integer, Integer, Double> powers = cartesianProduct.stream().collect(Collectors2.toTable(
                //rowMapper
                entry -> entry.get(0),
                //columnMapper
                entry -> entry.get(1),
                //valueMapper
                entry -> Math.pow(entry.get(0), entry.get(1))
        ));

        //Then
        for (int base = 1; base < 4; base++) {
            for (int exponent = 1; exponent < 4; exponent++) {
                assertEquals(new Double(Math.pow(base, exponent)), powers.get(base, exponent));
            }
        }
    }

    @Test
    public void toTableTestParallel() throws Exception {

        //Given
        final int baseMax = 10000;
        final int exponentMax = 10;
        Set<Integer> bases = IntStream.range(1, baseMax).boxed().collect(Collectors.toSet());
        Set<Integer> exponents = IntStream.range(1, exponentMax).boxed().collect(Collectors.toSet());

        Set<List<Integer>> cartesianProduct = cartesianProduct(bases, exponents);

        //When
        Table<Integer, Integer, Double> powers = cartesianProduct.parallelStream().collect(Collectors2.toTable(
                //rowMapper
                entry -> entry.get(0),
                //columnMapper
                entry -> entry.get(1),
                //valueMapper
                entry -> Math.pow(entry.get(0), entry.get(1))
        ));

        //Then
        for (int base = 1; base < baseMax; base++) {
            for (int exponent = 1; exponent < exponentMax; exponent++) {
                assertEquals(new Double(Math.pow(base, exponent)), powers.get(base, exponent));
            }
        }
    }
}