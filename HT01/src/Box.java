import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> box;
    private float accuracy = 0.1f;

    public Box() {
        box = new ArrayList<T>();
    }

    public void add(T fruit) {
        box.add(fruit);
    }

    public void setAccuracy(float acc) {
        accuracy = acc;
    }

    public float getWeight() {
        float w = 0f;
        for (T fruit: box) {
            w += fruit.getWeight();
        }
        return  w;
    }

    public boolean compare(Box<?> other) {
        return Math.abs(this.getWeight() - other.getWeight()) < accuracy;
    }

    public void tiltOutTo(Box<T> other) {
        for (T f : box) {
            other.add(f);
        }
        box.clear();
    }

    public void println() {
        System.out.println(box);
    }
}
