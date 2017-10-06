package org.gangel.playground.performance;

import java.util.UUID;

public class IdGenerator {

    public static String getNewId() {
        return UUID.randomUUID().toString();
    }
    
    public static String getNewId(int length) {
        return UUID.randomUUID().toString().substring(0, (length > 1 ? length - 1 : 1));
    }
    
}
