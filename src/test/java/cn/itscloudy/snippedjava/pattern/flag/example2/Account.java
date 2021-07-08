package cn.itscloudy.snippedjava.pattern.flag.example2;

import cn.itscloudy.snippedjava.pattern.flag.FlagsHolder;
import lombok.Getter;
import lombok.Setter;

/**
 * This is an entity class
 */
@Getter
@Setter
public class Account extends FlagsHolder<Role> {

    private int id;

    private int roles;

    @Override
    protected int getFlags() {
        return roles;
    }

    @Override
    protected void setFlags(int newFlags) {
        this.roles = newFlags;
    }
}
