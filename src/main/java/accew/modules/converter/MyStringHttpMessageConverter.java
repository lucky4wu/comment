package accew.modules.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;

/**
 * Created by CrazyFive on 2017/3/6.
 */
public class MyStringHttpMessageConverter extends StringHttpMessageConverter  {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public MyStringHttpMessageConverter (){
        super(DEFAULT_CHARSET);
    }


}
