//加载数据表格
$('#dg').datagrid({
	url : webRoot + 'classify/query',
    fit: true, //设置自适应高度
    fitColumns: true, //设置自适应宽度
    singleSelect: true,//设置为单选行
    striped: true, //设置单行换色
    border: false, //设置没有边框
    
    columns:[[
        {field:'name',title:'名称'},
        {field:'todayCount',title:'今日',align:'center'},
        {field:'nosee',title:'未读',align:'center'}
    ]],
    
    loadFilter: function(data){
    	return loadfilter(data);
    },
    
    onClickRow: function(rowIndex, rowData){
    	addTab('tabId_' + rowData.name,rowData.name,webRoot + 'common/index?url=common/query?groupid=' + rowData.id);
    },
    
    onLoadSuccess: function(data){
    	//加载每个网站平台的未读信息条数
    	$.ajax({
    		url: webRoot + 'classify/querySee',
    		type: 'post',
    		dataType: 'json',
    		success: function(data){
    			var see = data.see;
    			for(var i = 0; i < see.length; i++){
    				$.each($("#dg").datagrid('getRows'),function(index,item){
        				if(see[i].id == item.id){
        					alert(see[i].count + "," + item.id);
        					$("#dg").datagrid('updateRow',{
        						index: index,
        						row:{
        							nosee: see[i].count
        						}
        					})
        				}
        			});
    			}
    		}
    	});
    }
    
});
