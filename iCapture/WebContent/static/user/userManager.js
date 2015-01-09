/**
 * 账户页面js
 */

//加载数据表格
$('#dg').datagrid({
    url: webRoot + 'admin/user/query',
    
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

//以下为弹出窗口操作//

var url;

//添加账户
function addUser(){
	$("#addUser").dialog('open').dialog('setTitle','添加舆情级别');
	$("#addfm").form('clear');
	url = webRoot + 'admin/user/add';
}

//修改账户
function editUser(){
	var row = $("#dg").datagrid('getSelected');
	if(row){
		$("#addUser").dialog('open').dialog('setTitle','修改舆情级别');
		$("#password").textbox('setValue','------');
		$("#addfm").form('load',row);
		url = webRoot + "admin/user/update?id=" + row.id;
	} else {
		$.messager.alert('提示信息','请选择要修改的列!');
	}
}

//保存账户
function saveUser(){
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
				$('#addUser').dialog('close'); // 关闭窗口
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

//删除账户
function deleteUser(){
	var row = $("#dg").datagrid('getSelected');
	if(!row){
		$.messager.alert('提示信息','请选择要删除的列!');
		return false;
	}
	$.messager.confirm('提示信息','确定要删除该条数据吗?',function(result){
		if(result){
			$.post(
				webRoot + 'admin/user/delete',
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
