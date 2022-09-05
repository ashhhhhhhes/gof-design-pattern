package dev.ash.gofdesignpattern.singletone;

/**
 * 싱글톤 구현 방법 04
 * 이른 초기화.
 */
public class SimpleSingleton04 {

    private static final SimpleSingleton04 instance = new SimpleSingleton04();

    private SimpleSingleton04() {}

    public static SimpleSingleton04 getInstance() {
        return instance;
    }
}
