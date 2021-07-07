package cn.itscloudy.pattern.flag.example1;

import cn.itscloudy.pattern.flag.FlagsHolder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends FlagsHolder<ProductFlags> {

    private int id;

    private int flags;

    // name, brand, etc.
}
