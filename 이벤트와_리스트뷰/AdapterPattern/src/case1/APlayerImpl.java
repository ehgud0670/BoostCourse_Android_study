// note : 인스턴스에 의한 어댑터 패턴
package case1;

public class APlayerImpl implements APlayer {
    @Override
    public void play(String fileName) {
        System.out.println("(A) " +fileName);
    }

    @Override
    public void stop() {

    }

}
