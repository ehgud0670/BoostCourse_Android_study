# ByteArray
## #�ν�Ʈ�ڽ� #�ȵ���̵� ���α׷��� 

* ByteArray �� ������ ���� ������ �� �����μ� �� ByteArray�� ���� �ʰ� JSON�� ����ϴ� �� �˸� ���� �� ���� �����մϴ�.

### �����͸� ���ε��� �����°� �ƴ϶� ByteArray�� ������ ���� 

: ���� ���õǾ� �ִ� ������( ex : 3 + 5 )���� ���� ������ context switch ������ ��ó �����͸� ������ ���ϸ�, �� ���� ������ ������ ������� �۾��� ����� ���ϴ� ��찡 ����� �ȴ�.<br>���� ������ ��Ŷ���� ������ ���� �����Ƿ� , ���� ������ �����͵��� ByteArray�� �־� �Ѳ����� ������ ���� �Ǵ�.

```java 
ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
DataOutputStream dos = new DataOutputStream(bos);
dos.writeChar('+');
dos.writeInt(3);
dos.writeInt(5);
```
: �̷������� ���õ� �����͵�(3 + 5)�� ByteArray�� �θż� �Բ� ������.

### ByteArray�� ���ó���� Ȯ���� �ؾ� �ϴ� ���� 

=> ��Ʈ��ũ�� Stream�� �������� ��谡 �������� �ʱ� ������ ���������� �̾����� ��������. �׷��� �� ���� ��ģ���� �ϳ��� �ν��Ͽ� �����͸� �߸� �޾ƿ��� ��찡 ����� �ȴ�. �Ѹ���� �����Ͱ� ������ ���� �Ǵ� ���̴�. 

> �ذ�å 

1. �������� ���� ������ �Բ� ������. 
<br>=> ���� ������ �Բ� ������ ���� ������ �����Ͱ� �������� ������ ���� ������ �ڸ��� ��� �����͸� �� ���� ������ �� �ֱ� ������ ������ �ذ�ȴ�.  

2. �����͸� ��ū���� �����Ѵ�. 
<br>=> ���̳� �ڿ� ��ū�� �ٿ� �����͸� �����ϸ� ��ū���� �� �����͵��� ���� �������� ������ �ذ�ȴ�. 
<br>=> ������ : ��ū�� �ν��Ϸ��� 1byte�� Ȯ���ؾ� �ϴ� ���ɿ� ���� �ʴ� ����̰� ó���ϴ� �͵� ���ŷӴ�. �׷��� ���� ���̷� �� �����͸� �����Ѵ�. 

��� ����� ���õ� ������ �����ͷ� �����ٰ� �����غ���.
```
// Protocol - Calculation
// => Byte Array
// [op: char] [lhs: int] [rhs : int]
// '+' , '-' , '*'
```
=> op�� operation���� ��� �����ڷ� '+', '-', '*'�� ���� ���� �ǹ��Ѵ�.
<br>=> lhs�� Left Hand Side�� ������ �ǿ����ڸ� �ǹ��Ѵ�. 
<br>=> rhs�� Right Hand Side�� �������� �ǿ����ڸ� �ǹ��Ѵ�.

Ŭ���̾�Ʈ���� ���� ������ ByteArray�� ������ 
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
: Ŭ���̾�Ʈ���� ������ 10 + 32 �� ������ �ڵ��̴�.
<br>: bos�� writeXXX() �޼ҵ�� �� �����͵��� �ִ´�. 
<br>: bos�� ���̸� len�� �Ҵ��ϰ� len�� dos2�� �־� ���� ������ �Բ� ������.(��� ó��)

�������� Ŭ���̾�Ʈ���� ���� �ް� ����ϱ�
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
: �������� Ŭ���̾�Ʈ���� �����͸� byteArray �������� �޴� �ڵ��̴�. 
<br>: dis2�� ���� �������� ���� ������ packetLen�� �Ҵ��Ѵ�.
<br>: parketLen�� �������� �����Ͽ� data�� �����͸� �ִ´�. dis�� readXXX() �޼ҵ带 �̿��� op, lhs, rhs�� ������ ������ �ް� ����Ѵ�. 


### �� ByteArray�� ���� 

* ��ü���������� �ʴ�. ���� ���ŷӴ�.
<br>: ���� ���� byteArray�� �����͵��� ������ �־ �Բ� ������ �� ������ ��ü ��ü�� ������ ����� �ƴϴ�. ���� ��ü���⿡���� **�ִ��� ��ü���������� �����϶�� ���� ����**�ǹǷ� ������� �ʴ� ���� ����. �� ��ü���������� �ʱ� ������ writeXXX()��  readXXX()ó�� �޼ҵ�� ���� ByteArray�� �����͵��� ���ų� �о�� �ϱ� ������ **���ŷӴ�.** JSON�� ��ü ��ü�� ������ ���̱� ������ ��ü �������̰� ���� ���ŷ��� �ʴ�. 

=> ���� Serializable�� �̿��Ͽ� byteArray�� ��ü�� �־� ���� �� ������ �̹� �������� ���� ���� �ڵ�ó�� byteArray��  �� �����͸� ���� write�ϰų� read�ϴ� ����Դϴ�.