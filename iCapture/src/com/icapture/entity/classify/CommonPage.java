package com.icapture.entity.classify;

import java.util.Date;

/**
 * 分类下所属文章实体
 * 
 * @author huxiaohuan
 *
 */
public class CommonPage {
	
	public static final String DB_NAME = "common_pages";

	private Integer id;
	
	private Integer groupid;
	
	private Integer topicid;
	
	private Date time_end;
	
	private Integer stauts;
	
	private String item0;
	
	private String item1;
	
	private String item2;
	
	private String item3;
	
	/**
	 * 到topic_lists查询得到的url
	 * 只用于页面显示
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public Integer getTopicid() {
		return topicid;
	}

	public void setTopicid(Integer topicid) {
		this.topicid = topicid;
	}

	public Date getTime_end() {
		return time_end;
	}

	public void setTime_end(Date time_end) {
		this.time_end = time_end;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

	public String getItem0() {
		return item0;
	}

	public void setItem0(String item0) {
		this.item0 = item0;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}
	
}
