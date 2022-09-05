
# 싱글톤 패턴

```
인스턴스를 오직 하나만 제공하는 클래스. 
```


### 🏷️싱글톤 패턴이 사용 되어야하는 이유

시스템 런타임, 환경 세팅 정보 등 인스턴스가 여러개일 경우 문제가 발생할 여지가 있는 경우가 있다.
이때 인스턴스를 오직 하나만 만들어 제공하는 클래스가 필요하다.

![img.png](assets/img.png)

(그림 1) 싱글톤 클래스에 필요한 인자, 함수.
 


| 명칭                        | 유형       | 설명                                                              |
|:--------------------------|:---------|:----------------------------------------------------------------|
| instance: Singleton       | 인스턴스     | 하나만 존재해야하는 인스턴스 객체                                              |
| getInstance(): Singleton  | 함수       | 싱글톤을 반환 받을때 사용하는 함수, 오직 하나의 인스턴스만 반환한다.                         |

## [01] 싱글톤 구현 방법 1

---

### private 생성자에 static 메소드.
#### 가장 간단한 싱글톤 패턴을 구현하는 방법.

```
public class SimpleSingleton {

    private static SimpleSingleton instance;

    // * 외부에서 해각 인스턴스를 new 를 통해 생성하지 못하기 위해, 생성자를 private 으로 만든다.
    private SimpleSingleton() {}

    // * 인스턴스를 반환하받을 수 있는 함수.
    public static SimpleSingleton getInstance() {
        if (instance == null) {
            instance = new SimpleSingleton();
        }

        return instance;
    }

}
```

위 방법에서의 문제점으로는 getInstance()가 멀티쓰레드 환경에서 안전하지 않는 방식이다. 
그 이유는 당죽 멀티 쓰레드가 실행 되면서 동시에 각각 새로운 인스턴스를 생성할 수 있기 때문이다.


## 싱글톤 구현 방법 2

---

#### synchronized 키워들들 추가하여 메소드 단위로 동기화를 적용하여 멀티쓰레드 환경에서 안전하게 만드는 방법.

```

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

```

자바에서 synchronized 키워드를 사용할 경우 해당 메서드에 하나의 쓰레드만 사용이 가능하고, (Lock을 통해 제어)  
이후 다른 쓰레드는 사용중인 쓰레드의 작업이 마무리 될 때 까지 기다리게 된다. 그로 인해 성능에 불이익이 있을 수 있다.

쓰레드 A -> 메소드 사용 (LOCK)  -> 사용완료 LOCK 해제
쓰레드 B -> (LOCK) 으로 인해 사용 대기 -> 해당 메소드 사용.

static getInstance() 메소드 동기화시 사용하는 락(lOCK)은 해당 클래스 단위로 락이 발생 된다. 스태틱 메소드를 동기화 하는 방식은 이 메소드르 가진 클래스 기준으로 이루어 진다.
jvm 안에 클래스 객체는 클래스 당 하나만 존재할 수 있으므로 같은 클래스에 대해서는 오직 한 쓰레드만 동기화된 스태틱 메소드를 실행할 수 있다.


---
## Singleton 패턴 구현 방법 3
#### 이른 초기화 (eager initialization)을 사용하는 방법

static 으로 생성된 변수에 싱글톤 객체를 선언했기 때문에 클래스 로더에 의해 클래스가 로딩될 때 
싱글톤 객체가 생성된다. 또 클래스 로더에 의해 클래슥 최초 로딩 될 떄 객체가 생성되므로 Thread-safe 한 방식이다.
단 단점으로는 싱글톤 객체의 사용 유무와 상관없이 클래스가 로딩 되는 시점에서 항상 싱글톤 객체가 생성되고, 메모리를 잡고 있기 때문에 비효율적일 수 있다.

이른 초기화 방식은 항상 싱글톤 객체가 필요하거나 객체 생성 비용이 크게 들어나지 않는 경우 사용할때 유용하다.

```
public class Settings {
    private static final Settings INSTANCE = new Settings();
    
    private Settings() {}
    
    public static Settings getInstnace() {
        return INSTANCE;
    }

}
```

## singleton 패턴 구현 방법 4

---
#### double checked locking 으로 효율적인 동기화 블럭 만들기

instance 객체 생성 여부를 체크할 때 null 일경우 synchronized 블록에서 또한번 검증하도록 하는 방식이다.
만약 instance 객체가 생성되지 않았을 경우, synchronized 블록에 의해 locking이 되므로 첫 번째 쓰레드만 instance를 생성하게 되고
그 이후 진입한 쓰레드들은 대기상태가 된다. instance 생성 여부를 두번 검증하기 때문에 double-checked locking 이라한다.

장점으로는 getInstance() 함수 자체에 synchronized 시키는 것보다 성능적으로 우수하며, 필요할 때만 instnace 객체를 생성할 수 있어 효율적이다.

인스턴스 변수를 정의할 때 volatile 를 추가해줘야만 동기화가 된다. 

```
public static Settings {
    
    private static volatile Settings instance;
    
    private Settings() {}
    
    public static Settings getInstance() {
        if (instance == null) {
            //
            synchronzied (Settings.class) {
                if (instance == null) { 
                    instance == new Settings();
                }   
            }
        }
    }    
    
}
```


## singleton 패턴 구현 방법 5

---
#### static inner 클래스 사용하기

싱글톤 패턴 구현 시 권장하는 방법 중 하나다. 싱글톤 객체를 생성할 클래스 내부에 static inner 클래스를 사용하여 getInstance()를 호출 하는 시점에
SettingHolder 를 통해 인스턴스를 받아오는 방식이다. 

Settings.getInstance() 함수를 실행하는 시점(최초로 내부 클래스가 사용하는 시점)에 JVM에 SettingHolder 가 로드되기 때문에 지연 초기화(lazy initialization)이라 볼 수 있다.
그러므로 INSTANCE 변수에 static final 을 사용하였다고 하더라도, SettingHolder 가 최초로 사용되는 시점에 초기화가 된다. 
이른 초기화(early initialization) 방법과 달리 불필요하게 메모리를 차지 할 필요가 없어지게 된다. 


```
public static Settings {
    
    private static volatile Settings instance;
    
    private Settings() {}
    
    private static class SettingHolder {
        private static final Settings INSTANCE = new Settings();
    }
    
    public static Settings getInstance() {
      return Settings.INSTANCE;
    }    
    
}
```