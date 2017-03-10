package accew.common.enums;

/**
 * Created by acc on 2017/3/10.
 */
public enum CommentStatus {

    init("01", "初始化"),
    checked("03", "已审核"),
    closed("05", "已关闭");

    private String value;
    private String name;

    CommentStatus(String value, String name){
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
