package cn.itscloudy.snippedjava.trick.ex;

public class HeapPollution {

    @SafeVarargs // it does nothing, just remove the warning
    public final <Y> Y[] polluter(Y... ys) {
        return ys;
    }

    public <X> X[] polluterCaller(X x) {
        // the polluter will return Object[] not X[], cause it cannot recognize X
        return polluter(x);
    }

    public <B> B normal(B b) {
        return b;
    }

    public <A> A normalCaller(A a) {
        return normal(a);
    }
}
