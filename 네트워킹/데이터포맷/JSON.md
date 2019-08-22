# JSON
## #부스트코스 #안드로이드 프로그래밍

## JSON(JavaScript Object Notation , 자바스크립트 객체 표기법)

### JSON이란?

* JSON 의 JavaScript Object Notation의 줄인말로 , 자바스크립트 객체 포맷을 데이터로 주고받을 때 사용할 수 있도록 **문자열**로 표현한 것이다.

* JSON은 자바스크립트 객체 포맷이지만 , 완벽하게 언어로부터 독립적이라 많은 언어에서 데이터 포맷으로 사용한다. 대부분의 프로그래밍 언어에서 JSON 포맷의 데이터를 핸들링 할 수 있는 라이브러리를 제공한다.<br>(**Gson 라이브러리**를 이용하면 JSON 문자열을 자바 객체로 변환해주기 때문에 문제없이 자바 객체를 데이터로서 전달 가능하다.) 

  * Serializable을 통한 직렬화, 역직렬화는 자바 언어에서만 가능해서 네트워크 포맷으로는 JSON을 사용한다. 
   
* JSON은 경량(Lightweight)의 DATA 교환 형식이다. JSON은 사람이 읽고 쓰기에 용이하며, 기계가 분석하고 생성함에도 용이하다.

### JSON의 장점과 단점 

> JSON은 문자열 형식의 데이터다. 

* 장점 : 데이터 로깅이 편리해진다. 
<br>=> 타입 자체가 String이라 따로 조치를 취하지 않고 출력하여 데이터 정보를 확인할 수 있다. 
<br>=> 로그(log)는 컴퓨터나 서버(Server)등에서 유저(User)의 플레이 정보를 시간에 따라 남기는 기록을 뜻한다. 

꿀팁 : Gson에서 GsonBuilder 객체의 setPrettyPrinting 메소드를 사용하면 데이터 정보를 보기좋게(pretty) 출력할 수 있다. 

```java
Gson gson = new GsonBuilder().setPrettyPrinting().create();
```

* 단점 : 위에서 JSON은 경량의 DATA 교환 형식이라고 했지만, 타입이 String이라 binary 데이터를 직접 교환하는 거에 비해 더 많은 데이터를 보내야 하기 때문에 용량이 커진다는 단점이 있다.

### Gson(구글 Gson , Google Gson)

* JSON의 자바 오브젝트의 직렬화, 역직렬화를 해주는 오픈 소스 자바 라이브러리

도움얻은 사이트 : <br>
1. https://ko.wikipedia.org/wiki/JSON
2. https://ko.wikipedia.org/wiki/Gson
3. https://github.com/june0122/TIL/blob/master/JAVA/LAB/0607.md
4. https://www.json.org/json-ko.html

