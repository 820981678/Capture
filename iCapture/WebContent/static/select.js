$(function(){
	//加载标签
	
	//加载分组
	
	//查询事件
	$("#select").linkbutton({
		onClick: function(){
			addTab('tabId_s','ss',webRoot + 'common/index?url=common/query?groupid=1');
		}
	});
	
});