//加载数据表格
$('#dg').datagrid({
    //url: webRoot + 'common/query?groupid=' + groupid,
	//为了适应多个地方对该页面的调用,这里加载datagrid的url采用调用者传递的url进行查询,调用者去实现对应的请求查询
	url : webRoot + url,
    fit: true, //设置自适应高度
    fitColumns: true, //设置自适应宽度
    sortName: 'id',
    sortOrder: 'desc',
    pagination: true, //设置分页
    rownumbers: true, //显示行号
    singleSelect: true,//设置为单选行
    striped: true, //设置单行换色
    pageList: [10,20,30,40,50],
    pageSize: 10,
    border: false, //设置没有边框
    
    
    columns:[[
        {field:'ck',checkbox:true},
        {field:'has_read',title:'状态',align:'center',
        	formatter: function(val,rec){
        		if(val == 0){
        			return '<span style="color:red;">新</span>';
        		} else {
        			return '';
        		}
        	}
        },
        {field:'item0',title:'标题'},
        {field:'item1',title:'原文链接'},
        {field:'item2',title:'时间'},
        {
        	field:'a',
        	title:'操作',
			formatter: function(val,rec){// target="_blank"
				if(rec.has_read == 0){
					return '<a href="javascript:see(\'' + rec.id + '\',\'' + rec.item1 + '\');" >查看原文</a>';
				} else {
					return '<a href="' + rec.item1 + '" target="_blank" >查看原文</a>';
				}
			}		        	
        }
    ]],
    
    loadFilter: function(data){
    	return loadfilter(data);
    },
    
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

/**
 * 以下为打标签
 */

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
	    	return loadfilter(data);
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

/**
 * 以下为分组
 */

//右键添加分组
function addGroup(){
	$("#playGroup").dialog('open').dialog('setTitle','添加分组');
	//加载所有分组
	$("#groupTab").datagrid({
		url: webRoot + 'group/queryAll',
		fit: true, //设置自适应高度
	    sortName: 'id',
	    sortOrder: 'desc',
	    pagination: false, //设置分页
	    rownumbers: true, //显示行号
	    singleSelect: true, //设置为单选,只能包含一个分组
	    checkOnSelect: true,
	    
	    columns:[[
  	    	{field:'ck',checkbox:true},
  	    	{field:'id',title:'ID',hidden: true},
  	        {field:'name',title:'分组名称',width:320},
  	    ]],
  	    
		loadFilter: function(data){
			return loadfilter(data);
		},
	    
	    //初始化该文章所属的分组 ,data中group_groupid为所属的分组
	    onLoadSuccess: function(data){
	    	var dataRow = $("#dg").datagrid('getSelected');
	    	var catalog_id = dataRow.catalog_id;
	    	$.each($("#groupTab").datagrid('getRows'),function(index,item){
	    		if(item.id == catalog_id){
	    			$("#groupTab").datagrid('selectRow',index);
	    			return false;
	    		}
	    	})
	    }
  	    
	});
}

//保存分组
function savePlayGroup(){
	//获取选分组的id
	var groupId = $("#groupTab").datagrid('getSelected').id;
	//获取选中的文章id
	var commId = $("#dg").datagrid('getSelected').id;
	
	$.ajax({
		url: webRoot + 'common/playGroup',
		type: 'post',
		data: {'commId':commId,'groupId':groupId},
		dataType: 'json',
		success: function(data){
			if(data.code == 0){
				$('#dg').datagrid('reload');
				$('#playGroup').dialog('close'); // 关闭窗口
				 $.messager.show({
                   title: '提示信息',
                   msg: '操作成功!',
				 });
			} else {
				$.messager.alert('error',data.message);
			}
		}
	});
}

/**
 * 查看新闻
 * 
 * @param id
 */
function see(id,url){
	//在新窗口查看链接
	window.open(url);
	//后台请求改变为已经查看
	var row = $("#dg").datagrid('getSelected');
	var selectRowIndex = $("#dg").datagrid('getRowIndex',row);
	$.ajax({
		url: webRoot + 'common/see',
		type: 'post',
		data: {'id':id},
		dataType: 'json',
		success: function(data){
			if(data.code == 0){
				//修改该行的状态值
				$("#dg").datagrid('updateRow',{
					index: selectRowIndex,
					row:{
						ISSEE: '1'
					}
				});
			} else {
				$.messager.alert('error',data.message);
			}
		}
	});
}
