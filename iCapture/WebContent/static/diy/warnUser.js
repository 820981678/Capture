/**
 * 舆情处理人页面js
 */

//加载数据表格
$('#dg').datagrid({
    url: webRoot + 'warnUser/query',
    
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
    	{field:'warn_name',title:'舆情级别'},
        {field:'user_name',title:'处理人名称'}
    ]],
    
    loadFilter: function(data){
    	return loadfilter(data);
    }
    
});

//以下为弹出窗口操作//

var url;

//添加预警级别
function addWarn(){
	$("#addWarn").dialog('open').dialog('setTitle','添加舆情级别');
	$("#addfm").form('clear');
	url = webRoot + 'warnLevel/add';
}

//修改预警级别
function editWarn(){
	var row = $("#dg").datagrid('getSelected');
	if(row){
		$("#addWarn").dialog('open').dialog('setTitle','修改舆情级别');
		$("#addfm").form('load',row);
		url = webRoot + "warnLevel/update?id=" + row.id;
	} else {
		$.messager.alert('提示信息','请选择要修改的列!');
	}
}

//保存预警级别
function saveWarn(){
	//验证数据是否合法
	if(!$("#addfm").form('validate')){
		return false;
	}
	$.ajax({
		url: url,
		type: 'post',
		data: $("#addfm").serialize(),
		dataType: 'json',
		success: function(data){
			if(data.code == 0){
				$('#addWarn').dialog('close'); // 关闭窗口
                $('#dg').datagrid('reload');    // 刷新datagrid
                $.messager.show({
                    title: '提示信息',
                    msg: '保存成功!',
                });
			} else {
				$.messager.alert('error',data.message);
			}
		}
	});
}

//删除预警级别
function deleteWarn(){
	var row = $("#dg").datagrid('getSelected');
	if(!row){
		$.messager.alert('提示信息','请选择要删除的列!');
		return false;
	}
	$.messager.confirm('提示信息','确定要删除该条数据吗?',function(result){
		if(result){
			$.post(
				webRoot + 'warnLevel/delete',
				{'id':row.id},
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
