/**
 * 舆情级别管理页面js
 */

//加载数据表格
$('#dg').datagrid({
    url: webRoot + 'website/query',
    //fitColumns: true, //设置自适应宽度
    fit: true, //设置自适应高度
    sortName: 'id',
    sortOrder: 'desc',
    pagination: true, //设置分页
    rownumbers: true, //显示行号
    singleSelect: true,//设置为单选行
    border: true, //设置有边框
    
    toolbar:"#toolbar",
    
    columns:[[
    	{field:'id',title:'ID',hidden: true},
        {field:'name',title:'网站名称'},
        {field:'url',title:'网站地址'},
        {field:'site_rate',title:'权重',width:50,
        	formatter: function(val,row){
        		if(val == 10){
        			return "全国门户";
        		} else if(val == 9){
        			return "全国专业网站";
        		} else if(val == 8){
        			return "地方门户";
        		} else if(val == 6){
        			return "地方专业网站";
        		} else {
        			return "其他";
        		}
        	}
        },
        {field:'max_resource',title:'最大抓取数',width:50}
    ]],
    
    loadFilter: function(data){
    	return loadfilter(data);
    }
    
});

//加载权重
$("#site_rate_select").combobox({
	url: webRoot + 'website/queryWebSiteRate',
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
	url: webRoot + 'website/queryWebSiteRate',
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

//添加门户网站
function addWebsite(){
	$("#addWebsite").dialog('open').dialog('setTitle','添加舆情级别');
	$("#addfm").form('clear');
	url = webRoot + 'website/add';
}

//修改门户网站
function editWebsite(){
	var row = $("#dg").datagrid('getSelected');
	if(row){
		$("#addWebsite").dialog('open').dialog('setTitle','修改舆情级别');
		$("#addfm").form('load',row);
		url = webRoot + "website/update?id=" + row.id;
	} else {
		$.messager.alert('提示信息','请选择要修改的列!');
	}
}

//保存门户网站
function saveWebsite(){
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
				$('#addWebsite').dialog('close'); // 关闭窗口
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

//删除门户网站
function deleteWebsite(){
	var row = $("#dg").datagrid('getSelected');
	if(!row){
		$.messager.alert('提示信息','请选择要删除的列!');
		return false;
	}
	$.messager.confirm('提示信息','确定要删除该条数据吗?',function(result){
		if(result){
			$.post(
				webRoot + 'website/delete',
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
