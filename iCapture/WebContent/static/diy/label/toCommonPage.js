//加载数据表格
$('#dg').datagrid({
    url: webRoot + 'common/queryPageByLabel?labelid=' + labelid,
    fit: true, //设置自适应高度
    sortName: 'id',
    sortOrder: 'desc',
    pagination: true, //设置分页
    rownumbers: true, //显示行号
    singleSelect: true,//设置为单选行
    
    columns:[[
        {field:'item0',title:'标题'},
        {field:'item1',title:'原文链接'},
        {field:'item2',title:'时间'},
        {
        	field:'a',
        	title:'操作',
			formatter: function(val,rec){
				return '<a href="' + rec.item1 + '" target="_blank" >查看原文</a>';
			}		        	
        }
    ]],
    
    //绑定右键菜单
    onRowContextMenu: function(e,index,row){
    	//阻止浏览器的右键菜单
    	e.preventDefault();
    	$('#mm').menu('show', {
			left:e.pageX,
			top:e.pageY,
		});
    }

});


//右键菜单添加标签方法
function addLabel(){
	
	$("#playLabel").dialog('open').dialog('setTitle','添加标签');
	
	$('#labelTab').datagrid({
	    url: webRoot + 'label/queryAll',
	    
	    fit: true, //设置自适应高度
	    sortName: 'id',
	    sortOrder: 'desc',
	    pagination: false, //设置分页
	    rownumbers: true, //显示行号
	    checkOnSelect: true,
	    
	    columns:[[
	    	{field:'ck',checkbox:true},
	    	{field:'id',title:'ID',hidden: true},
	        {field:'name',title:'标签名称',width:320},
	    ]],
	    
	    loadFilter: function(data){
	    	if(data.code != 0){
	    		$.messager.alert("提示信息","服务器忙，请稍后再试!");
	    	} else {
	    		return data;
	    	}
	    },
	    
	    //初始化 选中行
	    onLoadSuccess: function(data){
	    	//获取选中行的id
	    	var dataRow = $("#dg").datagrid('getSelected');
	    	var id = dataRow.id;
	    	$.post(
	    		webRoot + 'label/qyeryLabelByCommon',
	    		{'common_id':id},
	    		function(data){
	    			if(data.code != 0){
	    				$.messager.alert("提示信息",data.message);
	    			} else {
	    				$.each(data.data, function(index, item){
	    					$.each($("#labelTab").datagrid('getRows'),function(i,i_item){
	    						if(item.id == i_item.id){
	    							$("#labelTab").datagrid('selectRow',i);
	    							return true;
	    						}
	    					})
	    				});
	    			}
	    		}
	    	);
	    }
	    
	});
}

/**
 * 保存打标签
 */
function savePlayLabel(){
	//获取所有选中标签，将id存放到arry中
	var array = new Array();
	var rows = $("#labelTab").datagrid("getSelections");    // 获取所有选中的行
	for (var i = 0; rows && i < rows.length; i++) {
	    var row = rows[i];
	    array.push(row.id);
	}
	//获取选中行的id
	var dataRow = $("#dg").datagrid('getSelected');
	var id = dataRow.id;
	
	$.ajax({
		url: webRoot + 'common/playLabel',
		type: 'post',
		data: {'id': id,'labels':array.join(",")},
		dataType: 'json',
		success: function(data){
			if(data.code == 0){
				 $('#playLabel').dialog('close'); // 关闭窗口
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