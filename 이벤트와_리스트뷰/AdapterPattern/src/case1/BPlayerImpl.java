// note : 인스턴스에 의한 어댑터 패턴
package case1;

public class BPlayerImpl implements BPlayer {
    @Override
    public void playFile(String fileName) {
        System.out.println("(B) " + fileName);
    }

    @Override
    public void stopFile() {
    }
}
