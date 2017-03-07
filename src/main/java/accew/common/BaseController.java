package accew.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * Created by acc on 2017/3/7.
 */
public class BaseController {

    public static MessageResult getSuccessMsg(){
        MessageResult mr = new MessageResult();
        mr.setCode(0);
        mr.setMessage("成功");
        return mr;
    }

    public static MessageResult getCodeAndMsg(Integer code, String message){
        MessageResult mr = new MessageResult();
        mr.setCode(code);
        mr.setMessage(message);
        return mr;
    }

    public static MessageResult getMessageResult(Object info){
        MessageResult mr = getSuccessMsg();
        mr.setInfo(info);
        return mr;
    }

    public String getUserNo(HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getSession().getId();
        Cookie[] cookies = request.getCookies();
        Random random = new Random();
        Integer threeVal = random.nextInt(900) + 100;
        String userNo = "";

        if (cookies != null && cookies.length > 0){
            boolean cookieFlag = true;
            for (Cookie cookie : cookies){
                if (sessionId.equals(cookie.getName())){
                    userNo = cookie.getValue();
                    cookieFlag = false;
                    break;
                }
            }
            if (cookieFlag){// 该用户cookie被清除或者第一次留言, 设置cookie
                for (Cookie cookie : cookies){
                    if ("JSESSIONID".equals(cookie.getName())){
                        sessionId = cookie.getValue();
                        break;
                    }
                }
                userNo = sessionId.substring(0, 5).concat(threeVal.toString());
                Cookie sessionCookie = new Cookie(sessionId, userNo);
                sessionCookie.setMaxAge(3600*24);
                response.addCookie(sessionCookie);
            }
        }else{
            userNo = sessionId.substring(0, 5).concat(threeVal.toString());
            Cookie sessionCookie = new Cookie(sessionId, userNo);
            sessionCookie.setMaxAge(3600*24);
            response.addCookie(sessionCookie);
        }
        return userNo;
    }

}
