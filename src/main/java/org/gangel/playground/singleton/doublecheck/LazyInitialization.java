package org.gangel.playground.singleton.doublecheck;

public class LazyInitialization {
    
    public LazyInitialization() {
        System.out.println("Constructor: creating new instance.");
    }
    
    private static class Holder {
        /**
         * Nested classes are loaded on demand so instance will be initialized then too.
         */
        public static final LazyInitialization instance = new LazyInitialization();
    }
    
    public static LazyInitialization getInstance() {
        return Holder.instance;
    }

}
