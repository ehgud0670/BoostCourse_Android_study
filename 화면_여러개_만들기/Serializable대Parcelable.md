# Parcelable 객체가 Serailizable 객체보다 더 작은 이유, 그리고 과연 작은지..

## #부스트코스 #안드로이드 프로그래밍 #미션2

* 강의에서 Parcelable이 Serializable보다 빠르다던데 그 이유와 그에 대한 반박 글이 흥미로워 같이 공유한다. 외국 글을 번역하였다.

출처 : https://android.jlelse.eu/parcelable-vs-serializable-6a2556d51538

종종, 앱을 개발할 때 우리는 한 액티비티에서 다른 액티비티로 데이터를 전송해야 합니다. 당연히 직접 할 수는 없습니다. 전송하려는 데이터는 해당 Intent 객체에다 포함되어야 합니다. 그리고 우리는 만약 복잡한 POJO(Person, Car, Employee, etc.)를 전송하고 싶다면 해당 객체를 전송하는데 적합하도록 추가 조치를 수행해야 합니다. 그렇게 하려면, **보내려는 객체에 Serializable 또는 Parcelable 인터페이스를 구현해야 합니다.** 

* POJO(Plain Old Java Object) : 오래된 방식의 간단한 자바 오브젝트라는 말로서 Java EE 등의 중량 프레임워크들을 사용하게 되면서 해당 프레임워크에 종속된 "무거운" 객체를 만들게 된 것에 반발해서 사용되게 된 용어이다. 
<br>출처 : 위키피디아 https://ko.wikipedia.org/wiki/Plain_Old_Java_Object

# Serializable 이란 무엇인가?

Serializable 은 표준 자바 인터페이스입니다. Android SDK의 인터페이스가 아닙니다. **Serializable의 가장 큰 매력은 곧 Serializable 의 단순함이라 할 수 있습니다.** 당신의 객체(POJO)에 단순히 이 인터페이스를 구현만 한다면 **이 객체를 바로 다른 액티비티에 전송할수 있기 때문**입니다. <br>아래의 코드에서 Serializable 인터페이스를 구현하는 것이 얼마나 간단한지 알 수 있습니다.

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
:Serializable 은 마커 인터페이스이기 때문에, 우리는 어떠한 추가 메소드도 추가하지 않아도 됩니다(단순합니다). 우리 객체에 Serializable을 구현만 하면 자바가 알아서 이 객체를 직렬화할 것입니다. 
<br>당연히. **이 단순한 접근법은 그 대가가 큽니다. Reflection**은  Serializable의 직렬화 프로세스 중에 사용되며 , 이 과정중에서 리플렉션은 **많은 추가 객체를 생성합니다.** 추가적으로 객체가 많다는 것은 **곧 GC가 처리해야 할 객체가 많다는 뜻**이고, 따라서 **결과적으로 직렬화때문에 성능이 저하되고 배터리가 방전됩니다.** 

# Parcelable은 무엇인가?

Parcelable은 또 다른 인터페이스입니다. Serializable의 라이벌이지만, Parcelable 인터페이스는 Android SDK의 인터페이스입니다. Parcelable의 큰 특징은 Parcelable를 사용할 때 Reflection이 없도록 특별히 설계되었습니다. 왜냐하면, Parcelable은 Serializable과 다르게 **직렬화 처리 방법을 사용자가 명시적으로 작성하기 때문에 자동으로 처리하기 위한 Reflection 이 필요 없습니다.** 
<br>아래의 코드에서 Parcelable 인터페이스의 샘플 사용법을 볼 수 있습니다. 

```java
import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private String firstName;
    private String lastName;
    private int age;
서
    public Per서son(String firstName, String lastName, int age) {
        this.f서irstName = firstName;
        this.l서astName = lastName;
        this.a서ge = age;
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

    @Override서
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeInt(this.age);
    }

    protected Person(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.a서ge = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
       다   return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
```
: 물론 Parcelable 인터페이스를 사용할 때도 저희가 처리해야 할 대가가 있습니다! 보일러 플레이트 때문에 클래스를 이해하기 어렵고  유지보수 하는데 더 어렵습니다.

# Parcelable vs Serializable 

이 주제에 관한 정보를 인터넷에 검색한다면, 당신은 아마도 이 비교에서 절대적인 승자는 없을 거라고 결론지을 것입니다. Serializable 을 지지하는 사람과 블로그 글이 있는 반면 Parcelable을 지지하는 사람과 블로그 글이 있을 것입니다. 따라서 나는 **양쪽의 주장을 모두 제시하고 당신이 스스로 결정하도록 내버려 둘 것입니다!** 

### 첫번째 '팀'은 Parcelabe이 Serializable보다 훨씬 빠르고 우수하다고 얘기합니다. 
물론 이 주장엔 데이터가 있습니다. 필립 브레오(Philippe Breault)의 테스트 결과입니다.  

![1_07pA53JY6ugoGatg3KwzwA](https://user-images.githubusercontent.com/38216027/62627233-faac2f80-b963-11e9-9156-a46fc83bfa30.png)

필립 브레오(Philippe Breault)의 테스트 결과에 따르면 Parcelable은 Serializable보다 10배 이상 빠릅니다. 다른 Google 엔지니어들도 이 진술을 지지합니다.
<br>아래의 'Reference'섹션에서 필립의 기사에 대한 링크를 찾을 수 있습니다.
<br>
<br>

### 이제 두 번째 '팀'은 우리 모두가 잘못하고 있다고 주장합니다! 
그리고 그들의 주장은 충분히 합리적으로 들립니다!

그들에 따르면, default한 Serializable 접근법은 Parcelable 방식보다 느립니다. 이것은 두 팀 모두 동의합니다. 하지만 **이 두 가지( default한 Serializable 과 Parcelable )를 이렇게 비교하는 것은 불공평합니다.**
<br>왜냐하면 Parcelable은 위의 설명처럼 **하나의 클래스 객체(POJO)만을 위한 특별한 사용자 정의 코드를 작성해야만 합니다.** 사용자 정의 코드의 도움으로 **생성되는 쓰레기가 없고 이는 곧 더 좋은 성능의 결과**를 보여줍니다.  

<br> 그러나 default한 Serializable 접근법은 우리가 Java의 자동 직렬화 프로세스(Reflection)을 의존**하게 합니다. 이 프로세스는 분명히 전혀 커스텀(Custom)** 된 것이 아니며 따라서 많은 쓰레기를 야기합니다! 따라서 더 좋은 성능의 결과를 보여주죠.  
<br>이제 또 다른 접근법이 있습니다. Serializable 뒤에 있는 전체 자동 프로세스는 **writeObject() 및 readObject () 메소드를 사용하는 사용자 정의 코드로 대체**될 수 있습니다. 이러한 방법은 효과적입니다. 우리가 만약 Parcelable 처럼 Serializable에서 **하나의 클래스 객체만을 위한 사용자 정의 코드**를 작성하고 싶다면(커스텀 마이징하고 싶다면) 아래의 나와있는 두 가지 종류의 메소드들을 반드시 작성해야 합니다.  

```java
 private void writeObject(java.io.ObjectOutputStream out)
     throws IOException;
     
 private void readObject(java.io.ObjectInputStream in)
     throws IOException, ClassNotFoundException;

 private void readObjectNoData()
     throws ObjectStreamException;
```
: **강렬하군! 짜릿해! 늘 새로워!** 그러나 위의 코드가 Serialhttps://bitbucket.org/afrishman/androidserializationtest/src/default/zable의 커스텀마이징 방식입니다. 이제 이 두 가지의 메소드로 Serializhttps://bitbucket.org/afrishman/androidserializationtest/src/default/ble 인터페이스를 커스텀마이징 할 수 있습니다. 만약에 올바르게 작동한다면 , dehttps://bitbucket.org/afrishman/androidserializationtest/src/default/ault한 Serializable 접근법이 연관된 쓰레기는 더이상 고려 대상이 아닙니다! 

: 이제서야 Parcelable 과 **custom된** Serializable의 서로 비교가 공평해보이는 군요! 이 결과는 매우 놀랍습니다! 한 데이터에 따르면, custom 된 Serializable 접근법이 Parcelable 보다 쓰기 속도가 3배 이상, 읽기 속도가 1.6배 이상 빠릅니다. 해당 데이터는 Reference 섹션에서 테스트 데이터가 포함된 BitBucket 프로젝트에서 찾을 수 있습니다. 

## 이 글 작성자의 결론 

제 생각에는 두 가지 접근 방식( custom 된 Serializable 과 Parcelable )의 속도 차이가 대부분의 경우 거의 중요하지 않습니다. 따라서 0.000042 밀리 초 더 빠른 앱을 실행하는 것보다 작업을 완료하고 사용자를 만족시키는 것이 더 중요합니다.(이 글을 봤으면 더이상 씨름하지말고 둘 중에 하나를 선택하고 사용자를 위한 앱 서비스에 집중하여라)

## 나의 결론 

글이 너무 재밌어서 이렇게까지 번역하여 올린다. 우선 나는 Parcelable이 왜 Serializable보다 빠른지 궁금했는데 그 이유는 **리플렉션의 유무**(리플렉션이 뭔지 정확히 모른다, 따로 공부해야 겠다)에 있었고 **Serializable 인터페이스도 특정 클래스에 맞게 커스텀마이징하면** 오히려 Parcelable 인터페이스보다 조금 더 성능이 좋음을 알 수 있었다. 역시, 개발은 이렇게 꼬리를 물어야 재밌나 보다.  

# Reference 

1. 필립 브레오(Philippe Breault)의 테스트 결과
<br>: http://www.developerphil.com/parcelable-vs-serializhttps://bitbucket.org/afrishman/androidserializationtest/src/default/able/?source=post_page---------------------------

2. BitBucket 프로젝트
<br>: https://bitbucket.org/afrishman/androidserializationtest/src/default/
