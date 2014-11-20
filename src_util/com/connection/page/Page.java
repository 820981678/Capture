package com.connection.page;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * <b>数据库分页数据 封装模型.</b>
 * <b>Description:</b> .
 * 
 * <b>Author:</b> huxiaohuan
 * <b>Date:</b> 2014-1-21 下午03:07:40
 * <b>Copyright:</b> Copyright &copy;2006-2014 chuying Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *    <b>Ver      Date          Author      Detail</b>
 *    1.00    2014-1-21 下午03:07:40  huxiaohuan   new file.
 * </pre>
 */
public class Page<T> {

    // 分页参数
    protected int pageNo;
    protected int pageSize;
    protected boolean autoCount;
    
    //排序参数
    protected String sort;
    protected String order;

    // 返回结果
    protected long total;
    protected List<T> rows;
    
    // 分页查询的基础SQL
    protected String sql = "select 1";
    protected Object[] params = new Object[] {};

    /**
     * 构造方法
     */
    public Page() {
        this(1, 10, true, 0, new ArrayList<T>(0));
    }

    public Page(int pageNo) {
        this(pageNo, 10, true, 0, new ArrayList<T>(0));
    }

    public Page(int pageNo, int pageSize) {
        this(pageNo, pageSize, true, 0, new ArrayList<T>(0));
    }

    public Page(int pageNo, int pageSize, long totalCount) {
        this(pageNo, pageSize, true, totalCount, new ArrayList<T>(0));
    }

    public Page(int pageNo, int pageSize, boolean autoCount) {
        this(pageNo, pageSize, autoCount, 0, new ArrayList<T>(0));
    }
    
    public Page(int pageNo, int pageSize, String sort, String order) {
    	this(pageNo, pageSize, true, 0, new ArrayList<T>(0));
    	this.sort = sort;
    	this.order = order;
	}

	private Page(int pageNo, int pageSize, boolean autoCount, long totalCount, List<T> result) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.autoCount = autoCount;
        this.total = totalCount;
        this.rows = result;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     * 
     * @return int
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     * 
     * @param pageNo
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 获得每页的记录数量,默认为1.
     * 
     * @return int
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量,低于1时自动调整为1.
     * 
     * @param pageSize
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;

        if (pageSize < 1) {
            this.pageSize = 1;
        }
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
     * 
     * @return boolean
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数.
     * 
     * @param autoCount
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    /**
     * 取得总记录数, 默认值为1.
     * 
     * @return long
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总记录数.
     * 
     * @param totalCount
     */
    public void setTotal(final long totalCount) {
        this.total = totalCount;
    }

    /**
     * 取得页内的记录列表.
     * 
     * @return List<T>
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置页内的记录列表.
     * 
     * @param result
     */
    public void setRows(final List<T> result) {
        this.rows = result;
    }

    /**
     * 获取page查询的SQL字符串.
     * 
     * @return String
     */
    public String getSql() {
        return sql;
    }

    /**
     * 设置获取page查询的SQL字符串.
     * 
     * @param sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 获取执行page查询的SQL的查询参数.
     * 
     * @return Object[]
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * 设置获取执行page查询的SQL的查询参数.
     * 
     * @param params
     */
    public void setParams(Object[] params) {
        this.params = params;
    }

    public Page<T> pageSize(final int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     * 
     * @return long
     */
    public long getTotalPages() {
        if (total < 0) {
            return -1;
        }

        long count = total / pageSize;
        if (total % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     * 
     * @return int
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 是否还有上一页.
     * 
     * @return boolean
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     * 
     * @return int
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否还有下一页.
     * 
     * @return boolean
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     * 
     * @return int
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }
    
    public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
