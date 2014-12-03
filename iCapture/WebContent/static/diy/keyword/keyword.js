//加载数据表格
$('#dg').datagrid({
    url: webRoot + 'keyword/query',
    
    fit: true, //设置自适应高度
    sortName: 'id',
    sortOrder: 'desc',
    pagination: true, //设置分页
    rownumbers: true, //显示行号
    singleSelect: true,//设置为单选行
    border: false, //设置没有边框
    
    toolbar:"#toolbar",
    
    columns:[[
        {field:'name',title:'关键字'},
        {field:'wtype',title:'正负面'},
        {field:'stype',title:'处理方式'},
        {field:'idate',title:'创建时间'},
        {field:'status',title:'状态'},
        {field:'groupid',title:'默认分组'}
    ]],
    
    loadFilter: function(data){
    	return loadfilter(data);
    }
    
});

//以下为弹出窗口操作//

var url;

//添加标签
function addKeyword(){
	$("#addKeyword").dialog('open').dialog('setTitle','添加关键字');
	url = webRoot + 'label/add';
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
