package org.gangel.playground.identifiers;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class UUIDConverter {

    public static String fromHexToBase64(String hexUUID) {
        var uuid = UUID.fromString(hexUUID);
        return generateBase64UUID(uuid);
    }

    public static String fromBase64ToHex(String base64UUID) {
        if (base64UUID == null) {
            throw new IllegalStateException("Expected not null UUID value");
        }
        if (base64UUID.isBlank()) {
            throw new IllegalStateException("Expected not empty UUID value");
        }
        var value = base64UUID;
        if (base64UUID.matches("^.*==$")) {
            if (base64UUID.length() != 24) {
                throw new IllegalStateException("Invalid UUID length. Expected 24 characters");
            }
            value = base64UUID;
        } else {
            if (base64UUID.length() != 22) {
                throw new IllegalStateException("Invalid UUID length. Expected 22 characters");
            }
            value = base64UUID + "==";
        }
        var buffer = Base64.getDecoder().decode(value);
        long msb = 0L;
        for (int i=0; i < 8; ++i) {
            msb <<= 8;
            msb |= (buffer[i] & 0xFF);
        }
        var lsb = 0L;
        for (int i=8; i < 16; ++i) {
            lsb <<= 8;
            lsb |= (buffer[i] & 0xFF);
        }
        var uuid = new UUID(msb, lsb);
        return uuid.toString();
    }

    public static String generateBase64UUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        var encoded = Base64.getEncoder().encodeToString(bb.array());
        return encoded.substring(0, encoded.length() - 2); // skip '=='
    }
}
