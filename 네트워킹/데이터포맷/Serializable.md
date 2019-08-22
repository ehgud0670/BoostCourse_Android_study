# Serializable 인터페이스
## #부스트코스 #안드로이드 프로그래밍

* Serializable도 직렬화하는 방법 중 하나로 이번 주제와 관련되어 있는 것 같아 글을 올립니다.

### Serializable이란?

Serializable 은 표준 자바 인터페이스로 자바 직렬화 방법이다. java.io.Serializable 인터페이스를 객체가 단순히 구현만 한다면 객체로서 데이터를 보낼 수 있다.  


Person.java
```java
import java.io.Serializable;

public class Person implements Serializable {

    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

Serializable 객체를 byteArray에 넣는 경우의 코드
```java
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Client {

    public static void main(String[] args) throws IOException {
        Person person = new Person("김", "도형", 27);
        byte[] serializedPerson;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(person);

            // serializedPerson -> 직렬화된 person 객체
            serializedPerson = baos.toByteArray();
        }

        // 바이트 배열로 생성된 직렬화 데이터를 base64로 변환 
        System.out.println(Base64.getEncoder().encodeToString(serializedPerson));
    }
}
```
: 서버에 보내진 않았다.
<br>: ObjectOutputStream 의 writeObject()로 객체로서 byteArray에 넣음을 알 수 있다. 

## Serializable로 구현한 객체가 네트워크 패킷으로서 적절하지 않은 이유  

* 바로 확장성의 문제가 있기 때문이다. 자바 프로그램에서 자바 프로그램으로 보내는 경우에는 상관 없지만 자바 파일에서 다른 언어의 프로그램으로 보내는 경우 Serializable 은 사용할 수 없다.
<br>=> 언어에 종속적이기 때문에 네트워크 패킷으로서 적절치 못하다.

* 혹 다른 언어에서 Serializable 객체를 푼다 하더라도 복잡하고 번거롭다.

### => 따라서 JSON을 사용하자( 언어에 독립적이다 ). 