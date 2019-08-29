# SELECT ��ɾ� 
## #�ν�Ʈ �ڽ� #�ȵ���̵� ���α׷��� 


### 1. SELECT ��ɾ��?

=> DB�� �����͸� ��ȸ�� �� ����ϴ� sql ��ɾ��̴�. SELECT �ڿ� ã�� ���� ���� �̸����� �����ϰ� FROM ����� �ڿ� ã�����ϴ� ���̺� �̸��� ����Ѵ�. �׸��� Ư�� ���� ã�� ���� ��쿡 WHERE ���� ����ϰ� WHERE ��ɾ� �ڿ� Ư�� ������ �����Ѵ�. 

```java
String sql = "SELECT name, age, mobile FROM customer WHERE age = 27";
```
: "customer ��� ���̺��� age�� 27�� ����� ã�� �� ����� �� name, age , mobile �� ������ ������ ��ȸ�ض�" ��� sql �����̴�. 


### 2. �ȵ���̵忡�� SELECT ��ɾ� ����ϱ� 

�ȵ���̵忡�� db�� �����͸� ��ȸ�Ϸ��� ���� ���� SELECT ���� ���Ե� sql���� �����ϰ� rawQuery()��� �޼ҵ忡 sql���� ���ڷ� �ִ´�. ���� rawQuery() �޼ҵ忡�� Cursor ��ü�� ��ȯ�Ǹ� Cursor�� ���� Ȯ���� ���ڵ�(��)�� ����ϸ� �ȴ�. 

����
```java
private void selectData(String tableName) {
        println("selectData() ȣ���.");

        if (database != null) {
            String sql = "SELECT name, age, mobile FROM " + tableName + " WHERE name = '�赵��'";
            Cursor cursor = database.rawQuery(sql, null);
            println("��ȸ�� ������ ���� : " + cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                println("#" + i + " -> " + name + ", " + age + ", " + mobile);
            }

            cursor.close();

        } else {
            println("���� �����ͺ��̽��� �����ϼ���.");
        }
    }
```
: sql ������ WHERE �ڿ� ���ڿ� ������ ���� �������� �˻��Ϸ��� ''�� ���ڿ� �յڿ� �ٿ��� �Ѵ�. (�� :'�赵��')
<br>: cursor.moveToNext()�� ȣ���ϸ� ���� ���ڵ��� ������ ������ �����´�.
<br>:cursor�� getString�̳� getInt�� �ش� ���ڵ��� ���� ������ ������ ��ȯ�Ѵ�. ���⼭ �ε����� ���� sql���� SELECT ��ɾ� ���� ������ column ���̴�. ���� name�� �ε��� 0 , age�� �ε��� 1 , mobile �� �ε��� 2�̴�.
 