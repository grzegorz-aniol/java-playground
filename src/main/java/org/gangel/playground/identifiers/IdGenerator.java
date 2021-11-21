package org.gangel.playground.identifiers;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class IdGenerator {

    public static String getNewId() {
        return UUID.randomUUID().toString();
    }
    
    public static String getNewId(int length) {
        return UUID.randomUUID().toString().substring(0, (length > 1 ? length - 1 : 1));
    }

    public static String getShortId() {
        var uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        var encoded = Base64.getEncoder().encodeToString(bb.array());
        return encoded.substring(0, encoded.length() - 2); // skip '=='
    }

    public static void main(String[] args) {
        var id1 = IdGenerator.getNewId();
        var id2 = IdGenerator.getShortId();
        System.out.printf("%s (%d characters)%n", id1, id1.length());
        System.out.printf("%s (%d characters)%n", id2, id2.length());
    }
}
