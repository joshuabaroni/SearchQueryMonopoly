package model;

public class Pair<T1, T2> {
    private T1 p1;
    private T2 p2;
//    private T3 p3; // image
    
    public Pair() {}
    
    public T1 getP1() {
        return p1;
    }
    
    public T2 getP2() {
        return p2;
    }
    
    public void setP1(T1 p1) {
        this.p1 = p1;
    }
    
    public void setP2(T2 p2) {
        this.p2 = p2;
    }
    
    @Override
    public String toString() {
        return "\n\t\t\"" + p1 + "\",\n\t\t\"" + p2 + "\"";
    }
}
