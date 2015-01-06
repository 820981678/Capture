package com.icapture.entity.diy;

/**
 * 舆情级别实体
 * 
 * @author huxiaohuan
 *
 */
public class WarnLevel {
	
	/**
	 * 对应数据库名称
	 */
	public static final String DB_NAME = "tbl_warn_level";

	private Integer id;
	
	/**
	 * 分级名称
	 */
	private String name;
	
	/**
	 * 权重下限
	 */
	private Double min_rate;
	
	/**
	 * 权重下线
	 */
	private Double max_rate;
	
	/**
	 * 处理描述
	 */
	private String description;
	
	/**
	 * 需要发送短信:0不需要,1需要
	 */
	private Integer send_msg;
	
	/**
	 * 需要人工处理:0不需要,1需要
	 */
	private Integer need_handle;
	
	/**
	 * 短信模板
	 */
	private String msg_tpl;
	
	/**
	 * 排序
	 */
	private Integer sortflag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMin_rate() {
		return min_rate;
	}

	public void setMin_rate(Double min_rate) {
		this.min_rate = min_rate;
	}

	public Double getMax_rate() {
		return max_rate;
	}

	public void setMax_rate(Double max_rate) {
		this.max_rate = max_rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSend_msg() {
		return send_msg;
	}

	public void setSend_msg(Integer send_msg) {
		this.send_msg = send_msg;
	}

	public Integer getNeed_handle() {
		return need_handle;
	}

	public void setNeed_handle(Integer need_handle) {
		this.need_handle = need_handle;
	}

	public String getMsg_tpl() {
		return msg_tpl;
	}

	public void setMsg_tpl(String msg_tpl) {
		this.msg_tpl = msg_tpl;
	}

	public Integer getSortflag() {
		return sortflag;
	}

	public void setSortflag(Integer sortflag) {
		this.sortflag = sortflag;
	}
	
}
