/**
 * 舆情处理人页面js
 */

//保存当前选中的舆情id
var warnLevelId;

//加载全部舆情级别
$.ajax({
	url: webRoot + 'warnLevel/queryAll',
	type: 'post',
	dataType: 'json',
	success: function(data){
		if(data.code != 0){
			$.messager.alert('error',"加载舆级别异常!");
			return false;
		}
		var sum = data.data.length;
		for(var i = 0; i < sum; i++){
			$("#warnLevel_sum").append('<li onclick="queryWarnUser(' + data.data[i].id + ')"><div class="d1">' + data.data[i].name + '</div><div class="d2">' + data.data[i].warn_user_sum + '人</div></li>');
		}
		//ul添加特效
		
	}
});

/**
 * 舆情级别点击事件，查询所属的全部处理人员
 * 
 * @param id
 */
function queryWarnUser(id){
	warnLevelId = id;
	$('#dg').datagrid({
	    url: webRoot + 'admin/user/queryByWarnLevelId?warnLevelId=' + id,
	    
	    fit: true, //设置自适应高度
	    sortName: 'id',
	    sortOrder: 'desc',
	    pagination: true, //设置分页
	    rownumbers: true, //显示行号
	    singleSelect: true,//设置为单选行
	    border: false, //设置没有边框
	    
	    toolbar:"#toolbar",
	    
	    columns:[[
	    	{field:'id',title:'ID',hidden: true},
	    	{field:'name',title:'账户名称'},
	        {field:'user_title',title:'职务'},
	        {field:'phone',title:'联系电话'},
	        {field:'warn_phone',title:'报警电话'},
	        {field:'status',title:'账户状态',align:'center',
	        	formatter: function(val){
	        		if(val == 1){
	        			return "<span style='color:blue'>启用</span>";
	        		} else {
	        			return "<span style='color:red'>禁用</span>";
	        		}
	        	}
	        }
	    ]],
	    
	    loadFilter: function(data){
	    	return loadfilter(data);
	    }
	    
	});
}

/**
 * 打开添加舆情处理人
 */
function openAddWarnUser(){
	$("#warnUserWindow").dialog('open').dialog('setTitle','配置舆情处理人');
	
	//加载可选的处理人
	$('#warnUserTab').datagrid({
	    url: webRoot + 'admin/user/queryAddWarnByWarnLevelId?warnLevelId=' + warnLevelId,
	    
	    fit: true, //设置自适应高度
	    sortName: 'id',
	    sortOrder: 'desc',
	    pagination: false, //设置分页
	    rownumbers: true, //显示行号
	    checkOnSelect: true,
	    
	    columns:[[
	    	{field:'ck',checkbox:true},
	    	{field:'id',title:'ID',hidden: true},
	    	{field:'name',title:'账户名称'},
	        {field:'user_title',title:'职务'},
	        {field:'phone',title:'联系电话'},
	        {field:'warn_phone',title:'报警电话'},
	        {field:'status',title:'账户状态',align:'center',
	        	formatter: function(val){
	        		if(val == 1){
	        			return "<span style='color:blue'>启用</span>";
	        		} else {
	        			return "<span style='color:red'>禁用</span>";
	        		}
	        	}
	        }
	    ]],
	    
	    loadFilter: function(data){
	    	return loadfilter(data);
	    }
	    
	});
}

/**
 * 保存舆情处理人
 */
function saveWarnUser(){
	//获取所有选中标签，将id存放到arry中
	var array = new Array();
	var rows = $("#warnUserTab").datagrid("getSelections");    // 获取所有选中的行
	for (var i = 0; rows && i < rows.length; i++) {
	    var row = rows[i];
	    array.push(row.id);
	}
	
	$.ajax({
		url: webRoot + 'warnUser/add',
		type: 'post',
		data: {'warn_level_id': warnLevelId,'user_ids':array.join(",")},
		dataType: 'json',
		success: function(data){
			if(data.code == 0){
				// 关闭窗口
				$('#warnUserWindow').dialog('close');
				//刷新舆情处理人
				$('#warnUserTab').datagrid('reload');
				$.messager.show({
				    title: '提示信息',
				    msg: '操作成功!',
				});
			} else {
				$.messager.alert('error',code.message);
			}
		}
	});
}

/**
 * 删除舆情处理人
 */
function deleteWarnUser(){
	var row = $("#dg").datagrid('getSelected');
	if(!row){
		$.messager.alert('提示信息','请选择要删除的列!');
		return false;
	}
	$.messager.confirm('提示信息','确定要删除该条数据吗?',function(result){
		if(result){
			$.post(
				webRoot + 'warnUser/delete',
				{'user_id':row.id,'warn_level_id':warnLevelId},
				function(data){
					if(data.code == 0){
		                $('#dg').datagrid('reload');    // 刷新datagrid
		                $.messager.show({
		                    title: '提示信息',
		                    msg: '删除成功!',
		                });
					} else {
						$.messager.alert('error',data.message);
					}
				}
			);
		}
	});
}

