package com.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * <pre>
 * <b>MD5加、解密辅助工具.</b>
 * <b>Description:</b> 主要提供对字符串进行加密; 对加密后字符串进行解密.
 *     MD5的算法在RFC1321中定义, 给出了Test suite用来检验你的实现是否正确: 
 *     MD5 ("") = d41d8cd98f00b204e9800998ecf8427e 
 *     MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 *     MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72 
 *     MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0 
 *     MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 * 
 * <b>Author:</b> huxiaohuan
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 firefly.org Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01    huxiaohuan
 *         new file.
 * </pre>
 */
public abstract class MD5Util extends _Util {

    /**
     * MD5
     */
    public static final String MD5 = "MD5";

    /**
     * 默认构造方法.
     */
    protected MD5Util() {
        super();
    }

    /**
     * 对字符串进行MD5加密.<br/>
     * 如果待加密的字符串为null, 则直接返回null.
     * 
     * @param str 待加密的字符串.
     * @return String 加密后字符串.
     */
    public static String encode(String str) {
        return encode(str, DEFAULT_CHARSET);
    }

    /**
     * 对字符串进行MD5加密.<br/>
     * 如果待加密的字符串为null, 则直接返回null.
     * 
     * @param str 待加密的字符串.
     * @param charset 文件转码字符集编码.
     * @return String 加密后字符串.
     */
    public static String encode(String str, Charset charset) {
        byte[] bytes = getBytes(str, charset);
        return encode(bytes);
    }

    /**
     * 对二进制进行MD5加密.<br/>
     * 如果待加密的二进制为null, 则直接返回null.
     * 
     * @param bytes 待加密的二进制.
     * @return String 加密后二进制.
     */
    public static String encode(byte[] bytes) {
        if (null == bytes) {
            return null;
        }

        String str = null;
        // 每个字节用 16 进制表示的话, 使用两个字符, 所以表示成 16 进制需要 32 个字符
        char chars[] = new char[16 * 2];
        // 表示转换结果中对应的字符位置
        int k = 0;
        try {
            // 用来将字节转换成 16 进制表示的字符
            MessageDigest md = MessageDigest.getInstance(MD5);
            md.update(bytes);
            // MD5 的计算结果是一个 128 位的长整数, 用字节表示就是 16 个字节
            byte _bytes[] = md.digest();
            // 从第一个字节开始, 对 MD5 的每一个字节转换成 16 进制字符的转换
            for (int i = 0; i < 16; i++) {
                // 取第 i 个字节
                byte bt = _bytes[i];
                // 取字节中高 4 位的数字转换, >>> 为逻辑右移, 将符号位一起右移
                chars[k++] = HEX_DIGITS[bt >>> 4 & 0xf];
                // 取字节中低 4 位的数字转换
                chars[k++] = HEX_DIGITS[bt & 0xf];
            }
            // 换后的结果转换为字符串
            str = new String(chars);
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("MD5Util.encode() error, bytes:" + Arrays.toString(bytes) + ", reason:" + e.getMessage());
            }
        }
        return str;
    }
}
