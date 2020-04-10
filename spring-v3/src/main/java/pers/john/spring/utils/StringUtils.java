
package pers.john.spring.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {

    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_IOS88591 = "ISO-8859-1";

    /**
     * 首字母变小写
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(final String str) {
        return (str == null) || (str.length() == 0);
    }

    /**
     * 判断是否不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    /**
     * 判断是否空白
     * @param str
     * @return
     */
    public static boolean isBlank(final String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0))
            return true;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否不是空白
     * @param str
     * @return
     */
    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * 判断多个字符串全部是否为空
     * @param strings
     * @return
     */
    public static boolean isAllEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串其中任意一个是否为空
     * @param strings
     * @return
     */
    public static boolean isHasEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkValue为 null 或者为 "" 时返回 defaultValue
     * @param checkValue
     * @param defaultValue
     * @return
     */
    public static String isEmpty(String checkValue, String defaultValue) {
        return isEmpty(checkValue) ? defaultValue : checkValue;
    }

    /**
     * 拼接字符串
     * @param list
     * @param append
     * @return list[0] + append + list[1] + append + list[2] + ..... + list[n]
     */
    public static String appendString(List<String> list, String append) {
        StringBuilder builder = new StringBuilder();
        if(list != null && list.size() > 0 ) {
            for(String tmp : list) {
                builder.append(tmp).append(append);
            }
            return builder.substring(0, builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * 转换字符串编码
     * @param srcStr 原始字符串
     * @param srcCharset 原始编码
     * @param targetCharset 目标编码
     * @return
     */
    public static String changeCharset(String srcStr, String srcCharset, String targetCharset)
        throws UnsupportedEncodingException {
        return new String(srcStr.getBytes(srcCharset), targetCharset);
    }

    public static List<String> split(String str, String regex) {
        if(isNotEmpty(str)) {
            return Arrays.asList(str.split(regex));
        }
        return null;
    }

    public static boolean isPhoneNumber(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static int[] stringArrToIntArr(String[] arr) {
        int[] intArr;
        if(arr == null || arr.length == 0){
            intArr = new int[0];
        } else {
            intArr = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                intArr[i] = Integer.parseInt(arr[i]);
            }
        }
        return intArr;
    }

    public static final String[] ESC_ARRAY = {"\\\\n", "\\\\r", "\\\\t", "\\\\v"};
    public static final String[] ESC_ARRAY_REPLACE = {"\\n", "\\r", "\\t", "\\v"};

    /**
     * 处理转义字符
     * @param str
     * @return
     */
    public static String replaceESC(String str) {
        for(int i = 0; i < ESC_ARRAY.length; i++) {
            str = str.replaceAll(ESC_ARRAY[i], ESC_ARRAY_REPLACE[i]);
        }
        return str;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11111");
        list.add("22222");
        List<String> sub = list.subList(1, 100);
        System.out.println(sub.get(0));
    }

}
