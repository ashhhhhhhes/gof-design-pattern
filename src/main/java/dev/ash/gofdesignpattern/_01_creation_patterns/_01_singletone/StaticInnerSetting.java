package dev.ash.gofdesignpattern._01_creation_patterns._01_singletone;

import java.io.Serializable;

/**
 * static inner 클래스 사용하기.
 */
public class StaticInnerSetting  implements Serializable {

    private StaticInnerSetting() {}

    // * 내부 정적 클래스, getInstance() 최초로 사용되는 시점에  INSTANCE 가 로딩된다.
    private static class SettingHolder {
        private static final StaticInnerSetting INSTANCE = new StaticInnerSetting();
    }

    public static StaticInnerSetting getInstance() {
        return SettingHolder.INSTANCE;
    }

}
