package accew.common.enums;

import java.io.Serializable;

/**
 * Created by acc on 2017/3/10.
 */
public class SysEnum implements Serializable{

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
