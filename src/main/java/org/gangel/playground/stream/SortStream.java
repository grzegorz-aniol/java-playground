package org.gangel.playground.stream;

import java.util.Optional;
import java.util.stream.Stream;

public class SortStream {

    public static void main(String[] args) {
        Optional<Integer> max = Stream.of(5, 1, 6, 9, 11, 3, 5, 2, 7)
            .min(Integer::compare);

        System.out.println(max);
    }

}
