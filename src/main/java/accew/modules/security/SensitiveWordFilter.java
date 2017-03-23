package accew.modules.security;

/**
 * Created by acc on 2017/3/23.
 */
public class SensitiveWordFilter {


    public static boolean isContainSensitiveWord(String txt, int matchType){
        return false;
    }

    public static String replaceSensitiveWord(String txt, int matchType){
        return txt.replaceAll("\r\n", "</br>");
    }
}
