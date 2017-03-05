package accew.modules.converter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by CrazyFive on 2017/3/6.
 */
public class DateConverter implements Converter<String, Date> {

    public Date convert(String source){
        try {
            if (StringUtils.isEmpty(source)){
                return null;
            }else {
                String value = source.trim();
                String[] dateFormat = new String[] {"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "EEE MMM dd HH:mm:ss Z yyyy"};
                return DateUtils.parseDate(value, dateFormat);
            }
        } catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
}
