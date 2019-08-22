# 프로토콜 버퍼(Protocol Buffers)
## #부스트코스  #안드로이드 프로그래밍

> 프로토콜 버퍼가 이번 주제와 관련이 있는 것 같아 같이 올립니다.

* 프로토콜 버퍼(Protocol Buffers)는 구조화된 데이터(객체)를 직렬화하는 방식 중 하나이다.**이진 직렬화 방법**이라 **데이터 변환 및 전송 속도에 최적화**하여 별도의 직렬화 방법을 제시하는 구조이다.
* 따라서 문자열 직렬화 방법 중 하나인 **JSON 보다 확실히 빠르다.**
* 구글에서 만들었고 플랫폼에 독립적인 데이터 직렬화 방식이다. 즉 언어에 독립적이다. 

## 데이터를 표현하기 위한 문서, proto

* 자바에서 프로토콜 버퍼는 특정 언어 또는 플랫폼에 종속되지 않은 방법을 구현하기 위해 직렬화 하기 위한 데이터를 표현하기 위한 문서가 따로 있다. 

> proto 문서의 필드 규칙 명시

* required : message는 이 필드를 정확히 1개만 가진다.
* optional : message는 이 필드를 가지지 않거나 1개만 가진다(1보다 크지 않다).
* repeated : 이 필드는 여러번(0번을 포함해서) 반복될 수 있다. 반복되는 값의 순서는 보존된다.

person.proto
```
required String name;
optional String birth;
repeated int age;
```
=> 이렇게 기술된 person.proto 문서를 **프로토콜 버퍼 컴파일러**를 이용해서 개발하기 원하는 언어(여기서는 자바)로 변환해야 한다. 자바로 변환하게 되면 프로토콜 버퍼 형태의 Person 클래스가 생성된다.
<br>=> 프로토콜 버퍼 컴파일러는 별도로 설치하거나 Gradle, Maven등 의 빌드 도구를 이용하면 된다. 


```java 
Person person = Person.newBuilder()
      .setAge(25)
      .setName("김도형")
      .setBirth("1993-09-28")
      .build();
  ByteArrayOutputStream baos = new ByteArrayOutputStream()
  person.writeTo(baos);
  // 프로토콜 버퍼 직렬화된 데이터
  byte[] serializedMember = baos.toByteArray();    
```
: 이런 식으로 Person 객체에 필드를 set하여 객체를 생성하고 writeTo()을 이용해 ByteArray에 넣어 서버로 보내면 된다. 
<br>: 자바 직렬화와 다른 점은 **데이터 스펙을 표현하기 위한 문서가 존재하는 부분**이다. 그 이외에는 대부분 동일하다. 

도움 얻은 사이트<br>:http://woowabros.github.io/experience/2017/10/17/java-serialize.html
<br>: https://ko.wikipedia.org/wiki/%ED%94%84%EB%A1%9C%ED%86%A0%EC%BD%9C_%EB%B2%84%ED%8D%BC
