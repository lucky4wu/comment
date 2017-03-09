package accew.modules.utils;

import java.io.Serializable;

/**
 * Created by acc on 2017/3/9.
 */
public class LoginSession implements Serializable{

    private String userNo;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
