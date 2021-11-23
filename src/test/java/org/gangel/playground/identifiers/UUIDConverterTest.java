package org.gangel.playground.identifiers;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UUIDConverterTest {

    @Test
    public void fromHexToBase64() {
        var input = "290d6cba-ce94-455e-b59f-029cf1e395c5";
        var output = UUIDConverter.fromHexToBase64(input);
        assertThat(output).isEqualTo("KQ1sus6URV61nwKc8eOVxQ");
    }

    @Test
    public void fromBase64ToHex() {
        var input = "KQ1sus6URV61nwKc8eOVxQ";
        var output = UUIDConverter.fromBase64ToHex(input);
        assertThat(output).isEqualTo("290d6cba-ce94-455e-b59f-029cf1e395c5");
    }
}