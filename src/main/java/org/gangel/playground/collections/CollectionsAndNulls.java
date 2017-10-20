package org.gangel.playground.collections;

import java.util.HashMap;
import java.util.TreeMap;

public class CollectionsAndNulls {

    public static void addNullKeyToHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put(null, "value2");
    }
    
    public static void addNullValueToHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key2", null);
    }
    
    public static void addNullKeyToTreeMap() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key", "value");
        map.put(null, "value2");
    }
    
    public static void addNullValueToTreeMap() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key", "value");
        map.put("key2", null);
    }    
    
    public static void main(String[] args) {
        try {
            System.out.println("addNullKeyToHashMap");
            addNullKeyToHashMap();
        } catch (Throwable e) { 
            System.err.println(e.getMessage());
        }
        
        try {
            System.out.println("addNullValueToHashMap");
            addNullValueToHashMap();
        } catch (Throwable e) { 
            System.err.println(e.getMessage());
        }
        
        try {
            System.out.println("addNullKeyToTreeMap");
            addNullKeyToTreeMap();
        } catch (Throwable e) { 
            System.err.println(e.getMessage());
        }
        
        try {
            System.out.println("addNullValueToTreeMap");
            addNullValueToTreeMap();
        } catch (Throwable e) { 
            System.err.println(e.getMessage());
        }        
        
        System.out.println("Done.");
    }
    
}
