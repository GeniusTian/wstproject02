package com.atguigu;

/**
 * @author wststart
 * @create 2019-08-06 13:51
 */
public class ETLUtil {
    public static String StringETL(String ori) {
        StringBuilder sb = new StringBuilder();
        String[] split = ori.split("\t");
        if (split.length < 9) return null;
        split[3] = split[3].replaceAll(" ", "");
        for (int i = 0; i < split.length; i++) {
            if (i < 9) {
                if (i == split.length - 1) {
                    sb.append(split[i]);
                } else {
                    sb.append(split[i]).append("\t");
                }
            } else {
                if (i == split.length - 1) {
                    sb.append(split[i]);
                } else {
                    sb.append(split[i]).append("&");
                }
            }
        }
        return sb.toString();
    }
}
