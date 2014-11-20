package com.icapture.entity.classify;

import java.util.Date;

/**
 * 任务分类实体
 * 
 * @author huxiaohuan
 *
 */
public class Classify {
	
	/**
	 * 对应数据库表名
	 */
	public static final String DB_NAME = "topic_groups";
	
	private Integer id;
	
	private String name;
	
	private String topic_name;
	
	private String url;
	
	private String resource_dir;
	
	private Integer topic_level;
	
	private Integer main_iframe;
	
	private Integer topic_iframe;
	
	private Integer max_topics;
	
	private Integer max_reply;
	
	private Integer max_resource;
	
	private String scripts_iframe;
	
	private String scripts_topic;
	
	private String scripts_content;
	
	private String scripts_links;
	
	private String scripts_reply;
	
	private String scripts_pages;
	
	private Integer max_tasks;
	
	private Integer max_depth;
	
	private String method;
	
	private String param;
	
	private String sessionid;
	
	private Integer need_down;
	
	private Integer status;
	
	private Date last_date;
	
	private Integer topic_type;
	
	private String update_interval;
	
	private String date_pos;

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

	public String getTopic_name() {
		return topic_name;
	}

	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResource_dir() {
		return resource_dir;
	}

	public void setResource_dir(String resource_dir) {
		this.resource_dir = resource_dir;
	}

	public Integer getTopic_level() {
		return topic_level;
	}

	public void setTopic_level(Integer topic_level) {
		this.topic_level = topic_level;
	}

	public Integer getMain_iframe() {
		return main_iframe;
	}

	public void setMain_iframe(Integer main_iframe) {
		this.main_iframe = main_iframe;
	}

	public Integer getTopic_iframe() {
		return topic_iframe;
	}

	public void setTopic_iframe(Integer topic_iframe) {
		this.topic_iframe = topic_iframe;
	}

	public Integer getMax_topics() {
		return max_topics;
	}

	public void setMax_topics(Integer max_topics) {
		this.max_topics = max_topics;
	}

	public Integer getMax_reply() {
		return max_reply;
	}

	public void setMax_reply(Integer max_reply) {
		this.max_reply = max_reply;
	}

	public Integer getMax_resource() {
		return max_resource;
	}

	public void setMax_resource(Integer max_resource) {
		this.max_resource = max_resource;
	}

	public String getScripts_iframe() {
		return scripts_iframe;
	}

	public void setScripts_iframe(String scripts_iframe) {
		this.scripts_iframe = scripts_iframe;
	}

	public String getScripts_topic() {
		return scripts_topic;
	}

	public void setScripts_topic(String scripts_topic) {
		this.scripts_topic = scripts_topic;
	}

	public String getScripts_content() {
		return scripts_content;
	}

	public void setScripts_content(String scripts_content) {
		this.scripts_content = scripts_content;
	}

	public String getScripts_links() {
		return scripts_links;
	}

	public void setScripts_links(String scripts_links) {
		this.scripts_links = scripts_links;
	}

	public String getScripts_reply() {
		return scripts_reply;
	}

	public void setScripts_reply(String scripts_reply) {
		this.scripts_reply = scripts_reply;
	}

	public String getScripts_pages() {
		return scripts_pages;
	}

	public void setScripts_pages(String scripts_pages) {
		this.scripts_pages = scripts_pages;
	}

	public Integer getMax_tasks() {
		return max_tasks;
	}

	public void setMax_tasks(Integer max_tasks) {
		this.max_tasks = max_tasks;
	}

	public Integer getMax_depth() {
		return max_depth;
	}

	public void setMax_depth(Integer max_depth) {
		this.max_depth = max_depth;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public Integer getNeed_down() {
		return need_down;
	}

	public void setNeed_down(Integer need_down) {
		this.need_down = need_down;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLast_date() {
		return last_date;
	}

	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}

	public Integer getTopic_type() {
		return topic_type;
	}

	public void setTopic_type(Integer topic_type) {
		this.topic_type = topic_type;
	}

	public String getUpdate_interval() {
		return update_interval;
	}

	public void setUpdate_interval(String update_interval) {
		this.update_interval = update_interval;
	}

	public String getDate_pos() {
		return date_pos;
	}

	public void setDate_pos(String date_pos) {
		this.date_pos = date_pos;
	}
	
}
