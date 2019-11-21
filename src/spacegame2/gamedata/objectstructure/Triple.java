package spacegame2.gamedata.objectstructure;

public class Triple<T, T1, T2> {
    private T first;
    private T1 second;
    private T2 third;

    public Triple(T first, T1 second, T2 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public T1 getSecond() {
        return second;
    }

    public T2 getThird() {
        return third;
    }
}
