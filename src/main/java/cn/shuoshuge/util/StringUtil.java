package cn.shuoshuge.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class StringUtil extends StringUtils {

    static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static String judgeUtf8 (String str) {

        try {
            return new String(str.getBytes("ISO8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串{}转换异常",str);
            e.printStackTrace();
            throw new RuntimeException("字符串" + str + "转换异常",e);//有问题
        }

    }

}
