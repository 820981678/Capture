package com.connection.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.PooledDataSource;
import com.util.DBUtil;
import com.util.LogsUtil;
import com.util.ObjectUtil;
import com.util.PropsUtil;
import com.util.StringUtil;

/**
 * <pre>
 * <b>数据库连接池提供器.</b>
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
final class DBPool {

    /**
     * DBPool的唯一Key, 主要用于区分缓存实例的Key.
     */
    private String key;

    /**
     * 属性配置的前缀.
     */
    private String prefix;

    /**
     * 数据库连接池配置文件相对路径.
     */
    private String configPath;

    /**
     * 数据库连接池属性配置集合.
     */
    private Properties props;

    /**
     * 数据库数据源.
     */
    private ComboPooledDataSource dataSource;

    /**
     * 持有当前线程操作DB数据库连接的持有器.
     */
    public ThreadLocal<Connection> connHolder;

    /**
     * 日志记录器.
     */
    private static final Logger logger = DBHandle.logger;

    /**
     * 构造方法, 指定唯一Key和配置文件相对路径.
     * 
     * @param key 唯一Key.
     * @param configPath 数据库连接池配置文件相对路径.
     * @throws DBException
     */
    public DBPool(String key, String configPath) throws DBException {
        this(key, configPath, null);
    }

    /**
     * 构造方法, 指定唯一Key和属性配置集合.
     * 
     * @param key 唯一Key.
     * @param props 数据库连接池属性配置集合.
     * @throws DBException
     */
    public DBPool(String key, Properties props) throws DBException {
        this(key, null, props);
    }

    protected DBPool(String key, String configPath, Properties props) throws DBException {
        this.key = key;
        this.configPath = configPath;
        // 如果当前为提供独立的配置文件, 则将在公用的配置文件中加载配置, 因此需要设置相应的配置属性的前缀以示区分.
        this.prefix = (StringUtil.hasText(this.configPath) ? "" : ("db." + key + "."));
        // 如果构造是提供属性配置集合无效, 则构建一个空的Properties.
        this.props = (null != props ? props : null);
        // 实例化持有当前线程操作DB数据库连接的持有器.
        this.connHolder = new ThreadLocal<Connection>();

        // 调用初始化DB操作的环境配置.
        this.init();
    }

    /**
     * <pre>
     * 初始化DB操作的环境配置.
     * 注: 本初始化方法逻辑主要是根据当前对象实例是否配置了有效的配置文件相对路径, 
     * 如果是则初始化时自动加载对应的配置文件, 同时如果装载失败时, 将使用内部提供的porps配置对象进行初始化; 
     * 如果不是配置文件路径无效, 则直接使用当前持有的属性配置集合进行配置数据库连接池.
     * </pre>
     * 
     * @throws DBException
     */
    private synchronized void init() throws DBException {
        logger.info(LogsUtil.LINE);
        logger.info(LogsUtil.PREFIX2 + "[" + this.key + "].DBPool init...");
        String initMode = (StringUtil.hasText(this.configPath) ? "custom config file" : "gloabl share props");
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("initMode", initMode));

        // 如果已经初始化时, 则直接退出当前初始化工作.
        if (null != this.dataSource) {
            logger.warn(this.key + " DBPool has inited!");
        }

        // 如果当前提供的配置文件相对路径有效并且物理配置文件存在, 则直接装载该配置文件用作配置初始化参数.
        if (StringUtil.hasText(this.configPath)) {
            logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("configPath", configPath));
            // 根据配置文件, 直接装载数据库连接池相关的配置对象.
            Properties _props = PropsUtil.load(this.configPath);
            // 判断根据配置文件路径状态的配置对象是否有效, 如果有效, 则采用覆盖的模式初始化当前持有的属性配置集合.
            if (ObjectUtil.isEmpty(_props)) {
                logger.warn(LogsUtil.PREFIX3 + this.key + " DBPool config is empty or file unexits, configPath:"
                        + this.configPath);
            } else {
                if (null != this.props) {
                    this.props.putAll(_props);
                } else {
                    this.props = _props;
                }
            }
        }

        // 实例化数据库数据源.
        this.dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(PropsUtil.getString(props, this.prefix + "driverClass"));
            dataSource.setJdbcUrl(PropsUtil.getString(props, this.prefix + "jdbcUrl"));
            dataSource.setUser(PropsUtil.getString(props, this.prefix + "user"));
            dataSource.setPassword(PropsUtil.getString(props, this.prefix + "password"));
            dataSource.setInitialPoolSize(PropsUtil.getInteger(props, this.prefix + "initialPoolSize", 4));
            dataSource.setMinPoolSize(PropsUtil.getInteger(props, this.prefix + "minPoolSize", 2));
            dataSource.setMaxPoolSize(PropsUtil.getInteger(props, this.prefix + "maxPoolSize", 8));

            dataSource.setAcquireIncrement(PropsUtil.getInteger(props, this.prefix + "acquireIncrement", 3));
            dataSource.setCheckoutTimeout(PropsUtil.getInteger(props, this.prefix + "checkoutTimeout", 0));
            dataSource.setIdleConnectionTestPeriod(PropsUtil.getInteger(props, this.prefix + "idleConnectionTestPeriod", 0));
            dataSource.setMaxIdleTime(PropsUtil.getInteger(props, this.prefix + "maxIdleTime", 0));
            dataSource.setMaxStatements(PropsUtil.getInteger(props, this.prefix + "maxStatements", 0));
            dataSource
                    .setMaxStatementsPerConnection(PropsUtil.getInteger(props, this.prefix + "maxStatementsPerConnection", 0));
            dataSource.setNumHelperThreads(PropsUtil.getInteger(props, this.prefix + "numHelperThreads", 3));
            dataSource.setPropertyCycle(PropsUtil.getInteger(props, this.prefix + "propertyCycle", 300));
            dataSource.setAcquireRetryAttempts(PropsUtil.getInteger(props, this.prefix + "acquireRetryAttempts", 30));
            dataSource.setAcquireRetryDelay(PropsUtil.getInteger(props, this.prefix + "acquireRetryDelay", 500));
            dataSource.setAutoCommitOnClose(PropsUtil.getBoolean(props, this.prefix + "autoCommitOnClose", false));

            dataSource
                    .setBreakAfterAcquireFailure(PropsUtil.getBoolean(props, this.prefix + "breakAfterAcquireFailure", false));
            dataSource.setTestConnectionOnCheckin(PropsUtil.getBoolean(props, this.prefix + "testConnectionOnCheckout", true));
            dataSource.setTestConnectionOnCheckin(PropsUtil.getBoolean(props, this.prefix + "testConnectionOnCheckin", true));
            dataSource.setPreferredTestQuery(PropsUtil.getString(props, this.prefix + "preferredTestQuery",
                    "SELECT 1 FROM DUAL"));
        } catch (PropertyVetoException e) {
            String message = this.key + ".DBPool init error, " + e.getMessage();
            logger.error(message, e);
            throw new DBException(DBException.PRIFIX + "CONFIG_ERROR", message, e);
        }
        
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("driverClass", this.dataSource.getDriverClass()));
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("jdbcUrl", this.dataSource.getJdbcUrl()));
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("user", this.dataSource.getUser()));
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("initialPoolSize", this.dataSource.getInitialPoolSize()));
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("minPoolSize", this.dataSource.getMinPoolSize()));
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("maxPoolSize", this.dataSource.getMaxPoolSize()));
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("checkoutTimeout", this.dataSource.getCheckoutTimeout()));
    }

    /**
     * 重新初始化DB操作的环境配置.
     * 
     * @throws DBException
     */
    public synchronized void reInit() throws DBException {
        logger.info(this.key + " threadPool is reiniting...");
        // 销毁当前数据库数据库.
        this.destroy();
        // 再次重新调用初始化DB操作的环境配置.
        this.init();
    }

    /**
     * 销毁当前数据库数据库.
     * 
     * @throws DBException
     */
    public synchronized void destroy() throws DBException {
        if (null != this.dataSource) {
            try {
                // 关闭 datasource
                DataSources.destroy(this.dataSource);
            } catch (SQLException e) {
                String message = this.key + " threadPool destroy error, " + e.getMessage();
                logger.error(message, e);
                throw new DBException(DBException.PRIFIX + "DESTROY_ERROR", message, e);
            }
            this.dataSource = null;
        }
    }

    /**
     * DBPool的唯一Key, 主要用于区分缓存实例的Key.
     * 
     * @return String
     */
    public String getKey() {
        return this.key;
    }

    /**
     * 获取当前的数据库数据源.
     * 
     * @return PooledDataSource
     */
    public PooledDataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * 获取数据库连接池中新的idle空闲连接.
     * 
     * @return Connection 新的idle空闲连接.
     * @throws DBException
     */
    public Connection getConn() throws DBException {
        try {
            return this.dataSource.getConnection();
        } catch (NullPointerException e) {
            String message = this.key + " DBPool's dataSource uninited, " + e.getMessage();
            logger.error(message, e);
            throw new DBException(DBException.PRIFIX + "UNINIT_DATASOURCE", message, e);
        } catch (SQLException e) {
            String message = this.key + " DBPool get idle connection error, " + e.getMessage();
            logger.error(message, e);
            throw new DBException(DBException.PRIFIX + "GETCONN_ERROR", message, e);
        }
    }

    /**
     * 获取当前线程操作DB数据库的idle空闲连接.
     * 
     * @return Connection 当前线程操作DB数据库的idle空闲连接.
     * @throws DBException
     */
    public Connection getCurrConn() throws DBException {
        Connection conn = null;
        // 持有当前线程操作DB数据库连接的持有器是否已分配可用的idle空闲连接.
        if (null == (conn = this.connHolder.get())) {
            // 为当前操作线程分配一个新的idle空闲连接并与其绑定.
            conn = this.getConn();
            this.connHolder.set(conn);
        }
        return conn;
    }

    /**
     * 数据事务自动提交开关.
     * 
     * @param conn 指定的数据库连接.
     * @param isAutoCommit 开关, true:打开事务自动提交; false: 关闭事务自动提交, 改为手动.
     * @throws DBException
     */
    private void switchTransation(Connection conn, boolean isAutoCommit) throws DBException {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DBException(DBException.PRIFIX + "TURN_RANSATION_ERROR", key + "'s conn "
                    + (isAutoCommit ? "open" : "close") + " auto commit error, " + e.getMessage(), e);
        }
    }

    /**
     * 打开当前数据库连接的事务.
     * 
     * @return Connection 当前线程操作DB数据库的idle空闲连接.
     * @throws DBException
     */
    public Connection beginTransation() throws DBException {
        Connection conn = getCurrConn();
        if (null == conn) {
            throw new DBException(DBException.PRIFIX + "NO_CURRCONN", key + "'s current conn is null. ");
        }
        // 关闭事务自动提交, 改为手动.
        this.switchTransation(conn, false);
        return conn;
    }

    /**
     * 提交当前的数据连接事务.
     * 
     * @throws DBException
     */
    public void commit() throws DBException {
        Connection conn = this.connHolder.get();
        if (null == conn) {
            return;
        }

        try {
            conn.commit();
        } catch (SQLException e) {
            throw new DBException(DBException.PRIFIX + "COMMIT_ERROR", key + "'s conn commit transation error, "
                    + e.getMessage(), e);
        } finally {
            // 打开事务自动提交.
            this.switchTransation(conn, true);
        }
    }

    /**
     * 回归当前的数据连接事务.
     * 
     * @throws DBException
     */
    public void rollback() throws DBException {
        Connection conn = this.connHolder.get();
        if (null == conn) {
            return;
        }

        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new DBException(DBException.PRIFIX + "ROLLBACK_ERROR", key + "'s conn rollback transation error, "
                    + e.getMessage(), e);
        } finally {
            // 打开事务自动提交.
            this.switchTransation(conn, true);
        }
    }

    /**
     * 释放当前数据库连接资源.
     * 
     * @throws DBException
     */
    public void release() {
        Connection conn = this.connHolder.get();
        if (null == conn) {
            return;
        }

        this.connHolder.remove();
        try {
            // 打开事务自动提交.
            this.switchTransation(conn, true);
        } catch (DBException e) {
            if (logger.isInfoEnabled()) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            if (null != conn) {
                DBUtil.close(conn);
            }
        }
    }

    /**
     * 获取当前数据库连接池的运行状态.
     * 
     * @return Map<String, Object>
     */
    public Map<String, Object> status() {
        // 构建Map, 将连接池中的相关状态数据进行持有.
        Map<String, Object> status = new HashMap<String, Object>(4);
        if (null != this.dataSource) {
            status.put("pool.key", this.key);
            status.put("pool.jdbcUrl", this.dataSource.getJdbcUrl());
            status.put("pool.user", this.dataSource.getUser());
            status.put("pool.initialPoolSize", this.dataSource.getInitialPoolSize());
            status.put("pool.minPoolSize", this.dataSource.getMinPoolSize());
            status.put("pool.maxPoolSize", this.dataSource.getMaxPoolSize());
            status.put("pool.maxIdleTime", this.dataSource.getMaxIdleTime());
            status.put("pool.checkoutTimeout", this.dataSource.getCheckoutTimeout());
            status.put("pool.maxStatements", this.dataSource.getMaxStatements());
            status.put("pool.maxConnectionAge", this.dataSource.getMaxConnectionAge());
            status.put("pool.idleConnectionTestPeriod", this.dataSource.getIdleConnectionTestPeriod());
            try {
                status.put("pool.numConnectionsDefaultUser", this.dataSource.getNumConnectionsDefaultUser());
                status.put("pool.numBusyConnectionsDefaultUser", this.dataSource.getNumBusyConnectionsDefaultUser());
                status.put("pool.numIdleConnectionsDefaultUser", this.dataSource.getNumIdleConnectionsDefaultUser());
                status.put("pool.threadPoolSize", this.dataSource.getThreadPoolSize());
                status.put("pool.threadPoolNumActiveThreads", this.dataSource.getThreadPoolNumActiveThreads());
                status.put("pool.threadPoolNumIdleThreads", this.dataSource.getThreadPoolNumIdleThreads());
                status.put("pool.threadPoolNumTasksPending", this.dataSource.getThreadPoolNumTasksPending());
            } catch (SQLException e) {
                if (logger.isInfoEnabled()) {
                    logger.error(this.key + " DBPool get status fail, " + e.getMessage(), e);
                }
            }
        }
        // 返回当前状态数据.
        return status;
    }

    /**
     * 重写GC回收实现.
     */
    @Override
    protected void finalize() throws Throwable {
        this.destroy();
        super.finalize();
    }
}