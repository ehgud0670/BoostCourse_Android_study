# ByteArray
## #부스트코스 #안드로이드 프로그래밍 

* ByteArray 도 데이터 전달 포맷의 한 종류로서 왜 ByteArray를 쓰지 않고 JSON을 써야하는 지 알면 좋을 것 같아 정리합니다.

### 데이터를 따로따로 보내는게 아니라 ByteArray로 보내는 이유 

: 서로 관련되어 있는 데이터( ex : 3 + 5 )들을 따로 보낼때 context switch 때문에 미처 데이터를 보내지 못하면, 못 보낸 데이터 때문에 상대편에서 작업을 제대로 못하는 경우가 생기게 된다.<br>따라서 온전한 패킷으로 보내는 것이 좋으므로 , 서로 연관된 데이터들을 ByteArray에 넣어 한꺼번에 보내는 것이 옳다.

```java 
ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
DataOutputStream dos = new DataOutputStream(bos);
dos.writeChar('+');
dos.writeInt(3);
dos.writeInt(5);
```
: 이런식으로 관련된 데이터들(3 + 5)를 ByteArray로 싸매서 함께 보낸다.

### ByteArray의 경계처리를 확실히 해야 하는 이유 

=> 네트워크의 Stream은 데이터의 경계가 존재하지 않기 때문에 연속적으로 이어져서 보내진다. 그러면 두 개가 겹친것을 하나로 인식하여 데이터를 잘못 받아오는 경우가 생기게 된다. 한마디로 데이터가 쓰레기 값이 되는 것이다. 

> 해결책 

1. 데이터의 길이 정보를 함께 보낸다. 
<br>=> 길이 정보를 함께 보내면 서버 쪽으로 데이터가 겹쳐져서 오더라도 길이 단위로 자르면 모든 데이터를 한 개씩 구분할 수 있기 때문에 문제가 해결된다.  

2. 데이터를 토큰으로 구분한다. 
<br>=> 앞이나 뒤에 토큰을 붙여 데이터를 전달하면 토큰으로 각 데이터들을 구분 가능해져 문제가 해결된다. 
<br>=> 문제점 : 토큰을 인식하려면 1byte씩 확인해야 하는 성능에 좋지 않는 방법이고 처리하는 것도 번거롭다. 그래서 보통 길이로 각 데이터를 구분한다. 

산술 연산과 관련된 정보를 데이터로 보낸다고 가정해보자.
```
// Protocol - Calculation
// => Byte Array
// [op: char] [lhs: int] [rhs : int]
// '+' , '-' , '*'
```
=> op는 operation으로 산술 연산자로 '+', '-', '*'와 같은 것을 의미한다.
<br>=> lhs는 Left Hand Side로 왼쪽의 피연산자를 의미한다. 
<br>=> rhs는 Right Hand Side로 오른쪽의 피연산자를 의미한다.

클라이언트에서 연산 정보를 ByteArray로 보내기 
```java
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client2 {
  public static void main(String[] args) {

    char op = '+';
    int lhs = 10;
    int rhs = 32;

    try {
      Socket socket = new Socket("127.0.0.1", 5000);

      try (OutputStream os = socket.getOutputStream();
           DataOutputStream dos2 = new DataOutputStream(os)) {

        for (int i = 0; i < 100000; i++) {
          ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
          DataOutputStream dos = new DataOutputStream(bos);

          dos.writeChar(op);
          dos.writeInt(lhs);
          dos.writeInt(rhs);

          byte[] data = bos.toByteArray();
          int len = bos.size();

          dos2.writeInt(len);
          os.write(data, 0, len);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```
: 클라이언트에서 서버로 10 + 32 를 보내는 코드이다.
<br>: bos에 writeXXX() 메소드로 각 데이터들을 넣는다. 
<br>: bos의 길이를 len에 할당하고 len을 dos2에 넣어 길이 정보도 함께 보낸다.(경계 처리)

서버에서 클라이언트에게 정보 받고 출력하기
```java 
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String[] args) {
    try {
      byte[] data = new byte[1024];
      ServerSocket serverSocket = new ServerSocket(5000);
      Socket socket = serverSocket.accept();


      try (InputStream is = socket.getInputStream();
           DataInputStream dis2 = new DataInputStream(is)) {

        int count = 0;
        while (true) {
          int packetLen;
          try {
            packetLen = dis2.readInt();
          } catch(EOFException e){
            break;
          }

          int ret = is.read(data, 0, packetLen);
          if (ret == -1) {
            break;
          }

          ByteArrayInputStream bis = new ByteArrayInputStream(data);
          DataInputStream dis = new DataInputStream(bis);

          char op = dis.readChar();
          int lhs = dis.readInt();
          int rhs = dis.readInt();

          System.out.printf("%5d - %d %c %d \n", count++, lhs, op, rhs);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```
: 서버에서 클라이언트에게 데이터를 byteArray 형식으로 받는 코드이다. 
<br>: dis2로 받은 데이터의 길이 정보를 packetLen에 할당한다.
<br>: parketLen을 기준으로 구분하여 data에 데이터를 넣는다. dis의 readXXX() 메소드를 이용해 op, lhs, rhs의 데이터 정보를 받고 출력한다. 


### 왜 ByteArray의 단점 

* 객체지향적이지 않다. 따라서 번거롭다.
<br>: 위의 경우는 byteArray에 데이터들을 일일히 넣어서 함께 보내는 것 뿐이지 객체 자체를 보내는 방식이 아니다. 따라서 객체지향에서는 **최대한 객체지향적으로 접근하라는 말에 위배**되므로 사용하지 않는 것이 좋다. 또 객체지향적이지 않기 때문에 writeXXX()나  readXXX()처럼 메소드로 따로 ByteArray의 데이터들을 쓰거나 읽어야 하기 때문에 **번거롭다.** JSON은 객체 자체를 보내는 것이기 때문에 객체 지향적이고 따라서 번거롭지 않다. 

=> 물론 Serializable을 이용하여 byteArray에 객체를 넣어 보낼 수 있지만 이번 포스팅의 경우는 위의 코드처럼 byteArray에  각 데이터를 따로 write하거나 read하는 경우입니다.