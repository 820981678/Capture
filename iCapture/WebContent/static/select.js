$(function(){
	//查询事件
	$("#select").linkbutton({
		onClick: function(){
			//获取所有被选中的标签/
			var label = '';
			$("[name='label']:checked").each(function(){
				label = label + $(this).val() + ',';
			});
			label = label.substring(0,label.length - 1);
			var group = '';
			//获取所有被选中的分组
			$("[name='group']:checked").each(function(){
				group = $(this).val();
			});
			var tabId = "L" + label.replace(new RegExp(/(,)/g),'') + "G" + group;
			addTab('tabId_' + tabId,tabId,webRoot + 'common/index?url=common/queryPageByAll?label=' + label + '-group=' + group);
		}
	});
	
});