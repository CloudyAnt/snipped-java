package cn.itscloudy.snippedjava.pattern.flag.example1;

import cn.itscloudy.snippedjava.pattern.flag.FlagsHolder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product implements FlagsHolder<ProductFlags> {

    private int id;

    private int flags;

    @Override
    public int currentFlags() {
        return flags;
    }

    @Override
    public void assignFlags(int newFlags) {
        this.flags = newFlags;
    }

    // name, brand, etc.
}
