package accew.common.enums;

/**
 * Created by acc on 2017/3/21.
 */
public enum CommentType {

    //类型(01普通留言；03主题；)
    ordinary("01", "普通留言"),
    theme("03", "主题");

    private String value;
    private String name;

    CommentType(String value, String name){
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
