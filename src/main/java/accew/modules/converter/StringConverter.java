package accew.modules.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by CrazyFive on 2017/3/6.
 */
public class StringConverter implements Converter<String, String> {


    public String convert(String source) {
        if (source == null){
            return null;
        }else {
            return source.trim();
        }
    }
}
