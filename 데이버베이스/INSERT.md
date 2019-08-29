# INSERT ��ɾ� 
## #�ν�Ʈ�ڽ� #�ȵ���̵� ���α׷���

### 1. INSERT ��ɾ��? 

=> DB�� �����͸� ���� �� ����ϴ� sql ��ɾ��̴�. INSERT INTO �ڿ� ���̺� �̸��� �����ϰ� ��ȣ�� ���� �����͸� ���� ���� �̸��� �����Ѵ�. VALUES ����� �ڿ� �տ� �����ߴ� ���� ���� �����͵��� �����Ѵ�.

```java
String sql = "INSERT INTO customer(name, age, mobile) VALUES('�赵��',27,'010-2297-5762')";
String sql2 = "INSERT INTO customer(name, age, mobile) VALUES(?,?,?)";
```
: sql ���� �ۼ��ϴ� ���� ��ó�� 2������ �ִ�. sqló�� VALUES �ڿ� �����͵��� ���� �����ϰų� sql2ó�� VALUES �ڿ� ? �� ���� ���� ���ڵ��� Object �迭�� �����Ͽ� ���� execSQL ������ �ִ´�.

### 2. ������ ����غ��� 

```java
    private void insertData(String name, int age, String mobile) {
        println("insertData() ȣ���");

        if (database != null) {
            String sql = "INSERT INTO customer(name, age, mobile) values(?, ?, ?)";
            Object[] params = {
                    name, age, mobile
            };
            database.execSQL(sql, params);

            println("������ �߰���.");

        } else {
            println("���� �����ͺ��̽��� �����ϼ���.");
        }
    }
```
: ��ó�� INSERT ��ɾ ���Ե� sql���� �ۼ��Ͽ� �����ͺ��̽��� execSQL �޼ҵ忡 ���ڷ� ������ �ȴ�.
: sql ���� VALUES �ڿ� ? �� �ۼ��ߴٸ� ? �� �ش��ϴ� ���ڵ��� Object[] �� �ְ� sql���� ���� execSQL �޼ҵ忡 ���ڷ� ������ �ȴ�.