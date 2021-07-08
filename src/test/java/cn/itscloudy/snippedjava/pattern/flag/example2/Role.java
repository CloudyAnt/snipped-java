package cn.itscloudy.snippedjava.pattern.flag.example2;

import cn.itscloudy.snippedjava.pattern.flag.Flag;
import lombok.Getter;
import lombok.Setter;

/**
 * This is an entity class
 */
@Getter
@Setter
public class Role implements Flag {

    private int id;

    private String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int ordinal() {
        return id;
    }

    // work details, privileges, etc.
}

