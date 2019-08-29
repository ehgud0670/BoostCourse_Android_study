# INSERT 명령어 
## #부스트코스 #안드로이드 프로그래밍

### 1. INSERT 명령어란? 

=> DB에 데이터를 넣을 때 사용하는 sql 명령어이다. INSERT INTO 뒤에 테이블 이름을 기입하고 괄호을 열고 데이터를 넣을 열의 이름을 열거한다. VALUES 예약어 뒤에 앞에 열거했던 열의 실제 데이터들을 기입한다.

```java
String sql = "INSERT INTO customer(name, age, mobile) VALUES('김도형',27,'010-2297-5762')";
String sql2 = "INSERT INTO customer(name, age, mobile) VALUES(?,?,?)";
```
: sql 문을 작성하는 법은 위처럼 2가지가 있다. sql처럼 VALUES 뒤에 데이터들을 직접 기입하거나 sql2처럼 VALUES 뒤에 ? 로 적은 다음 인자들을 Object 배열에 선언하여 같이 execSQL 구문에 넣는다.

### 2. 실제로 사용해보기 

```java
    private void insertData(String name, int age, String mobile) {
        println("insertData() 호출됨");

        if (database != null) {
            String sql = "INSERT INTO customer(name, age, mobile) values(?, ?, ?)";
            Object[] params = {
                    name, age, mobile
            };
            database.execSQL(sql, params);

            println("데이터 추가함.");

        } else {
            println("먼저 데이터베이스를 오픈하세요.");
        }
    }
```
: 위처럼 INSERT 명령어가 포함된 sql문을 작성하여 데이터베이스의 execSQL 메소드에 인자로 넣으면 된다.
: sql 문에 VALUES 뒤에 ? 로 작성했다면 ? 에 해당하는 인자들을 Object[] 에 넣고 sql문과 같이 execSQL 메소드에 인자로 넣으면 된다.