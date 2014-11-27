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
        {field:'todayCount',title:'今日更新',align:'center'}
    ]],
    
    loadFilter: function(data){
    	return loadfilter(data);
    },
    
    onClickRow: function(rowIndex, rowData){
    	addTab('tabId_' + rowData.name,rowData.name,webRoot + 'common/index?url=common/query?groupid=' + rowData.id);
    }
    
});
