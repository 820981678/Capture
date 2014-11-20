package com.icapture.entity.tblkey;

/**
 * 关键字 对应的页面信息实体
 * 
 * @author huxiaohuan
 *
 */
public class TblKeyPages {

	private Integer id;
	
	/**
	 * 关键字id
	 */
	private Integer keyid;
	
	/**
	 * 论坛组
	 */
	private Integer groupid;
	
	/**
	 * 对应的页面,对应topic_list表id
	 */
	private Integer topicid;
	
	/**
	 * 关键字匹配次数
	 */
	private Integer count1;
	
	/**
	 * 正面还是负面 ,从关键词中来，可以修改,1 正面，2负面,0不定
	 */
	private Integer wtype;
	
	/**
	 * 发布时间,暂时没有数据
	 */
	private String pdate;
	
	/**
	 * 临时属性,主要用于页面数据展示
	 * 记录该条信息对应的页面的标题
	 */
	private String title;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getKeyid() {
		return keyid;
	}

	public void setKeyid(Integer keyid) {
		this.keyid = keyid;
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

	public Integer getCount1() {
		return count1;
	}

	public void setCount1(Integer count1) {
		this.count1 = count1;
	}

	public Integer getWtype() {
		return wtype;
	}

	public void setWtype(Integer wtype) {
		this.wtype = wtype;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
