package com.hujinwen.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by joe on 2020/4/9
 * <p>
 * 转换工具包
 */
public class ConvertUtils {
    /**
     * byte[]转为指定类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Object fromBytes(byte[] bytes, Class<T> clazz) {
        if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)
                || clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class)) {
            return bytesToInt(bytes);
        } else if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)) {
            return bytesToLong(bytes);
        } else if (clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class)) {
            return bytesToFloat(bytes);
        } else if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)) {
            return bytesToDouble(bytes);
        } else if (clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(char.class)) {
            return bytesToChar(bytes);
        } else if (clazz.isEnum()) {
            return Enum.valueOf((Class) clazz, new String(bytes, StandardCharsets.UTF_8));
        } else if (clazz.isAssignableFrom(String.class)) {
            return new String(bytes, StandardCharsets.UTF_8);
        } else if (clazz.isAssignableFrom(byte[].class) || clazz.isAssignableFrom(Byte[].class)) {
            return bytes;
        } else if (clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class)) {
            return bytes.length > 0 && bytes[0] > 0;
        }
        return null;
    }

    /**
     * byte[]转int
     */
    public static int bytesToInt(byte[] bytes) {
        int i = (bytes[0] << 24) & 0xFF000000;
        i |= (bytes[1] << 16) & 0xFF0000;
        i |= (bytes[2] << 8) & 0xFF00;
        i |= bytes[3] & 0xFF;
        return i;
    }

    /**
     * int转byte[]
     */
    public static byte[] intToBytes(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (i >>> 24);
        b[1] = (byte) (i >>> 16);
        b[2] = (byte) (i >>> 8);
        b[3] = (byte) i;
        return b;
    }

    /**
     * long转byte[]
     *
     * @see java.io.DataOutputStream
     */
    public static byte[] longToBytes(long l) {
        byte[] b = new byte[8];
        b[0] = (byte) (l >>> 56);
        b[1] = (byte) (l >>> 48);
        b[2] = (byte) (l >>> 40);
        b[3] = (byte) (l >>> 32);
        b[4] = (byte) (l >>> 24);
        b[5] = (byte) (l >>> 16);
        b[6] = (byte) (l >>> 8);
        b[7] = (byte) (l);
        return b;
    }

    /**
     * byte[]转long
     *
     * @see java.io.DataInputStream
     */
    public static long bytesToLong(byte[] bytes) {
        return (((long) bytes[0] << 56) +
                ((long) (bytes[1] & 255) << 48) +
                ((long) (bytes[2] & 255) << 40) +
                ((long) (bytes[3] & 255) << 32) +
                ((long) (bytes[4] & 255) << 24) +
                ((bytes[5] & 255) << 16) +
                ((bytes[6] & 255) << 8) +
                (bytes[7] & 255));
    }

    /**
     * byte[]转char
     */
    public static char bytesToChar(byte[] bytes) {
        char c = (char) ((bytes[0] << 8) & 0xFF00L);
        c |= (char) (bytes[1] & 0xFFL);
        return c;
    }

    /**
     * byte[]转double
     */
    public static double bytesToDouble(byte[] bytes) {
        return Double.longBitsToDouble(bytesToLong(bytes));
    }

    /**
     * byte[]转float
     */
    public static float bytesToFloat(byte[] bytes) {
        return Float.intBitsToFloat(bytesToInt(bytes));
    }

    /**
     * char转byte[]
     */
    public static byte[] charToBytes(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) (c >>> 8);
        b[1] = (byte) c;
        return b;
    }

    /**
     * double转byte[]
     */
    public static byte[] doubleToBytes(double d) {
        return longToBytes(Double.doubleToLongBits(d));
    }

    /**
     * float转byte[]
     */
    public static byte[] floatToBytes(float f) {
        return intToBytes(Float.floatToIntBits(f));
    }


    /**
     * 将16进制字符串压缩转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] hexToBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        if (str.length() % 2 > 0) { // 因为一个字节转为16进制为两个字符，所以必须是双数，否则转换会有问题
            throw new RuntimeException("string is not hex");
        }
        int size = (str.length() / 2);
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    public static byte[] hexStrToByte(String value) {
        byte[] bytes = new byte[100];
        String[] valArr = value.split("\\\\x");
        int i = 0;
        for (String v : valArr) {
            if (StringUtils.isBlank(v)) {
                continue;
            }
            if (i == 0) {
                char[] chars = v.toCharArray();
                for (char c : chars) {
                    bytes[i++] = (byte) c;
                }
                continue;
            }
            String sub = v.substring(0, 2);
            bytes[i++] = (byte) Integer.parseInt(sub, 16);
            if (v.length() > 2) {
                char[] chars = v.substring(2).toCharArray();
                for (char c : chars) {
                    bytes[i++] = (byte) c;
                }
            }
        }
        return Arrays.copyOfRange(bytes, 0, i);
    }

    public static String hexDecode(String value) {
        char[] chars = value.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int step = 0; step < chars.length; step++) {
            char c = chars[step];
            if (c == '\\' && step < chars.length - 3 && chars[step + 1] == 'x') {
                int data = Integer.parseInt(String.valueOf(chars[step + 2]) + chars[step + 3], 16);
                result.append((char) data);
                step += 3;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}