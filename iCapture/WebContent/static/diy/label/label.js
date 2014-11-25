/**
 * 标签管理页面js
 */

//加载数据表格
$('#dg').datagrid({
    url: webRoot + 'label/query',
    
    //title: '所有文章',
    //fitColumns: true, //设置自适应宽度
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
        {field:'name',title:'标签名称',width:320},
        {
        	field:'a',title:'操作',
        	formatter: function(val,rec){
        		//return '<a href=\'javascript:addTab("' + rec.name + '","' + rec.name + '","' + webRoot + 'label/toCommonPage?labelid=' + rec.id + '");\'>' + '全部文章' + '</a>';
        		return '<a href=\'javascript:addTab("' + rec.name + '","' + rec.name + '","' + webRoot + 'common/index?url=common/queryPageByLabel?labelid=' + rec.id + '");\'>' + '全部文章' + '</a>';
        	}
        }
    ]],
    
    loadFilter: function(data){
    	if(data.code != 0){
    		$.messager.alert("提示信息","服务器忙，请稍后再试!");
    	} else {
    		return data;
    	}
    }
    
});

//以下为弹出窗口操作//

var url;

//添加标签
function addLabel(){
	$("#addLabel").dialog('open').dialog('setTitle','添加标签');
	$("#addfm").form('clear');
	url = webRoot + 'label/add';
}

//修改标签
function editLabel(){
	var row = $("#dg").datagrid('getSelected');
	if(row){
		$("#addLabel").dialog('open').dialog('setTitle','修改标签');
		$("#addfm").form('load',row);
		url = webRoot + "label/update?id=" + row.id;
	} else {
		$.messager.alert('提示信息','请选择要修改的列!');
	}
}

//保存标签
function saveLabel(){
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
				$('#addLabel').dialog('close'); // 关闭窗口
                $('#dg').datagrid('reload');    // 刷新datagrid
                $.messager.show({
                    title: '提示信息',
                    msg: '保存成功!',
                });
			} else {
				$.messager.alert('error',code.message);
			}
		}
	});
}

//删除标签
function deleteLabel(){
	var row = $("#dg").datagrid('getSelected');
	if(!row){
		$.messager.alert('提示信息','请选择要删除的列!');
		return false;
	}
	$.messager.confirm('提示信息','确定要删除该条数据吗?',function(result){
		if(result){
			$.post(
				webRoot + 'label/delete',
				{'id':row.id},
				function(data){
					if(data.code == 0){
		                $('#dg').datagrid('reload');    // 刷新datagrid
		                $.messager.show({
		                    title: '提示信息',
		                    msg: '删除成功!',
		                });
					} else {
						$.messager.alert('error',code.message);
					}
				}
			);
		}
	});
}
