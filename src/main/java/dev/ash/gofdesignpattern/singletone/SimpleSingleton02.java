package dev.ash.gofdesignpattern.singletone;


/**
 * 싱글톤 패턴 구현 방법 02
 *  동기화(synchronized)를 사용해 멀티쓰레드 환경에 안전하게 만드는 방법
 * (2022-09-05 AM 12:19)
 */
public class SimpleSingleton02 {

    private static SimpleSingleton02 instance;

    // * 외부에서 해각 인스턴스를 new 를 통해 생성하지 못하기 위해, 생성자를 private 으로 만든다.
    private SimpleSingleton02() {}

    // * 인스턴스를 반환하받을 수 있는 함수.
    public static synchronized SimpleSingleton02 getInstance() {
        if (instance == null) {
            instance = new SimpleSingleton02();
        }

        return instance;
    }

}
