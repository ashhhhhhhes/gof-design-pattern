package dev.ash.gofdesignpattern._01_creation_patterns._01_singletone;

/**
 * 싱글톤 패턴 구현 방법 04
 * double-checked locking
 */
public class SimpleSingleton03 {

    private static volatile SimpleSingleton03 instance;

    private SimpleSingleton03() {}

    public static SimpleSingleton03 getInstance() {
        if (instance == null) {
            synchronized (SimpleSingleton03.class) {
                if (instance == null) {
                    instance = new SimpleSingleton03();
                }
            }
        }

        return instance;
    }
}
