package com.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

/**
 * <pre>
 * <b>数据库操作辅助工具.</b>
 * <b>Description:</b> 
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
public abstract class DBUtil extends _Util {

    /**
     * 查询统计综合时用该key 获取对应的字段值.
     */
    public static final String OTALCOUNT_KEY = "totalCount";

    /**
     * 默认构造方法.
     */
    protected DBUtil() {
        super();
    }

    /**
     * 构建UUID.
     * 
     * @return String
     */
    public static String buildUuid() {
        String uuid = UUID.randomUUID().toString().toUpperCase();
        return uuid.replaceAll("-", "");
    }

    /**
     * <pre>
     * 转换普通SQL 的 select count(1) 语句 （Oracle 版本）.
     * 
     * eg: "select * from per_user";
     *     "select uuid from per_user where uuid in (select userId from per_userrole)"
     * </pre>
     * 
     * @param page
     */
    public static String buildCountSql(final String sql) {
        // int index = sql.indexOf("from");
        // sql = "select count(1) as " + OTALCOUNT_KEY + " " +
        // sql.substring(index, sql.length());
        // return sql;
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) ").append(OTALCOUNT_KEY);
        sb.append(" from (").append(sql).append(")");
        return sb.toString();
    }
    
    /**
     * 转换普通SQL 的 select count(1) 语句 （Mysql 版本）.
     * 
     * @param sql
     * @return
     */
    public static String buildCountSql_mysql(final String sql){
    	StringBuffer sb = new StringBuffer();
        sb.append("select count(1) ").append(OTALCOUNT_KEY);
        /**
         * mysql 需要添加上 表的别名 t, oracle不需要添加别名
         */
        sb.append(" from (").append(sql).append(") t");
        return sb.toString();
    }

    /**
     * 构建分页SQL语句（Oracle 版本）.
     * 
     * @param sql
     * @param pageNo
     * @param pageSize
     * @return String
     */
    public static String buildPageSql(String sql, final int pageNo, final int pageSize) {
        StringBuffer sb = new StringBuffer();
        String beginrow = String.valueOf((pageNo - 1) * pageSize);
        String endrow = String.valueOf(pageNo * pageSize);
        sb.append("select * from ( select data.*, rownum rownum_ from ( ");
        sb.append(sql).append(" ) data where rownum<= ").append(endrow);
        sb.append(") where rownum_ > ").append(beginrow);
        return sb.toString();
    }
    
    /**
     * 构建分页SQL语句(Mysql 版本)
     * 
     * @param sql
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static String buildPageSql_mysql(String sql, final int pageNo, final int pageSize){
    	StringBuffer sb = new StringBuffer();
    	String begin = String.valueOf((pageNo - 1) * pageSize);
    	sb.append("select * from ( ");
    	sb.append(sql).append(" ) t limit ").append(begin).append(",").append(pageSize);
    	return sb.toString();
    }

    /**
     * 关闭会话中所有对象.
     * 
     * @param res
     * @param stm
     * @param conn
     */
    public static void close(final ResultSet res, final Statement stm, final Connection conn) {
        close(res);
        close(stm);
        close(conn);
    }

    /**
     * 关闭回话中所有对象.
     * 
     * @param res
     * @param stm
     */
    public static void close(final ResultSet res, final Statement stm) {
        close(res);
        close(stm);
    }

    /**
     * 关闭数据库相关操作对象.
     * 
     * @param conn
     */
    public static void close(final Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.warn("DBUtil.close() error, conn:" + String.valueOf(conn) + ", reason:" + e.getMessage());
                }
            }
        }
    }

    /**
     * 关闭数据库相关操作对象.
     * 
     * @param stm
     */
    public static void close(final Statement stm) {
        if (null != stm) {
            try {
                stm.close();
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.warn("DBUtil.close() error, stm:" + String.valueOf(stm) + ", reason:" + e.getMessage());
                }
            }
        }
    }

    /**
     * 关闭数据库相关操作对象.
     * 
     * @param res
     */
    public static void close(final ResultSet res) {
        if (null != res) {
            try {
                res.close();
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.warn("DBUtil.close() error, res:" + String.valueOf(res) + ", reason:" + e.getMessage());
                }
            }
        }
    }

}
