package cn.itscloudy.snippedjava.trick.ex;

public class HeapPollution {

    @SafeVarargs // it does nothing, just remove the warning
    public final <Y> Y[] polluter(Y... ys) {
        return ys;
    }

    public <X> X[] caller(X x) {
        // the inner call of polluter() cannot recognize X, so it return Object[]
        return polluter(x);
    }

}
