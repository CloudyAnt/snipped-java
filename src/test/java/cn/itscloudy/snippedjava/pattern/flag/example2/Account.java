package cn.itscloudy.snippedjava.pattern.flag.example2;

import cn.itscloudy.snippedjava.pattern.flag.FlagsHolder;
import lombok.Getter;
import lombok.Setter;

/**
 * This is an entity class
 */
@Getter
@Setter
public class Account implements FlagsHolder<Role> {

    private int id;

    private int roles;

    @Override
    public int currentFlags() {
        return roles;
    }

    @Override
    public void assignFlags(int newFlags) {
        this.roles = newFlags;
    }
}
