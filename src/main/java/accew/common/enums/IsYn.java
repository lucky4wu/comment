package accew.common.enums;

/**
 * Created by CrazyFive on 2017/3/11.
 */
public enum IsYn {

    YES("Y", "是"),
    NO("N", "否");

    private String value;
    private String name;

    IsYn(String value, String name){
        this.value = value;
        this.name = name;
    }

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
