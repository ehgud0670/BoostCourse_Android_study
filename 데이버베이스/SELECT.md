# SELECT 명령어 
## #부스트 코스 #안드로이드 프로그래밍 


### 1. SELECT 명령어란?

=> DB의 데이터를 조회할 때 사용하는 sql 명령어이다. SELECT 뒤에 찾고 싶은 열의 이름들을 나열하고 FROM 예약어 뒤에 찾고자하는 테이블 이름을 명시한다. 그리고 특정 행을 찾고 싶은 경우에 WHERE 예약어를 사용하고 WHERE 명령어 뒤에 특정 조건을 나열한다. 

```java
String sql = "SELECT name, age, mobile FROM customer WHERE age = 27";
```
: "customer 라는 테이블에서 age가 27인 행들을 찾고 그 행들의 열 name, age , mobile 의 데이터 값들을 조회해라" 라는 sql 구문이다. 


### 2. 안드로이드에서 SELECT 명령어 사용하기 

안드로이드에서 db의 데이터를 조회하려면 위와 같이 SELECT 문이 포함된 sql문을 선언하고 rawQuery()라는 메소드에 sql문을 인자로 넣는다. 이후 rawQuery() 메소드에서 Cursor 객체가 반환되며 Cursor를 통해 확인한 레코드(행)을 사용하면 된다. 

예제
```java
private void selectData(String tableName) {
        println("selectData() 호출됨.");

        if (database != null) {
            String sql = "SELECT name, age, mobile FROM " + tableName + " WHERE name = '김도형'";
            Cursor cursor = database.rawQuery(sql, null);
            println("조회된 데이터 개수 : " + cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                println("#" + i + " -> " + name + ", " + age + ", " + mobile);
            }

            cursor.close();

        } else {
            println("먼저 데이터베이스를 오픈하세요.");
        }
    }
```
: sql 구문의 WHERE 뒤에 문자열 형태인 열을 기준으로 검색하려면 ''을 문자열 앞뒤에 붙여야 한다. (예 :'김도형')
<br>: cursor.moveToNext()를 호출하면 다음 레코드의 데이터 값들을 가져온다.
<br>:cursor의 getString이나 getInt는 해당 레코드의 열의 데이터 값들을 반환한다. 여기서 인덱스는 위의 sql문의 SELECT 명령어 뒤의 나열된 column 순이다. 따라서 name이 인덱스 0 , age가 인덱스 1 , mobile 이 인덱스 2이다.
 