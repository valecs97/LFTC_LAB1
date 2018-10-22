package model;

public class Tuple<X, Y> extends BaseEntity {
    public final X x;
    public final Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public Tuple(Long id, X x, Y y) {
        super(id);
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
