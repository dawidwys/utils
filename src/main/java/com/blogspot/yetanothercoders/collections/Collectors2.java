package com.blogspot.yetanothercoders.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

import static com.google.common.collect.HashBasedTable.create;

/**
 * Set of custom collectors similar to {@link java.util.stream.Collectors}
 * @author Dawid Wysakowicz <wysakowicz.dawid@gmail.com>
 * @since 2014-06-22.
 */
public final class Collectors2 {

    private Collectors2() {
    }

    /**
     * Returns a {@code Collector} that accumulates elements into a
     * {@link com.google.common.collect.Table} whose rows, columns and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * @implNote This implementation does not provide that the underlying table is concurrent
     *
     * @param <T> the type of the input elements
     * @param <R> the output type of the row mapping function
     * @param <C> the output type of the column mapping function
     * @param <V> the output type of the value mapping function
     * @param rowMapper a mapping function to produce rows
     * @param columnMapper a mapping function to produce columns
     * @param valueMapper a mapping function to produce values
     * @return a {@code Collector} which collects elements into a {@code Table}
     * whose columns, keys and values are the result of applying mapping functions to
     * the input elements
     */
    public static <T, R, C, V> Collector<T, ?, Table<R, C, V>> toTable(
            Function<? super T, ? extends R> rowMapper,
            Function<? super T, ? extends C> columnMapper,
            Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                //Supplier
                HashBasedTable::create,

                //Accumulator
                (BiConsumer<Table<R, C, V>, T>) (objectObjectObjectHashBasedTable, o) ->
                        objectObjectObjectHashBasedTable.put(rowMapper.apply(o), columnMapper.apply(o), valueMapper.apply(o)),

                //Combiner
                (rcvHashBasedTable, rcvHashBasedTable2) -> {
                    Table<R, C, V> newTable = create(rcvHashBasedTable);
                    newTable.putAll(rcvHashBasedTable2);
                    return newTable;
                },

                Collector.Characteristics.UNORDERED
        );
    }
}
