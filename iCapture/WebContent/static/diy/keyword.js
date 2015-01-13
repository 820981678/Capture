//加载数据表格
$('#dg').datagrid({
    url: webRoot + 'keyword/query',
    
    fit: true, //设置自适应高度
    sortName: 'id',
    sortOrder: 'desc',
    pagination: true, //设置分页
    rownumbers: true, //显示行号
    singleSelect: true,//设置为单选行
    border: true, //设置有边框
    
    toolbar:"#toolbar",
    
    columns:[[
        {field:'name',title:'关键字'},
        {field:'wtype',title:'正负面',
        	formatter: function(value,row,index){
        		if(value == 0){
        			return '不定';
        		} else if(value == 1){
        			return '正面';
        		} else {
        			return '负面';
        		}
        	}
        },
        {field:'stype',title:'处理方式',
        	formatter: function(val,row,index){
        		if(val == 0){
        			return '全词匹配';
        		} else if(val == 1){
        			return '分隔后全部匹配';
        		} else {
        			return '分隔后匹配任何一个';
        		}
        	}
        },
        {field:'idate',title:'创建时间'},
        {field:'status',title:'状态',
        	formatter: function(val,row,index){
        		if(val == 0){
        			return '有效';
        		} else {
        			return '无效';
        		}
        	}
        },
        {field:'groupName',title:'默认分组'},
        {field:'site_rate', title:'权重',
        	formatter: function(val,row){
        		var text;
        		switch(val){
        		case 10:
        			text = "非常敏感";
        			break;
        		case 8:
        			text = "很敏感";
        			break;
        		case 6:
        			text = "一般关注";
        			break;
        		case 4:
        			text = "一般";
        			break;
        		case 0:
        			text = "其他";
        			break;
        		}
        		return text;
        	}
        }
    ]],
    
    loadFilter: function(data){
    	return loadfilter(data);
    }
    
});

//加载分组
$("#groupid").combobox({
	url: webRoot + 'group/queryAll',
	valueField: 'id',
	textField: 'name',
	loadFilter: function(data){
		if(data.code == 0){
			return data.rows;
		} else {
			$.message.alert('error','加载分组异常');
		}
	}
});

//加载权重
$("#site_rate_select").combobox({
	url: webRoot + 'keyword/querySiteRate',
	valueField: 'key',
	textField: 'name',
	loadFilter: function(data){
		if(data.code == 0){
			return data.data;
		} else {
			$.messager.alert('error','加载权重异常');
		}
	}
});
$("#site_rate_form").combobox({
	url: webRoot + 'keyword/querySiteRate',
	valueField: 'key',
	textField: 'name',
	loadFilter: function(data){
		if(data.code == 0){
			return data.data;
		} else {
			$.message.alert('error','加载权重异常');
		}
	}
});

//以下为弹出窗口操作//

var url;

//添加关键字
function addKeyword(){
	$("#addKeyword").dialog('open').dialog('setTitle','添加关键字');
	url = webRoot + 'keyword/add';
}

//修改关键字
function editKeyword(){
	var row = $("#dg").datagrid('getSelected');
	if(row){
		$("#addKeyword").dialog('open').dialog('setTitle','修改标签');
		$("#addfm").form('load',row);
		url = webRoot + "keyword/update?id=" + row.id;
	} else {
		$.messager.alert('提示信息','请选择要修改的列!');
	}
}

//保存关键字
function saveKeyword(){
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
				$('#addKeyword').dialog('close'); // 关闭窗口
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

//删除关键字
function deleteKeyword(){
	var row = $("#dg").datagrid('getSelected');
	if(!row){
		$.messager.alert('提示信息','请选择要删除的列!');
		return false;
	}
	$.messager.confirm('提示信息','确定要删除该条数据吗?',function(result){
		if(result){
			$.post(
				webRoot + 'keyword/delete',
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