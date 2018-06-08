package org.gangel.playground.optional;

import java.time.Duration;
import java.util.Optional;

public class OptionalMapping {

    public static class Recording {

        private Duration duration;

        public Recording(Duration duration) {
            this.duration = duration;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }
    }


    public static void main(String args[]) {

        Optional<Duration> d = Optional.ofNullable(
            Optional.ofNullable(new Recording(null))
            .map(Recording::getDuration)
            .map(dd -> {
                System.out.println("Test:" + dd);
                return dd;
            } )
            .orElseGet(() -> null)
        );

        System.out.println(d);
    }
}
