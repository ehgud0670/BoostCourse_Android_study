// note : 클래스에 의한 어댑터 패턴
package case2;

public class BPlayerImpl extends BPlayer {
    @Override
    public void playFile(String fileName) {
        System.out.println("(B) " + fileName);
    }

    @Override
    public void stopFile() {
    }
}
