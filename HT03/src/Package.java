import java.io.Serializable;

public class Package implements Serializable {
    private int content;
    private long[] load = new long[5000000];

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }
}
