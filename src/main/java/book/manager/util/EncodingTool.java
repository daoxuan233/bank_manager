package book.manager.util;

import java.io.UnsupportedEncodingException;

/**
 * 修改默认编码方式 ISO-8859-1 为 UTF-8
 * @author hw
 * @date 2022-10-22
 * @dec 描述
 */
public class EncodingTool {
    public static String encodingStr(String str){
        try {
            return new String(str.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
