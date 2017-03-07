package accew.common;

/**
 * Created by acc on 2017/3/7.
 */
public class MessageResult<T> {

    private Integer code;
    private String message;
    private T info;

    public MessageResult(){
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
