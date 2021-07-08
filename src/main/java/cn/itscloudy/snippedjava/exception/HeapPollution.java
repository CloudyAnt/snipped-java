package cn.itscloudy.snippedjava.exception;

public class HeapPollution {

    @SafeVarargs
    public final <Y> Y[] polluter(Y... ys) {
        return ys;
    }

    public <X> X[] caller(X x) {
        // the inner call of polluter() cannot recognize X, so it return Object[]
        return polluter(x);
    }

}
