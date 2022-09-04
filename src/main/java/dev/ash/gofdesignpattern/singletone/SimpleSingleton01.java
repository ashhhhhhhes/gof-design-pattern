package dev.ash.gofdesignpattern.singletone;

/**
 * 싱글톤 생성 방법 01.
 * private 생성좌와 static 메소드
 * (2022-09-05 AM 12:03)
 */
public class SimpleSingleton01 {

    private static SimpleSingleton01 instance;

    // * 외부에서 해각 인스턴스를 new 를 통해 생성하지 못하기 위해, 생성자를 private 으로 만든다.
    private SimpleSingleton01() {}

    // * 인스턴스를 반환하받을 수 있는 함수.
    public static SimpleSingleton01 getInstance() {
        if (instance == null) {
            instance = new SimpleSingleton01();
        }

        return instance;
    }

}
