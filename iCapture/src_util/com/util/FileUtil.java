package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * <pre>
 * <b>文件辅助工具.</b>
 * <b>Description:</b> 主要提供如下: 
 *   1、提供获取当前程序的BasePath路径;
 *   2、对文件、文件目录进行创建、移动、拷贝、删除、判断是否存在等;
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
public abstract class FileUtil extends _Util {

    /**
     * 缓冲池大小, 默认: 2048.
     */
    public static final int BUFFER_SIZE = 2048;

    /**
     * 操作系统路径分隔符, 默认: File.separator.
     */
    public static final String SEPARATOR = File.separator;

    /**
     * 只读模式： r.
     */
    public static final String READ_MODE = "r";

    /**
     * 可读可写模式： rw.
     */
    public static final String READ_WRITE_MODE = "rw";

    /**
     * CLASSPATH 真实的物理路径, <br/>
     * System.getProperty("user.dir");<br/>
     * FileUtil.class.getResource("/").getPath();<br/>
     * Thread.currentThread().getContextClassLoader().getResource("").getPath();<br/>
     * Thread.currentThread().getContextClassLoader().getResource("/").getPath()
     */
    public static final String CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    /**
     * 当前App运行的根目录路径.<br/>
     * 如果Web App, 则定位到WebContent或者WebRoot目录;<br/>
     * 如果非Web App, 则定位到与 Bin同目录.
     */
    public static final String BASE_PATH = basePath();

    /**
     * 默认构造方法.
     */
    protected FileUtil() {
        super();
    }

    /**
     * 根据当前 CLASS_PATH 解析出App的跟目录路径.
     * 
     * @return String 根目录路径.
     */
    private static String basePath() {
        if (null == CLASS_PATH || CLASS_PATH.length() == 0) {
            return EMPTY_STR;
        }

        String path = CLASS_PATH;
        // 在jar包中是, 需要将 "file:" 去掉, 以及将Windows的文件路径"\" 全替换为 "/".
        path = StringUtil.replace(path, "file:", "");
        path = StringUtil.replace(path, "\\", "/");
        int index = path.length();
        // 循环解析, 直到定位在非特殊目录为止
        while (true) {
            // 查找当前路径中是否包含有/WEB-INF、/bin、/lib、/classes
            if ((index = path.lastIndexOf("/lib")) >= 0 || (index = path.lastIndexOf("/WEB-INF")) >= 0
                    || (index = path.lastIndexOf("/bin")) > 0 || (index = path.lastIndexOf("/classes")) >= 0) {
                path = path.substring(0, index);
            } else {
                break;
            }
        }

        String os = System.getProperty("os.name");
        if (os.toUpperCase().startsWith("WINDOWS") && path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        path = StringUtil.endsWith(path, "/") ? path : path + "/";

        return path;
    }

    /**
     * 判断文件路径对应的文件是否存在.
     * 
     * @param filePath 文件路径.
     * @return boolean 是否存在.
     */
    public static boolean exists(String filePath) {
        if (null == filePath || filePath.length() == 0) {
            return false;
        }

        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 判断文件是否存在.
     * 
     * @param path 文件.
     * @return boolean 是否存在.
     */
    public static boolean exists(File file) {
        if (null == file) {
            return false;
        }

        return file.exists();
    }

    /**
     * 根据路径获取文件.
     * 
     * @param filePath.
     * @return File
     */
    public static File getFile(String filePath) {
        return new File(filePath);
    }

    /**
     * 创建文件目录.
     * 
     * @param filePath
     * @return boolean
     */
    public static boolean makeDir(String filePath) {
        File file = new File(filePath);
        return makeDir(file);
    }

    /**
     * 创建文件目录.
     * 
     * @param floderFile
     * @return boolean
     */
    public static boolean makeDir(File floderFile) {
        if (!floderFile.isDirectory()) {
            makeDir(floderFile.getParentFile());
        }

        if (!floderFile.exists()) {
            return floderFile.mkdirs();
        }
        return false;
    }

    /**
     * 根据路径获取文件输入流.
     * 
     * @param filePath
     * @return FileInputStream
     * @throws FileNotFoundException
     */
    public static FileInputStream getInputStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(getFile(filePath));
    }

    /**
     * 根据文件获取文件输入流.
     * 
     * @param path
     * @return FileInputStream
     * @throws FileNotFoundException
     */
    public static FileInputStream getInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /**
     * 将输入流保存到指定路径文件中.
     * 
     * @param is
     * @param filePath
     */
    public static void saveInputStream(InputStream is, String filePath) {
        saveInputStream(is, new File(filePath));
    }

    /**
     * 将输入流保存到指定文件中.
     * 
     * @param is
     * @param file
     */
    public static void saveInputStream(InputStream is, File file) {
        RandomAccessFile raf = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            raf = new RandomAccessFile(file, READ_WRITE_MODE);
            byte[] by = new byte[1024];
            int i = 0;
            while (-1 != (i = is.read(by))) {
                raf.write(by, 0, i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IoUtil.close(raf, br, isr);
        }
    }
}
