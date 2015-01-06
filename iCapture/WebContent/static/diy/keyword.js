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
        {field:'site_rate', title:'权重'}
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
$("#site_rate").combobox({
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

//添加标签
function addKeyword(){
	$("#addKeyword").dialog('open').dialog('setTitle','添加关键字');
	url = webRoot + 'keyword/add';
}

//保存标签
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
				$.messager.alert('error',code.message);
			}
		}
	});
}
