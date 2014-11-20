package com.connection.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.connection.page.Page;
import com.inter.PropertiesSource;
import com.sun.rowset.CachedRowSetImpl;
import com.util.DBUtil;
import com.util.LogsUtil;
import com.util.ObjectUtil;
import com.util.StringUtil;
import com.util._Util;

/**
 * <pre>
 * <b>数据库操作管理器.</b>
 * <b>Description:</b> 主要提供对数据库的增、删、改、查等操作.
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
public final class DBHandle {

    /**
     * 多个数据库源名称集合.
     */
    private Set<String> configNames;

    /**
     * 存储不同数据库连接池的别名以及对应的配置文件路径.
     */
    private Map<String, String> configPaths;

    /**
     * 持有多个数据库连接池实例的容器.
     */
    private static Map<String, DBPool> dbPools = new HashMap<String, DBPool>(1);
    
    /**
     * 获取dbhandle配置文件接口
     */
    private PropertiesSource propertiesSource;

	/**
     * 数据库配置文件 由DbProperties接口负责注入
     */
    private static Properties dbProper;
    
    /**
     * 日志记录器.
     */
    protected static final Logger logger = Logger.getLogger("init.db");

    /**
     * 私有构造方法, 禁止实例化.
     */
    private DBHandle() {
        super();
    }

    /**
     * 业务初始化方法. 注： 独立的配置文件优先于通用配置文件.
     */
    public void init() {
        logger.info(LogsUtil.LINE);
        logger.info(LogsUtil.PREFIX2 + "DBHandle init ...");
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("configNames", configNames));
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("configPaths", configPaths));
        
        //注入dbhandle配置文件
        dbProper = propertiesSource.getProperties();

        // 判断当前是否提供多个有效的数据库连接池自定义配置文件进行初始化.
        if (!ObjectUtil.isEmpty(this.configPaths)) {
            try {
                for (Entry<String, String> entry : this.configPaths.entrySet()) {
                    init(entry.getKey(), entry.getValue());
                }
            } catch (DBException e) {
                throw new RuntimeException(e.getCode() + ":DBHandle init by configPaths error, " + e.getMessage(), e);
            }
        }

        // 判断当前是否提供多个有效的数据库连接池的别名通过共享属性配置进行初始化.
        if (!ObjectUtil.isEmpty(this.configNames)) {
            // 获取全局共享属性配置集合.
            if (ObjectUtil.isEmpty(dbProper)) {
                logger.warn("PropsUtil's gloabl share props is empty!");
            }

            try {
                for (String configName : this.configNames) {
                    init(configName, dbProper);
                }
            } catch (DBException e) {
                throw new RuntimeException(e.getCode() + ":DBHandle init by configNames error, " + e.getMessage(), e);
            }
        }

        // 判断当前启用DBHandle后, 是否有效初始化其中任何一个DBPool.
        if (ObjectUtil.isEmpty(dbPools)) {
            logger.warn("DBHandle current have any one DBPool instance!");
        }
    }

    /**
     * 根据自定义配置文件路径初始化数据库支撑实例.
     * 
     * @param key 数据库唯一Key.
     * @param configPath 数据库配置文件路径.
     * @throws DBException
     */
    public static void init(final String key, final String configPath) throws DBException {
        synchronized (dbPools) {
            if (dbPools.containsKey(key)) {
                logger.warn(key + " DBPool has exist!");
            } else {
                DBPool dbPool = new DBPool(key, configPath);
                dbPools.put(key, dbPool);
            }
        }
    }

    /**
     * 根据全局共享属性配置初始化数据库支撑实例.
     * 
     * @param key 数据库唯一Key.
     * @param props 配置属性集合.
     * @throws DBException
     */
    public static void init(final String key, final Properties props) throws DBException {
        synchronized (dbPools) {
            if (dbPools.containsKey(key)) {
                logger.warn(key + " DBPool has exist!");
            } else {
                DBPool dbPool = new DBPool(key, props);
                dbPools.put(key, dbPool);
            }
        }
    }

    /**
     * 获取指定的数据库连接池.
     * 
     * @param key 数据库类别Key.
     * @return DBPool
     * @throws DBException
     */
    private static DBPool getPool(String key) throws DBException {
        if (!dbPools.containsKey(key)) {
            throw new DBException(DBException.PRIFIX + "UNEXIST_POOL", key + " DBPool unexist!");
        }

        return dbPools.get(key);
    }

    /**
     * 获取默认的数据库连接.
     * 
     * @return Connection
     * @throws DBException
     */
    public static Connection getConn() throws DBException {
        return getConn(_Util.DEFAULT);
    }

    /**
     * 获取指定Key对应的数据库连接.
     * 
     * @param key 数据库类别Key.
     * @return Connection
     * @throws DBException
     */
    public static Connection getConn(final String key) throws DBException {
        DBPool dbPool = getPool(key);
        return dbPool.getCurrConn();
    }

    /**
     * 打开当前数据库连接的事务.
     * 
     * @return Connection 当前线程操作DB数据库的idle空闲连接.
     * @throws DBException
     */
    public static Connection beginTransation() throws DBException {
        return beginTransation(_Util.DEFAULT);
    }

    /**
     * 打开当前数据库连接的事务.
     * 
     * @param key 数据库类别Key.
     * @return Connection 当前线程操作DB数据库的idle空闲连接.
     * @throws DBException
     */
    public static Connection beginTransation(final String key) throws DBException {
        return getPool(key).beginTransation();
    }

    /**
     * 提交当前的数据连接事务.
     * 
     * @throws DBException
     */
    public static void commit() throws DBException {
        commit(_Util.DEFAULT);
    }

    /**
     * 提交当前的数据连接事务.
     * 
     * @param key 数据库类别Key.
     * @throws DBException
     */
    public static void commit(final String key) throws DBException {
        getPool(key).commit();
    }

    /**
     * 回归当前的数据连接事务.
     * 
     * @throws DBException
     */
    public static void rollback() throws DBException {
        rollback(_Util.DEFAULT);
    }

    /**
     * 回归当前的数据连接事务.
     * 
     * @param key 数据库类别Key.
     * @throws DBException
     */
    public static void rollback(final String key) throws DBException {
        getPool(key).rollback();
    }

    /**
     * 释放当前数据库连接资源.
     */
    public static void release() {
        release(_Util.DEFAULT);
    }

    /**
     * 释放当前数据库连接资源.
     * 
     * @param key 数据库类别Key.
     */
    public static void release(final String key) {
        try {
            getPool(key).release();
        } catch (DBException e) {
            if (logger.isInfoEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 执行查询, 查询结果封装为List<Map<String, Object>>.
     * 
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @return List<Map<String, Object>>
     * @throws DBException
     */
    public static List<Map<String, Object>> query(final String sql, final Object... params) throws DBException {
        return exceute(_Util.DEFAULT, sql, new MapListHandler(), params);
    }

    /**
     * 执行查询, 查询结果封装为List<Map<String, Object>>.
     * 
     * @param key 数据库类别Key.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @return List<Map<String, Object>>
     * @throws DBException
     */
    public static List<Map<String, Object>> query(final String key, final String sql, final Object... params)
            throws DBException {
        return exceute(key, sql, new MapListHandler(), params);
    }

    /**
     * 执行查询, 查询结果封装为List<T>.
     * 
     * @param <T> 指定用于封装查询结果的Entity类.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @return List<T>
     * @throws DBException
     */
    public static <T> List<T> query(final String sql, final Object[] params, final Class<T> clazz) throws DBException {
        return exceute(_Util.DEFAULT, sql, new BeanListHandler<T>(clazz), params);
    }

    /**
     * 执行查询, 查询结果封装为List<T>.
     * 
     * @param <T> 指定用于封装查询结果的Entity类.
     * @param key 数据库类别Key.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @return List<T>
     * @throws DBException
     */
    public static <T> List<T> query(final String key, final String sql, final Object[] params, final Class<T> clazz)
            throws DBException {
        return exceute(key, sql, new BeanListHandler<T>(clazz), params);
    }

    /**
     * 执行分页查询, 查询结果封装为Page<T>.
     * 
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param page 分页封装实例.
     * @return Page<T>
     * @throws DBException
     */
    public static <T> Page<T> query(final String sql, final Object[] params, final Page<T> page,Base base) throws DBException {
        return exceute(_Util.DEFAULT, sql, params, null, page,base);
    }

    /**
     * 执行分页查询, 查询结果封装为Page<T>.
     * 
     * @param key 数据库类别Key.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param page 分页封装实例.
     * @return Page<T>
     * @throws DBException
     */
    public static <T> Page<T> query(final String key, final String sql, final Object[] params, final Page<T> page,Base base)
            throws DBException {
        return exceute(key, sql, params, null, page,base);
    }

    /**
     * 执行分页查询, 查询结果封装为Page<T>.
     * 
     * @param <T> 指定用于封装查询结果的Entity类.
     * @param sql查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @param page 分页封装实例.
     * @return Page<T>
     * @throws DBException
     */
    public static <T> Page<T> query(final String sql, final Object[] params, final Class<T> clazz, final Page<T> page,Base base)
            throws DBException {
        return exceute(_Util.DEFAULT, sql, params, clazz, page,base);
    }

    /**
     * 执行分页查询, 查询结果封装为Page<T>.
     * 
     * @param <T> 指定用于封装查询结果的Entity类.
     * @param key 数据库类别Key.
     * @param sql查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @param page 分页封装实例.
     * @return Page<T>
     * @throws DBException
     */
    public static <T> Page<T> query(final String key, final String sql, final Object[] params, final Class<T> clazz,
            final Page<T> page,Base base) throws DBException {
        return exceute(key, sql, params, clazz, page,base);
    }

    /**
     * 执行查询第一行第一列, 查询结果封装为T.
     * 
     * @param <T> 指定返回的数据类型.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @return <T> T
     * @throws DBException
     */
    public static <T> T queryScalar(final String sql, final Object... params) throws DBException {
        return exceute(_Util.DEFAULT, sql, new ScalarHandler<T>(), params);
    }

    /**
     * 执行查询第一行第一列, 查询结果封装为T.
     * 
     * @param <T> 指定返回的数据类型.
     * @param key 数据库类别Key.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @return <T> T
     * @throws DBException
     */
    public static <T> T queryScalar(final String key, final String sql, final Object... params) throws DBException {
        return exceute(key, sql, new ScalarHandler<T>(), params);
    }

    /**
     * 执行查询第一行, 查询结果封装为Map<String, Object>.
     * 
     * @param <T> 指定返回的数据类型.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @return Map<String, Object>
     * @throws DBException
     */
    public static Map<String, Object> queryFirst(final String sql, final Object... params) throws DBException {
        return exceute(_Util.DEFAULT, sql, new MapHandler(), params);
    }

    /**
     * 执行查询第一行, 查询结果封装为Map<String, Object>.
     * 
     * @param <T> 指定返回的数据类型.
     * @param key 数据库类别Key.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @return Map<String, Object>
     * @throws DBException
     */
    public static Map<String, Object> queryFirst(final String key, final String sql, final Object... params) throws DBException {
        return exceute(key, sql, new MapHandler(), params);
    }

    /**
     * 执行查询第一行, 查询结果封装为T.
     * 
     * @param <T> 指定返回的数据类型.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @return List<Map<String, Object>>
     * @throws DBException
     */
    public static <T> T queryFirst(final String sql, final Object[] params, final Class<T> clazz) throws DBException {
        return (T) exceute(_Util.DEFAULT, sql, new BeanHandler<T>(clazz), params);
    }

    /**
     * 执行查询第一行, 查询结果封装为T.
     * 
     * @param <T> 指定返回的数据类型.
     * @param key 数据库类别Key.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @param clazz 数据封装实体类型.
     * @return List<Map<String, Object>>
     * @throws DBException
     */
    public static <T> T queryFirst(final String key, final String sql, final Object[] params, final Class<T> clazz)
            throws DBException {
        return (T) exceute(key, sql, new BeanHandler<T>(clazz), params);
    }

    /**
     * 将ResultSet中某一列的数据存成List, List中存放的是Object对象.
     * 
     * @param key
     * @param sql
     * @param params
     * @param columnName
     * @return
     * @throws DBException
     */
    public static <T> List<T> queryColumnList(final String sql, final Object[] params, final String columnName)
            throws DBException {
        return queryColumnList(_Util.DEFAULT, sql, params, columnName);
    }

    /**
     * 将ResultSet中某一列的数据存成List, List中存放的是Object对象.
     * 
     * @param key
     * @param sql
     * @param params
     * @param columnName
     * @return
     * @throws DBException
     */
    public static <T> List<T> queryColumnList(final String key, final String sql, final Object[] params, final String columnName)
            throws DBException {
        return exceute(key, sql, new ColumnListHandler<T>(columnName), params);
    }

    /**
     * 执行对大字段查询, 返回CachedRowSet接口操作对象.
     * 
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @return CachedRowSet
     * @throws DBException
     */
    public static CachedRowSet queryRowSet(final String sql, final Object... params) throws DBException {
        return queryRowSet(_Util.DEFAULT, sql, params);
    }

    /**
     * 执行大字段查询, 返回CachedRowSet接口操作对象.
     * 
     * @param key 数据库类别Key.
     * @param sql 查询SQL.
     * @param params 查询参数列表.
     * @return CachedRowSet
     * @throws DBException
     */
    public static CachedRowSet queryRowSet(final String key, final String sql, final Object... params) throws DBException {
        String tsql = cleanNull(sql);
        PreparedStatement stm = null;
        ResultSet res = null;
        CachedRowSet cst = null;
        Connection conn = getConn(key);
        try {
            stm = conn.prepareStatement(tsql);
            fillPreparedStatement(stm, params);
            res = stm.executeQuery();
            cst = new CachedRowSetImpl();
            cst.populate(res);
        } catch (SQLException e) {
            String message = key + " db query cached rowset fail, sql:" + sql + ", params:" + Arrays.toString(params) + ", "
                    + e.getMessage();
            throw new DBException("DB_QUERY", message, e);
        } finally {
        	DBUtil.close(res, stm, conn);
        }
        return cst;
    }

    /**
     * 执行序列Sequence查询, 返回生成的新的序列号.
     * 
     * @param seqName 序列Sequence名称后缀, 格式为： SEQ_ + 名称后缀.
     * @return Long
     * @throws DBException
     */
    public static Long querySequence(final String seqName) throws DBException {
        return querySequence(_Util.DEFAULT, seqName);
    }

    /**
     * 执行序列Sequence查询, 返回生成的新的序列号.
     * 
     * @param key 数据库类别Key.
     * @param seqName 序列Sequence名称后缀, 格式为： SEQ_ + 名称后缀.
     * @return Long
     * @throws DBException
     */
    public static Long querySequence(final String key, final String seqName) throws DBException {
        String sql = "SELECT SEQ_" + seqName + ".NEXTVAL FROM DUAL";
        BigDecimal index = queryScalar(key, sql, new Object[0]);
        if (null != index) {
            return index.longValue();
        }
        return null;
    }

    /**
     * 执行增、删、改操作, 返回更新影响记录行数.
     * 
     * @param sql 更新SQL.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final String sql, final Object... params) throws DBException {
        return exceute(_Util.DEFAULT, sql, params);
    }

    /**
     * 执行增、删、改操作, 返回更新影响记录行数.
     * 
     * @param key 数据库类别Key.
     * @param sql 更新SQL.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final String key, final String sql, final Object... params) throws DBException {
        Connection conn = getConn(key);
        QueryRunner qr = new QueryRunner();
        try {
            return qr.update(conn, sql, params);
        } catch (SQLException e) {
            String message = key + " db exceute DML fail, sql:" + sql + ", params:" + Arrays.toString(params) + ", "
                    + e.getMessage();
            logger.error(message, e);
            throw new DBException("DB_DML", message, e);
        }
    }

    /**
     * 执行<b>批量</b>增、删、改操作, 返回更新影响记录行数.
     * 
     * @param sql 更新SQL.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final String sql, final List<Object[]> params) throws DBException {
        return exceute(_Util.DEFAULT, sql, params.toArray());
    }

    /**
     * 执行<b>批量</b>增、删、改操作, 返回更新影响记录行数.
     * 
     * @param key 数据库类别Key.
     * @param sql 更新SQL.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final String key, final String sql, final List<Object[]> params) throws DBException {
        return exceute(key, sql, params.toArray());
    }

    /**
     * 执行<b>批量</b>增、删、改操作, 返回更新影响记录行数.
     * 
     * @param sql 更新SQL.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final String sql, final Object[][] params) throws DBException {
        return exceute(_Util.DEFAULT, sql, params);
    }

    /**
     * 执行<b>批量</b>增、删、改操作, 返回更新影响记录行数.
     * 
     * @param key 数据库类别Key.
     * @param sql 更新SQL.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final String key, final String sql, final Object[][] params) throws DBException {
        Connection conn = getConn(key);
        QueryRunner qr = new QueryRunner();
        int[] count;
        try {
            count = qr.batch(conn, sql, params);
        } catch (SQLException e) {
            String message = key + " db batch DML fail, sql:" + sql + ", params:" + Arrays.toString(params) + ", "
                    + e.getMessage();
            logger.error(message, e);
            throw new DBException("DB_DMLS", message, e);
        }

        int result = 0;
        if (!ObjectUtil.isEmpty(count)) {
            for (int i = 0; i < count.length; i++) {
                result += count[i];
            }
        }
        return result;
    }

    /**
     * 执行<b>批量</b>增、删、改操作, 返回更新影响记录行数.
     * 
     * @param sqls 更新SQL列表.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final List<String> sqls, final List<Object[]> params) throws DBException {
        return exceute(_Util.DEFAULT, sqls, params);
    }

    /**
     * 执行<b>批量</b>增、删、改操作, 返回更新影响记录行数.
     * 
     * @param key 数据库类别Key.
     * @param sqls 更新SQL列表.
     * @param params 更新参数列表.
     * @return int 更新影响记录行数.
     * @throws DBException
     */
    public static int exceute(final String key, final List<String> sqls, final List<Object[]> params) throws DBException {
        if (ObjectUtil.isEmpty(sqls)) {
            String message = key + " db batch DML fail, sqls is empty, params total:" + params.size();
            throw new DBException("DB_DMLS", message);
        }

        int result = 0;
        QueryRunner qr = new QueryRunner();
        Connection conn = beginTransation(key);
        try {
            if (ObjectUtil.isEmpty(params)) {
                for (int i = 0; i < sqls.size(); i++) {
                    String sql = sqls.get(i);
                    result += qr.update(conn, sql);
                }
            } else {
                for (int i = 0; i < sqls.size(); i++) {
                    String sql = sqls.get(i);
                    result += qr.update(conn, sql, params.get(i));
                }
            }
            commit(key);
        } catch (SQLException e) {
            rollback(key);
            String message = key + " db batch DMLS fail, " + e.getMessage();
            logger.error(message, e);
            throw new DBException("DB_DMLS", message, e);
        }
        return result;
    }

    /**
     * 执行分页查询.
     */
    @SuppressWarnings("unchecked")
    private static <T> Page<T> exceute(final String key, final String sql, final Object[] params, final Class<T> clazz,
            Page<T> page,Base base) throws DBException {
        if (page.isAutoCount()) {
            String totalSql = "";
            if(base == Base.Oracle){
            	totalSql = DBUtil.buildCountSql(sql);
            } else if (base == Base.Mysql){
            	totalSql = DBUtil.buildCountSql_mysql(sql);
            }
            Object totalCount = queryScalar(key, totalSql, params);
            if (totalCount == null) {
                throw new DBException("DB_QUERY", key + " db query page totalCount is invalid, sql:" + totalSql + ", params:"
                        + Arrays.toString(params));
            } else {
                page.setTotal(ObjectUtil.toLong(totalCount, 0L));
            }
        }

        if (!page.isAutoCount() || page.getTotal() != 0) {
            String pageSql = "";
            if(base == Base.Oracle){
            	pageSql = DBUtil.buildPageSql(sql, page.getPageNo(), page.getPageSize());
            } else if (base == Base.Mysql){
            	pageSql = DBUtil.buildPageSql_mysql(sql, page.getPageNo(), page.getPageSize());
            }
            List<T> list = null;
            if (null != clazz) {
                list = exceute(key, pageSql, new BeanListHandler<T>(clazz), params);
            } else {
                list = (List<T>) exceute(key, pageSql, new MapListHandler(), params);
            }
            if (!ObjectUtil.isEmpty(list)) {
                page.setRows(list);
            }
        }

        return page;
    }

    /**
     * 执行通用查询.
     */
    private static <T> T exceute(String key, String sql, ResultSetHandler<T> rsh, Object... params) throws DBException {
        Connection conn = getConn(key);
        QueryRunner qr = new QueryRunner();
        T result = null;
        try {
            result = qr.query(conn, sql, rsh, params);
        } catch (SQLException e) {
            String message = key + " db query fail, sql:" + sql + ", params:" + Arrays.toString(params) + ", " + e.getMessage();
            logger.error(message, e);
            throw new DBException("DB_QUERY", message, e);
        }
        return result;
    }

    /**
     * 清理查询SQL中的多余的 "null" 字符串.
     * 
     * @param sql
     * @return
     */
    private static String cleanNull(String sql) {
        sql = StringUtil.replace(sql, "'null'", "''");
        sql = StringUtil.replace(sql, "'%null%'", "'%'");
        return sql;
    }

    private static volatile boolean pmdKnownBroken = false;

    /**
     * 从QueryRunner参考 Fill the <code>PreparedStatement</code> replacement parameters with the given objects.
     * 
     * @param stmt PreparedStatement to fill
     * @param params Query replacement parameters; <code>null</code> is a valid value to pass in.
     * @throws SQLException if a database access error occurs
     */
    private static void fillPreparedStatement(PreparedStatement stmt, Object[] params) throws SQLException {
        if (null == params) {
            return;
        }
        ParameterMetaData pmd = null;
        if (!pmdKnownBroken) {
            pmd = stmt.getParameterMetaData();
            if (pmd.getParameterCount() < params.length) {
                String message = "Too many parameters:expected " + pmd.getParameterCount() + ", was given " + params.length;
                throw new SQLException(message);
            }
        }
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                stmt.setObject(i + 1, params[i]);
            } else {
                // VARCHAR works with many drivers regardless of the actual
                // column type. Oddly, NULL and OTHER don't work with Oracle's
                // drivers.
                int sqlType = Types.VARCHAR;
                if (!pmdKnownBroken) {
                    try {
                        sqlType = pmd.getParameterType(i + 1);
                    } catch (SQLException e) {
                        pmdKnownBroken = true;
                    }
                }
                stmt.setNull(i + 1, sqlType);
            }
        }
    }

    /**
     * 设置 多个数据库源名称集合.
     * 
     * @param configNames
     */
    public void setConfigNames(Set<String> configNames) {
        this.configNames = configNames;
    }

    /**
     * 存储不同数据库连接池的别名以及对应的配置文件路径.
     * 
     * @param configPaths
     */
    public void setConfigPaths(Map<String, String> configPaths) {
        this.configPaths = configPaths;
    }

    /**
     * 重写GC回收实现.
     */
    @Override
    protected void finalize() throws Throwable {
        for (String key : dbPools.keySet()) {
            release(key);
        }
        super.finalize();
    }
    
    public void setPropertiesSource(PropertiesSource propertiesSource) {
		this.propertiesSource = propertiesSource;
	}
    
    /**
     * 数据库类型枚举
     * 
     * @author Administrator
     *
     */
    public enum Base{
    	Oracle,
    	Mysql,
    	Mssql
    }

}
