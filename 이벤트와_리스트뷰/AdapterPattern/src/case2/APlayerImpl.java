// note : 클래스에 의한 어댑터 패턴
package case2;

public class APlayerImpl implements APlayer {
    @Override
    public void play(String fileName) {
        System.out.println("(A) " +fileName);
    }

    @Override
    public void stop() {

    }
}
