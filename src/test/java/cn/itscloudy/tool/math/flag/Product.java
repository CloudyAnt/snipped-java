package cn.itscloudy.tool.math.flag;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product extends FlagsHolder<ProductFlags> {

    private int id;

    private int flags;

    // name, brand, etc.
}
