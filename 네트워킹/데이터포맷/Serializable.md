# Serializable �������̽�
## #�ν�Ʈ�ڽ� #�ȵ���̵� ���α׷���

* Serializable�� ����ȭ�ϴ� ��� �� �ϳ��� �̹� ������ ���õǾ� �ִ� �� ���� ���� �ø��ϴ�.

### Serializable�̶�?

Serializable �� ǥ�� �ڹ� �������̽��� �ڹ� ����ȭ ����̴�. java.io.Serializable �������̽��� ��ü�� �ܼ��� ������ �Ѵٸ� ��ü�μ� �����͸� ���� �� �ִ�.  


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

Serializable ��ü�� byteArray�� �ִ� ����� �ڵ�
```java
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Client {

    public static void main(String[] args) throws IOException {
        Person person = new Person("��", "����", 27);
        byte[] serializedPerson;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(person);

            // serializedPerson -> ����ȭ�� person ��ü
            serializedPerson = baos.toByteArray();
        }

        // ����Ʈ �迭�� ������ ����ȭ �����͸� base64�� ��ȯ 
        System.out.println(Base64.getEncoder().encodeToString(serializedPerson));
    }
}
```
: ������ ������ �ʾҴ�.
<br>: ObjectOutputStream �� writeObject()�� ��ü�μ� byteArray�� ������ �� �� �ִ�. 

## Serializable�� ������ ��ü�� ��Ʈ��ũ ��Ŷ���μ� �������� ���� ����  

* �ٷ� Ȯ�强�� ������ �ֱ� �����̴�. �ڹ� ���α׷����� �ڹ� ���α׷����� ������ ��쿡�� ��� ������ �ڹ� ���Ͽ��� �ٸ� ����� ���α׷����� ������ ��� Serializable �� ����� �� ����.
<br>=> �� �������̱� ������ ��Ʈ��ũ ��Ŷ���μ� ����ġ ���ϴ�.

* Ȥ �ٸ� ���� Serializable ��ü�� Ǭ�� �ϴ��� �����ϰ� ���ŷӴ�.

### => ���� JSON�� �������( �� �������̴� ). 