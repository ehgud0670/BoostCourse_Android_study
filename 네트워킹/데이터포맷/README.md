# 데이터 교환 포맷
## #부스트코스 ##안드로이드 프로그래밍

* XML
* **JSON** (**J**ava**S**cript **O**bject **N**otation)
* **Protocol Buffers**


=> 요즘엔 **XML을 사용하지 않고** JSON과 Protocol Buffers를 7:3 정도의 비율로 데이터 교환 포맷으로서 사용한다고 한다. 

* Serializable
* ByteArray

=> Serializable 인터페이스는 **자바에서만의 인터페이스이기 때문에** 네트워크 패킷으로는 적절하지 못하다.
<br>=> ByteArray는 전달할 때 **경계 처리**를 제대로 해야 하기 때문에 번거롭고, **객체지향적인 방식이 아니기 때문에**<br>네트워크 패킷으로는 적절하지 못하다.
