package accew.common.security;

import accew.modules.security.AntUrlPathMatcher;
import accew.modules.security.UrlMatcher;

/**
 * Created by acc on 2017/3/23.
 */
public class BaseInterceptor {

    protected String include;

    private UrlMatcher urlMatcher = AntUrlPathMatcher.getInstance();
    /**
     * checkAction:(檢查是否符合包含的情形)
     *
     * @param uri
     * @return (true=可以通过, false=需要檢查)
     */
    protected boolean checkAction(String uri) {
        boolean result = true;
        String[] includeStr = new String[0];
        if (include != null && include.length() > 0){
            includeStr = include.split(",");
        }
        for (String method : includeStr) {
            if (urlMatcher.pathMatchesUri(method, uri)){
                result = false;
                break;
            }
        }

        return result;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        if (include != null) {
            this.include = include.replaceAll("\\s*", "");
        }
        else {
            this.include = include;
        }
    }

}
