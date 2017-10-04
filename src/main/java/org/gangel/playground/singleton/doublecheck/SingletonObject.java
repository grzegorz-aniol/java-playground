package org.gangel.playground.singleton.doublecheck;

/**
 * Example of Double Check patter for generating one object instance (Singleton) in multi-threading application
 * 
 * @author Grzegorz_Aniol
 *
 */
final public class SingletonObject {

    private static volatile SingletonObject instance;
    
    public static SingletonObject getInstance() {
        // return an instance if it's created already
        if (instance != null) {
            return instance; 
        }
        // otherwise synchronize all requesting threads
        synchronized(SingletonObject.class) {
            // check instance value - theoretically other threads which were waiting could execute this block of code
            if (instance == null) {
                instance = new SingletonObject();
            }
        }
        return instance; 
    }
}
